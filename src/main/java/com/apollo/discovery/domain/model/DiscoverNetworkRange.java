/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.discovery.domain.model;

import com.google.common.net.InetAddresses;
import java.net.InetAddress;

/**
 *
 * @author schari
 */
public class DiscoverNetworkRange {

    private InetAddress start;
    private InetAddress end;
    private boolean discoveryComplete = false;

    public DiscoverNetworkRange(String start, String end) {
        this.start = InetAddresses.forString(start);
        this.end = InetAddresses.forString(end);
        this.discoveryComplete = false;
    }

    /**
     * @return the start
     */
    public InetAddress getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(InetAddress start) {
        this.start = start;
        this.discoveryComplete = false;
    }

    /**
     * @return the end
     */
    public InetAddress getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(InetAddress end) {
        this.end = end;
        this.discoveryComplete = false;
    }

    public boolean isDiscoveryComplete() {
        return discoveryComplete;
    }

    public void setDiscoveryComplete(boolean discoveryComplete) {
        this.discoveryComplete = discoveryComplete;
    }
}
