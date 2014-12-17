/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.BaseDiscoveryService;
import com.apollo.discover.nmap.DiscoveryServiceNmapImpl;
import com.google.inject.AbstractModule;

/**
 *
 * @author schari
 */
public class DiscoveryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BaseDiscoveryService.class).to(DiscoveryServiceNmapImpl.class);
    }
}
