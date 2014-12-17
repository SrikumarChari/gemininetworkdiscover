/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.nmap;

import com.apollo.discover.basediscover.BaseDiscoveryService;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
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
                nmapCmd += dn.getStart() + "-" + dn.getEnd();
                dn.setDiscoveryComplete(true);
            }
        }
        
        NMap nmap = new NMap();
        try {
            nmap.runCommand(nmapCmd);
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DiscoveryServiceNmapImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fullDiscover(List<DiscoverNetworkRange> networks) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
