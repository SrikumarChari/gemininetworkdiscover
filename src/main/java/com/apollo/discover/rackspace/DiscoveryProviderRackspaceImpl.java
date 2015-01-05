/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.rackspace;

import com.apollo.discover.basediscover.BaseDiscoveryProvider;
import com.apollo.discovery.domain.model.DiscoverNetworkRange;
import com.apollo.discover.nmap.Host;
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
    public List<Host> discover(List<DiscoverNetworkRange> networks) {
        System.out.println("Not supported yet.");
        return null;
    }

    @Override
    public List<Host> fullDiscover(List<DiscoverNetworkRange> networks) {
        System.out.println("Not supported yet.");
        return null;

    }

}
