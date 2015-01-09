/*
 * Provides information about which discovery type is currently for Apollo
 *
 */
package com.apollo.discover.basediscover;

import com.apollo.discover.apollodiscover.DiscoveryModule;
import com.apollo.discover.nmap.Host;
import com.apollo.domain.model.ApolloEnvironmentType;
import com.apollo.domain.model.ApolloNetwork;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class DiscoveryService {

    private ApolloEnvironmentType discType;
    private BaseDiscoveryProvider discoveryProvider = null;

    public ApolloEnvironmentType getDiscType() {
        return discType;
    }

    public void setDiscType(ApolloEnvironmentType discType) {
        this.discType = discType;
        //inject the discovery provider
        Injector injector = Guice.createInjector(new DiscoveryModule(discType));
        discoveryProvider = injector.getInstance(BaseDiscoveryProvider.class);
    }

    public void setDiscType(String dType) {
        for (ApolloEnvironmentType d : ApolloEnvironmentType.values()) {
            if (d.name().equals(dType)) {
                discType = d;

                //inject the discovery provider
                Injector injector = Guice.createInjector(new DiscoveryModule(discType));
                discoveryProvider = injector.getInstance(BaseDiscoveryProvider.class);
            }
        }
    }

    public List<Host> discoverNetworks(List<ApolloNetwork> networks) {
        if (discoveryProvider != null) {
            return discoveryProvider.discoverNetworks(networks);
        } else {
            Logger.error("Discovery type not set!!");
            return null;
        }
    }

    public List<Host> discoverSingleNetwork(ApolloNetwork network) {
        if (discoveryProvider != null) {
            return discoveryProvider.discoverSingleNetwork(network);
        } else {
            Logger.error("Discovery type not set!!");
            return null;
        }
    }
    
    public List<Host> fullDiscover (List<ApolloNetwork> networks) {
        if (discoveryProvider != null) {
            return discoveryProvider.fullDiscover(networks);
        } else {
            Logger.error("Discovery type not set!!");
            return null;
        }
    }
}
