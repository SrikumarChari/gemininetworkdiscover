/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemini.discover.basediscover;

import com.gemini.discover.nmap.Host;
import com.gemini.domain.model.GeminiNetwork;
import java.util.List;

/**
 *
 * @author schari
 */
public interface BaseDiscoveryProvider {
    public String discoveryDesc();
    public List<Host> discoverSingleNetwork(GeminiNetwork network);
    public List<Host> discoverNetworks (List<GeminiNetwork> networks); //only discover new networks
    public List<Host> fullDiscover(List<GeminiNetwork> networks); //discover all networks, i.e., a refresh
}
