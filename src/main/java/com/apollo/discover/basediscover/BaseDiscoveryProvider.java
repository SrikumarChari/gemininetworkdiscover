/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.basediscover;

import com.apollo.discovery.domain.model.DiscoverNetworkRange;
import com.apollo.discover.nmap.Host;
import java.util.List;

/**
 *
 * @author schari
 */
public interface BaseDiscoveryProvider {
    public String discoveryDesc();
    public List<Host> discover (List<DiscoverNetworkRange> networks); //only discover new networks
    public List<Host> fullDiscover(List<DiscoverNetworkRange> networks); //discover all networks, i.e., a refresh
}
