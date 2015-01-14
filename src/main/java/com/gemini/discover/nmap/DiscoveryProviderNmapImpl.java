/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemini.discover.nmap;

import com.gemini.discover.basediscover.BaseDiscoveryProvider;
import com.gemini.domain.model.GeminiNetwork;
import com.google.common.base.Splitter;
import com.google.common.net.InetAddresses;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
@Singleton
public class DiscoveryProviderNmapImpl implements BaseDiscoveryProvider {

    static Integer MIN_NETWORK_MASK = 23;

    @Override
    public List<Host> discoverNetworks(List<GeminiNetwork> networks) {
        //build the network for nmap
        String nmapCmd = "";
//        networks.stream()
//                .filter(dn -> dn.isDiscoveryComplete())
//                .forEach((dn) -> {
//                    String start = InetAddresses.toAddrString(dn.getStart());
//                    String end = InetAddresses.toAddrString(dn.getEnd());
//
//                    //get the last three letters of the end string
//                    Iterable<String> splits = Splitter.on(".").split(end);
//                    for (String s : splits) {
//                        end = s;
//                    }
//                    nmapCmd = start + "-" + end + ",";
//                });

        for (GeminiNetwork dn : networks) {
            if (!dn.isDiscovered()) {
                //parse the discovery network from the network object... it can be one or all of the 
                //start/end, network/mask and host
                String start = InetAddresses.toAddrString(dn.getStart());
                String end = InetAddresses.toAddrString(dn.getEnd());
                if (!start.isEmpty() && !end.isEmpty()) {
                    //get the last three letters of the end string
                    List<String> splits = Splitter.on(".").splitToList(end);
                    nmapCmd += start + "-" + splits.get(splits.size() - 1);
                }

                String net = InetAddresses.toAddrString(dn.getNetwork());
                String mask = String.valueOf(dn.getMask());
                if (!net.isEmpty() && dn.getMask() > MIN_NETWORK_MASK) {
                    if (!nmapCmd.isEmpty()) {
                        nmapCmd += ",";
                    }
                    nmapCmd += net + "/" + mask;
                }

                String host = dn.getHost();
                if (!host.isEmpty()) {
                    if (!nmapCmd.isEmpty()) {
                        nmapCmd += ",";
                    }
                    nmapCmd += host;
                }
            }
        }

        if (nmapCmd.isEmpty()) {
            //no hosts to discover
            Logger.error("NMAP Discovery: No hosts to discover");
            return null;
        }

        NMap nmap = new NMap();
        try {
            //run the nmap.run command, returns a list of hosts
            List<Host> l = nmap.runCommand(nmapCmd);

            //store the servers in the database
            //the other service needs a REST API to add the servers
            //for now just print to the console
            //System.out.println(Joiner.on(",").join(l));
            return l;
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException ex) {
            Logger.error("NMAP Discovery failed: {}", ex.toString());
            return null;
        }
    }

    @Override
    public List<Host> discoverSingleNetwork(GeminiNetwork network) {
        //build the network for nmap
        String nmapCmd = "";

        if (network.isDiscovered()) {
            //no hosts to discover
            Logger.error("NMAP Discovery: Network already discovered - reset flag and try again");
            return null;
        }

        //parse the discovery network from the network object... it can be one or all of the 
        //start/end, network/mask and host
        String start = InetAddresses.toAddrString(network.getStart());
        String end = InetAddresses.toAddrString(network.getEnd());
        if (!start.isEmpty() && !end.isEmpty()) {
            //get the last three letters of the end string
            List<String> splits = Splitter.on(".").splitToList(end);
            nmapCmd += start + "-" + splits.get(splits.size() - 1);
        }

        String net = InetAddresses.toAddrString(network.getNetwork());
        String mask = String.valueOf(network.getMask());
        if (!net.isEmpty() && network.getMask() > MIN_NETWORK_MASK) {
            if (!nmapCmd.isEmpty()) {
                nmapCmd += ",";
            }
            nmapCmd += net + "/" + mask;
        }

        String host = network.getHost();
        if (!host.isEmpty()) {
            if (!nmapCmd.isEmpty()) {
                nmapCmd += ",";
            }
            nmapCmd += host;
        }

        if (nmapCmd.isEmpty()) {
            //no hosts to discover
            Logger.error("NMAP Discovery: No hosts to discover");
            return null;
        }

        NMap nmap = new NMap();
        try {
            //run the nmap.run command, returns a list of hosts
            return nmap.runCommand(nmapCmd);
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException ex) {
            Logger.error("NMAP Discovery failed: {}", ex.toString());
            return null;
        }
    }

    @Override
    public List<Host> fullDiscover(List<GeminiNetwork> networks) {
        //build the network for nmap
        String nmapCmd = "";
        for (GeminiNetwork dn : networks) {
                //parse the discovery network from the network object... it can be one or all of the 
            //start/end, network/mask and host
            String start = InetAddresses.toAddrString(dn.getStart());
            String end = InetAddresses.toAddrString(dn.getEnd());
            if (!start.isEmpty() && !end.isEmpty()) {
                //get the last three letters of the end string
                List<String> splits = Splitter.on(".").splitToList(end);
                nmapCmd += start + "-" + splits.get(splits.size() - 1);
            }

            String net = InetAddresses.toAddrString(dn.getNetwork());
            String mask = String.valueOf(dn.getMask());
            if (!net.isEmpty() && dn.getMask() > MIN_NETWORK_MASK) {
                if (!nmapCmd.isEmpty()) {
                    nmapCmd += ",";
                }
                nmapCmd += net + "/" + mask;
            }

            String host = dn.getHost();
            if (!host.isEmpty()) {
                if (!nmapCmd.isEmpty()) {
                    nmapCmd += ",";
                }
                nmapCmd += host;
            }
        }

        if (nmapCmd.isEmpty()) {
            //no hosts to discover
            return null;
        }

        NMap nmap = new NMap();
        try {
            //run the nmap.run command, returns a list of hosts
            return nmap.runCommand(nmapCmd);
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException ex) {
            Logger.error("NMAP Discovery failed: {}", ex.toString());
            return null;
        }
    }

    @Override
    public String discoveryDesc() {
        return "DiscoveryServiceNmapImpl discovers physical hosts given a Class C network or range/set of IP Addresses";
    }
}
