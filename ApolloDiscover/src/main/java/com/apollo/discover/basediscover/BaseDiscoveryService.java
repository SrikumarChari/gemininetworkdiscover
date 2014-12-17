/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.basediscover;

import java.util.List;

/**
 *
 * @author schari
 */
public interface BaseDiscoveryService {
    public void discover (List<DiscoverNetworkRange> networks); //only discover new networks
    public void fullDiscover(List<DiscoverNetworkRange> networks); //discover all networks, i.e., a refresh
}
