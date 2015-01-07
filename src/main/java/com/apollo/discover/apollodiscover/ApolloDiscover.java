/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.DiscoveryType;
import com.apollo.discovery.domain.model.DiscoverNetworkRange;
import com.apollo.discover.basediscover.DiscoveryService;
import com.apollo.discover.nmap.Host;
import com.apollo.domain.dto.ApolloServerDTO;
import com.apollo.mapper.ApolloMapper;
import com.apollo.mapper.ApolloMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.List;
import static spark.Spark.*;
import org.pmw.tinylog.Logger;

//import spark.Request;
//import spark.Response;
/**
 *
 * @author schari
 */
public class ApolloDiscover {

    private final static List<DiscoverNetworkRange> discoveryNetworks = new ArrayList();
    private static final DiscoveryService discSvc = new DiscoveryService(DiscoveryType.PHYSICAL);

    public static void main(String[] args) {
        //for now we will listen on 1234, i.e., 'localhost:1234/...'
        setPort(1234);

        //currently we will default to physical discovery
        //discSvc.setDiscType(DiscoveryType.PHYSICAL);

        //create the mapper
        Injector injector = Guice.createInjector(new ApolloMapperModule());
        ApolloMapper mapper = injector.getInstance(ApolloMapper.class);

        //change the discovery type in the discovery service
        post("/disctype", "application/json", (request, response) -> {
            String dType = request.queryParams("disctype");
            discSvc.setDiscType(dType);
            return "Successfully changed discovery type";
        }, new JsonTransformer());

        get("/networks", "application/json", (request, response) -> {
            //return the networks...
            return discoveryNetworks;
        }, new JsonTransformer());

        post("/networks", "application/json", (request, response) -> {
            //get the start and end addresses
            String start = request.queryParams("netstart");
            String end = request.queryParams("netend");

            DiscoverNetworkRange newNet = new DiscoverNetworkRange(start, end);
            discoveryNetworks.add(newNet);

            //start discovering...
            List<Host> hosts = discSvc.discover(discoveryNetworks);
            if (hosts == null) {
                Logger.info("No networks discovered for start: {} and end: {}", start, end);
                response.status(200);
                return "No networks discovered for start: " + start + " and end: " + end;
            }
            List<ApolloServerDTO> dtoSrvs = new ArrayList();
            hosts.stream().forEach((h) -> {
                dtoSrvs.add(mapper.getDTOFromHost(h));
            });

            //since all the services are running on the same computer
            response.header("Access-Control-Allow-Origin", "*");

            //return the networks...
            response.status(200);
            return dtoSrvs;
        }, new JsonTransformer());
    }
}
