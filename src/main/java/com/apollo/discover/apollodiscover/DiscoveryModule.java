/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.apollodiscover;

import com.apollo.discover.basediscover.BaseDiscoveryProvider;
import com.apollo.discover.basediscover.DiscoveryType;
import com.apollo.discover.nmap.DiscoveryProviderNmapImpl;
import com.google.inject.AbstractModule;

/**
 *
 * @author schari
 */
public class DiscoveryModule extends AbstractModule {

    private DiscoveryType type;

    public DiscoveryModule(DiscoveryType type) {
        this.type = type;
    }

    @Override
    protected void configure() {
        if (type == DiscoveryType.PHYSICAL) {
            bind(BaseDiscoveryProvider.class).to(DiscoveryProviderNmapImpl.class);
        } else if (type == DiscoveryType.RACKSPACE) {
            bind(BaseDiscoveryProvider.class).to(DiscoveryProviderNmapImpl.class);
        }
    }
}
