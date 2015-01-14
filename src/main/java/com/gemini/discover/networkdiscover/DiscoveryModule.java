/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemini.discover.networkdiscover;

import com.gemini.discover.basediscover.BaseDiscoveryProvider;
import com.gemini.domain.model.GeminiEnvironmentType;
import com.gemini.discover.nmap.DiscoveryProviderNmapImpl;
import com.google.inject.AbstractModule;

/**
 *
 * @author schari
 */
public class DiscoveryModule extends AbstractModule {

    private GeminiEnvironmentType type;

    public DiscoveryModule(GeminiEnvironmentType type) {
        this.type = type;
    }

    @Override
    protected void configure() {
        if (type == GeminiEnvironmentType.PHYSICAL) {
            bind(BaseDiscoveryProvider.class).to(DiscoveryProviderNmapImpl.class);
        } else if (type == GeminiEnvironmentType.RACKSPACE) {
            bind(BaseDiscoveryProvider.class).to(DiscoveryProviderNmapImpl.class);
        }
    }
}
