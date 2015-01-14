/*
 * Provides information about which discovery type is currently set for the environment and also 
 * invokes the appropriate discovery function. Note - it uses google guice to inject
 * the correct discovery service based on the discovery type
 *
 */
package com.gemini.discover.basediscover;

import com.gemini.discover.networkdiscover.DiscoveryModule;
import com.gemini.discover.nmap.Host;
import com.gemini.domain.model.GeminiEnvironmentType;
import com.gemini.domain.model.GeminiNetwork;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class DiscoveryService {

    private GeminiEnvironmentType discType;
    private BaseDiscoveryProvider discoveryProvider = null;

    public GeminiEnvironmentType getDiscType() {
        return discType;
    }

    public void setDiscType(GeminiEnvironmentType discType) {
        this.discType = discType;
        //inject the discovery provider
        Injector injector = Guice.createInjector(new DiscoveryModule(discType));
        discoveryProvider = injector.getInstance(BaseDiscoveryProvider.class);
    }

    public void setDiscType(String dType) {
        for (GeminiEnvironmentType d : GeminiEnvironmentType.values()) {
            if (d.name().equals(dType)) {
                discType = d;

                //inject the discovery provider
                Injector injector = Guice.createInjector(new DiscoveryModule(discType));
                discoveryProvider = injector.getInstance(BaseDiscoveryProvider.class);
            }
        }
    }

    public List<Host> discoverNetworks(List<GeminiNetwork> networks) {
        if (discoveryProvider != null) {
            return discoveryProvider.discoverNetworks(networks);
        } else {
            Logger.error("Discovery type not set!!");
            return null;
        }
    }

    public List<Host> discoverSingleNetwork(GeminiNetwork network) {
        if (discoveryProvider != null) {
            return discoveryProvider.discoverSingleNetwork(network);
        } else {
            Logger.error("Discovery type not set!!");
            return null;
        }
    }
    
    public List<Host> fullDiscover (List<GeminiNetwork> networks) {
        if (discoveryProvider != null) {
            return discoveryProvider.fullDiscover(networks);
        } else {
            Logger.error("Discovery type not set!!");
            return null;
        }
    }
}
