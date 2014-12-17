/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.nmap;

import com.apollo.discover.basediscover.BaseDiscoveryService;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.net.InetAddresses;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author schari
 */
@Singleton
public class DiscoveryServiceNmapImpl implements BaseDiscoveryService {

    @Override
    public void discover(List<DiscoverNetworkRange> networks) {
        //build the network for nmap
        String nmapCmd = "";
        for (DiscoverNetworkRange dn : networks) {
            if (!dn.isDiscoveryComplete()) {
                String start = InetAddresses.toAddrString(dn.getStart());
                String end = InetAddresses.toAddrString(dn.getEnd());

                //get the last three letters of the end string
                Iterable<String> splits = Splitter.on(".").split(end);
                for (String s : splits) {
                    end = s;
                }
                nmapCmd += start + "-" + end + ",";
            }
        }

        if (nmapCmd.isEmpty()) {
            //no hosts to discover
            return;
        }

        NMap nmap = new NMap();
        try {
            //run the nmap.run command, returns a list of hosts
            List<Host> l = nmap.runCommand(nmapCmd);

            //store the servers in the database
            //the other service needs a REST API to add the servers
            //for now just print to the console
            System.out.println(Joiner.on(",").join(l));
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DiscoveryServiceNmapImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fullDiscover(List<DiscoverNetworkRange> networks) {
        //build the network for nmap
        String nmapCmd = "";
        for (DiscoverNetworkRange dn : networks) {
            String start = InetAddresses.toAddrString(dn.getStart());
            String end = InetAddresses.toAddrString(dn.getEnd());

            //get the last three letters of the end string
            Iterable<String> splits = Splitter.on(".").split(end);
            for (String s : splits) {
                end = s;
            }
            nmapCmd += start + "-" + end + ",";
        }

        if (nmapCmd.isEmpty()) {
            //no hosts to discover
            return;
        }

        NMap nmap = new NMap();
        try {
            //run the nmap.run command, returns a list of hosts
            List<Host> l = nmap.runCommand(nmapCmd);

            //store the servers in the database
            //the other service needs a REST API to add the servers
            //for now just print to the console
            System.out.println(Joiner.on(",").join(l));
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DiscoveryServiceNmapImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
