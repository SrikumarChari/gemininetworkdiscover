/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discover.basediscover;

import java.net.InetAddress;

/**
 *
 * @author schari
 */
public class DiscoveryNetworkHost {

    InetAddress hostAddress;
    private boolean discoveryComplete = false;

    public DiscoveryNetworkHost(InetAddress hostAddress) {
        this.hostAddress = hostAddress;
    }

    public InetAddress getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(InetAddress hostAddress) {
        this.hostAddress = hostAddress;
        discoveryComplete = false;
    }

    public boolean isDiscoveryComplete() {
        return discoveryComplete;
    }

    public void setDiscoveryComplete(boolean discoveryComplete) {
        this.discoveryComplete = discoveryComplete;
    }

}
