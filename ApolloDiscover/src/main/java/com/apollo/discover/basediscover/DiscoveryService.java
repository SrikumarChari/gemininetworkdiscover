/*
 * Provides information about which discovery type is currently for Apollo
 *
 */
package com.apollo.discover.basediscover;

import com.apollo.discover.apollodiscover.DiscoveryModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class DiscoveryService {

    private DiscoveryType discType;
    private BaseDiscoveryProvider discoveryProvider = null;

    public DiscoveryType getDiscType() {
        return discType;
    }

    public void setDiscType(DiscoveryType discType) {
        this.discType = discType;

    }

    public void setDiscType(String dType) {
        for (DiscoveryType d : DiscoveryType.values()) {
            if (d.name().equals(dType)) {
                discType = d;

                //inject the discovery provider
                Injector injector = Guice.createInjector(new DiscoveryModule(discType));
                discoveryProvider = injector.getInstance(BaseDiscoveryProvider.class);
            }
        }
    }

    public void discover(List<DiscoverNetworkRange> networks) {
        if (discoveryProvider != null) {
            discoveryProvider.discover(networks);
        } else {
            Logger.error("Discovery type not set!!");
        }
    }
}
