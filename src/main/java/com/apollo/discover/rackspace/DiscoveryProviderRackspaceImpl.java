/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.rackspace;

import com.apollo.discover.basediscover.BaseDiscoveryProvider;
import com.apollo.discover.basediscover.DiscoverNetworkRange;
import java.util.List;

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
    public void discover(List<DiscoverNetworkRange> networks) {
        System.out.println("Not supported yet."); 
    }

    @Override
    public void fullDiscover(List<DiscoverNetworkRange> networks) {
        System.out.println("Not supported yet."); 
    }
    
}
