/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.BaseDiscoveryService;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
import com.google.inject.Inject;
import java.util.List;

/**
 *
 * @author schari
 */
public class DiscoveryService {
    private BaseDiscoveryService discoveryService;
    
    @Inject
    public void setDiscoveryService(BaseDiscoveryService svc) {
        this.discoveryService = svc;
    }
    
    //only discover new networks
    public void discover (List<DiscoverNetworkRange> networks) {
        discoveryService.discover(networks);
    }
    
    //discover all networks, i.e., a refresh
    public void fullDiscover(List<DiscoverNetworkRange> networks) {
        discoveryService.fullDiscover(networks);
    }
}
