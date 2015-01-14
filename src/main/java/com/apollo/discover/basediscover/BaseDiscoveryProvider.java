/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.basediscover;

import com.apollo.discover.nmap.Host;
import com.apollo.domain.model.ApolloNetwork;
import java.util.List;

/**
 *
 * @author schari
 */
public interface BaseDiscoveryProvider {
    public String discoveryDesc();
    public List<Host> discoverSingleNetwork(ApolloNetwork network);
    public List<Host> discoverNetworks (List<ApolloNetwork> networks); //only discover new networks
    public List<Host> fullDiscover(List<ApolloNetwork> networks); //discover all networks, i.e., a refresh
}
