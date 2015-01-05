/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discovery.domain.model;

import java.net.InetAddress;

/**
 *
 * @author schari
 */
public class DiscoverNetwork {

    private InetAddress network;
    private Integer mask = 24;
    private boolean discoveryComplete = false;

    public DiscoverNetwork(InetAddress network) {
        this.network = network;
    }

    /**
     * @return the network
     */
    public InetAddress getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(InetAddress network) {
        this.network = network;
        setDiscoveryComplete(false);
    }

    /**
     * @return the mask
     */
    public Integer getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(Integer mask) {
        this.mask = mask;
        setDiscoveryComplete(false);
    }

    @Override
    public String toString() {
        return network.toString() + "/" + mask.toString();
    }

    public boolean isDiscoveryComplete() {
        return discoveryComplete;
    }

    public void setDiscoveryComplete(boolean discoveryComplete) {
        this.discoveryComplete = discoveryComplete;
    }
}
