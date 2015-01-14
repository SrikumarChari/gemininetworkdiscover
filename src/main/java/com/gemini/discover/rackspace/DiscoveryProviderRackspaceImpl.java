/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemini.discover.rackspace;

import com.gemini.discover.basediscover.BaseDiscoveryProvider;
import com.gemini.discover.nmap.Host;
import com.gemini.domain.model.GeminiNetwork;
import java.util.List;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class DiscoveryProviderRackspaceImpl implements BaseDiscoveryProvider {

    //rackspace username and password
    private String userName = "";
    private String password = "";

    @Override
    public String discoveryDesc() {
        return "DiscoveryServiceRackspaceImpl discovers hosts given a range/set of IP Addresses for a specific rackspace customer";
    }

    @Override
    public List<Host> discoverNetworks(List<GeminiNetwork> networks) {
        Logger.error("Not supported yet.");
        return null;
    }

    @Override
    public List<Host> fullDiscover(List<GeminiNetwork> networks) {
        Logger.error("Not supported yet.");
        return null;
    }

    @Override
    public List<Host> discoverSingleNetwork(GeminiNetwork network) {
        Logger.error("Not supported yet.");
        return null;
    }

}
