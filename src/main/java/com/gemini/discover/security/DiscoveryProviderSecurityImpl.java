/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemini.discover.security;

import com.gemini.discover.basediscover.BaseDiscoveryProvider;
import com.gemini.discover.nmap.Host;
import com.gemini.domain.model.GeminiNetwork;
import java.util.List;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class DiscoveryProviderSecurityImpl implements BaseDiscoveryProvider {

    @Override
    public String discoveryDesc() {
        return "DiscoveryProviderSecurityImpl discovers security information for given security device. Currently"
                + "supports Cisco ASA";
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
