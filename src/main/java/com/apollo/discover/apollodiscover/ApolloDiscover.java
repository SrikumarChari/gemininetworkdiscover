/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.DiscoveryService;
import com.apollo.discover.nmap.Host;
import com.apollo.domain.dto.ApolloNetworkDTO;
import com.apollo.domain.dto.ApolloServerDTO;
import com.apollo.domain.model.ApolloEnvironment;
import com.apollo.domain.model.ApolloNetwork;
import com.apollo.domain.model.ApolloServer;
import com.apollo.domain.repository.impl.ApolloEnvironmentRepositoryMongoDBImpl;
import com.apollo.mapper.ApolloMapper;
import com.apollo.mapper.ApolloMapperModule;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClient;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import static spark.Spark.*;
import org.pmw.tinylog.Logger;

//import spark.Request;
//import spark.Response;
/**
 *
 * @author schari
 */
public class ApolloDiscover {

    //private final static List<ApolloNetwork> discoveryNetworks = new ArrayList();
    private static final DiscoveryService discSvc = new DiscoveryService();
    static MongoClient mongoClient;
    static Morphia morphia = new Morphia();
    static Datastore ds;

    public static void main(String[] args) throws UnknownHostException {
        //for now we will listen on 1234, i.e., 'localhost:1234/...'
        setPort(1234);

        //setup the mongodb access
        mongoClient = new MongoClient("localhost");

        //create table for the application, networks and servers
        ds = morphia.createDatastore(mongoClient, "Apollo");

        //close the client, it will be opened as required.
        mongoClient.close();

        //currently we will default to physical discovery
        //discSvc.setDiscType(ApolloEnvironmentType.PHYSICAL);
        //create the mapper
        Injector injector = Guice.createInjector(new ApolloMapperModule());
        ApolloMapper mapper = injector.getInstance(ApolloMapper.class);

        //check if authenticated, create the call context and user context here
        //for now it is empty!!!!
        before((request, response) -> {
            boolean authenticated = true;
            // ... check if authenticated
            if (!authenticated) {
                halt(401, "Nice try, you are not welcome here");
            }
        });

        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            //response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            //response.header("Access-Control-Max-Age", "3600");
            //response.header("Access-Control-Allow-Headers", "x-requested-with");
        });

        get("/env/:envname/networks", "application/json", (request, response) -> {
            String envName;
            //decode the URL as it may contain escape characters, etc.
            try {
                envName = URLDecoder.decode(request.params(":envname"), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.error("Severe Error: Unsupported encoding in URL - server Name: {} Exception: {}",
                        request.params(":name"), ex);
                return "Severe Error: Unsupported encoding in URL";
            }
            try {
                //return the networks...
                List<ApolloNetwork> l = allNetworks(envName);
                if (l == null || l.isEmpty()) {
                    response.status(404);
                    Logger.info("Could not find any network for environment {}.", envName);
                    return "No Applications found for environment " + envName;
                } else {
                    response.status(200);
                    //convert to DTO
                    List<ApolloNetworkDTO> netDTOList = new ArrayList();
                    l.stream().forEach(n -> netDTOList.add(mapper.getDTOFromNetwork(n)));
                    Logger.debug("Found applications");
                    return netDTOList;
                }
            } catch (UnknownHostException ex) {
                Logger.error("Severe Error: Unknown host - {}", "localhost");
                response.status(500);
                return "Severe Error: Unknown database host";
            }
        }, new JsonTransformer());

        //discover a single network within a single environment - the network object is in the request body
        post("/env/:envname/discovernetwork", "application/json", (request, response) -> {
            String envName;
            //decode the URL as it may contain escape characters, etc.
            try {
                envName = URLDecoder.decode(request.params(":envname"), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.error("Severe Error: Unsupported encoding in URL - server Name: {} Exception: {}",
                        request.params(":name"), ex);
                return "Severe Error: Unsupported encoding in URL";
            }

            //get DTO json from request body
            String reqBody = request.body();
            ApolloNetworkDTO netDTO = null;
            if (reqBody == null || reqBody.isEmpty() || (netDTO = new Gson().fromJson(reqBody, ApolloNetworkDTO.class)) == null) {
                Logger.error("Error: Wrong request - no body in Discover Network request");
                response.status(200);
                return "Error: Wrong request - no body in Discover Network request";
            }

            try {
                ApolloNetwork network = mapper.getNetworkFromDTO(netDTO);
                List<Host> lSrvs = discoverSingleEnvNetwork(envName, network);
                if (lSrvs == null || lSrvs.isEmpty()) {
                    response.status(404);
                    Logger.info("Could not find any hosts for environment {} network: start {} end {} network {} mask {} hosts {}",
                            envName, network.getStart(), network.getEnd(), network.getNetwork().getHostAddress(), network.getMask(), network.getHost());
                    return "No Applications found for environment " + envName;
                } else {
                    response.status(200);
                    //convert to DTO
                    List<ApolloServerDTO> srvDTOList = new ArrayList();
                    lSrvs.stream().forEach(n -> srvDTOList.add(mapper.getDTOFromHost(n)));
                    Logger.debug("Found {} servers in environment {} for network: start {} end {} network {} mask {} hosts {}",
                            srvDTOList.size(), envName, network.getStart(), network.getEnd(),
                            network.getNetwork().getHostAddress(), network.getMask(), network.getHost());
                    return srvDTOList;
                }
            } catch (UnknownHostException ex) {
                Logger.error("Severe Error: Unknown host - {}", "localhost");
                response.status(500);
                return "Severe Error: Unknown database host";
            }
        }, new JsonTransformer());

        //discover all the networks within an environment -- this is kinda like a refresh
        post("/env/:endvname/discovernetworks", "application/json", (request, response) -> {
            String envName;
            //decode the URL as it may contain escape characters, etc.
            try {
                envName = URLDecoder.decode(request.params(":envname"), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.error("Severe Error: Unsupported encoding in URL - server Name: {} Exception: {}",
                        request.params(":name"), ex);
                return "Severe Error: Unsupported encoding in URL";
            }

            try {
                List<Host> lSrvs = discoverAllEnvNetworks(envName);
                if (lSrvs == null || lSrvs.isEmpty()) {
                    response.status(404);
                    Logger.info("Could not find any hosts for environment {}", envName);
                    return "No servers found for environment " + envName;
                } else {
                    response.status(200);
                    //convert to DTO
                    List<ApolloServerDTO> srvDTOList = new ArrayList();
                    lSrvs.stream().forEach(n -> srvDTOList.add(mapper.getDTOFromHost(n)));
                    Logger.debug("Found {} servers in environment {}", srvDTOList.size(), envName);
                    return srvDTOList;
                }
            } catch (UnknownHostException ex) {
                Logger.error("Severe Error: Unknown host - {}", "localhost");
                response.status(500);
                return "Severe Error: Unknown database host";
            }
        }, new JsonTransformer());
    }

    private static List<ApolloNetwork> allNetworks(String envName) throws UnknownHostException {
        //setup the mongodb access
        mongoClient = new MongoClient("localhost");

        ApolloEnvironmentRepositoryMongoDBImpl netDB = new ApolloEnvironmentRepositoryMongoDBImpl(mongoClient, morphia, "Apollo");
        List<ApolloNetwork> networks = netDB.getEnvNetworks(envName);

        //close the db client
        mongoClient.close();

        return networks;
    }

    private static List<Host> discoverSingleEnvNetwork(String envName, ApolloNetwork network) throws UnknownHostException {
        //setup the mongodb access
        mongoClient = new MongoClient("localhost");

        //get the environment object
        ApolloEnvironmentRepositoryMongoDBImpl netDB = new ApolloEnvironmentRepositoryMongoDBImpl(mongoClient, morphia, "Apollo");
        ApolloEnvironment env = netDB.getEnvByName(envName);
        if (env == null) {
            Logger.error("Error: Discover Single Network - environment with name {} not found", envName);
            return null;
        }

        //discover network
        discSvc.setDiscType(env.getType());
        return discSvc.discoverSingleNetwork(network);
    }

    private static List<Host> discoverAllEnvNetworks(String envName) throws UnknownHostException {
        //setup the mongodb access
        mongoClient = new MongoClient("localhost");

        //get the environment object
        ApolloEnvironmentRepositoryMongoDBImpl netDB = new ApolloEnvironmentRepositoryMongoDBImpl(mongoClient, morphia, "Apollo");
        ApolloEnvironment env = netDB.getEnvByName(envName);
        if (env == null) {
            Logger.error("Error: Discover Single Network - environment with name {} not found", envName);
            return null;
        }

        //discover network
        discSvc.setDiscType(env.getType());
        List<ApolloNetwork> lNet = env.getNetworks();
        lNet.stream().forEach(l -> l.setDiscovered(false));
        return discSvc.discoverNetworks(lNet);
    }
}
