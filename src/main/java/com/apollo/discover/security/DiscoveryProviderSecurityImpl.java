/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.security;

import com.apollo.discover.basediscover.BaseDiscoveryProvider;
import com.apollo.discover.nmap.Host;
import com.apollo.domain.model.ApolloNetwork;
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
    public List<Host> discoverNetworks(List<ApolloNetwork> networks) {
        Logger.error("Not supported yet.");
        return null;
    }

    @Override
    public List<Host> fullDiscover(List<ApolloNetwork> networks) {
        Logger.error("Not supported yet.");
        return null;
    }

    @Override
    public List<Host> discoverSingleNetwork(ApolloNetwork network) {
        Logger.error("Not supported yet.");
        return null;
    }
}
