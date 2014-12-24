/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.DiscoveryType;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
import com.apollo.discover.basediscover.DiscoveryService;
import java.util.ArrayList;
import java.util.List;
import static spark.Spark.*;
//import spark.Request;
//import spark.Response;

/**
 *
 * @author schari
 */
public class ApolloDiscover {

    private final static List<DiscoverNetworkRange> discoveryNetworks = new ArrayList();
    private final static boolean autoDiscover = true; //immediately do a discovery 
    private static final DiscoveryService discSvc = new DiscoveryService();

    public static void main(String[] args) {
        //for now we will listen on 1234, i.e., 'localhost:1234/...'
        setPort(1234);

        //currently we will default to physical discovery
        discSvc.setDiscType(DiscoveryType.PHYSICAL);

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
            String start = request.queryParams("start");
            String end = request.queryParams("end");

            DiscoverNetworkRange newNet = new DiscoverNetworkRange(start, end);
            discoveryNetworks.add(newNet);

            if (autoDiscover) {
                //start discovering...
                discSvc.discover(discoveryNetworks);
            }

            //since all the services are running on the same computer
            response.header("Access-Control-Allow-Origin", "*");

            //return the networks...
            return discoveryNetworks;
        }, new JsonTransformer());

    }
}
