/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.security;

import com.apollo.discover.basediscover.BaseDiscoveryProvider;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
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
    public void discover(List<DiscoverNetworkRange> networks) {
        Logger.error("Not supported yet."); 
    }

    @Override
    public void fullDiscover(List<DiscoverNetworkRange> networks) {
        Logger.error("Not supported yet."); 
    }
    
}
