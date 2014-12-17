/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.BaseDiscoveryService;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
import com.apollo.discover.nmap.DiscoveryServiceNmapImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
    private final static boolean autoDiscover = false;

    public static void main(String[] args) {
        //for now we will listen on 1234, i.e., 'localhost:1234/...'
        setPort(1234);

        //we only support nmap discovery for now... create the injector and bind the service
        //this will eventually be controlled by a value based on the user's environment
        Injector injector = Guice.createInjector(new DiscoveryModule());
        BaseDiscoveryService discSvc = injector.getInstance(DiscoveryServiceNmapImpl.class);

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
