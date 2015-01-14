/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemini.mapper;

import com.gemini.discover.nmap.Host;
import com.gemini.domain.dto.GeminiEnvironmentDTO;
import com.gemini.domain.dto.GeminiNetworkDTO;
import com.gemini.domain.dto.GeminiServerDTO;
import com.gemini.domain.model.GeminiEnvironment;
import com.gemini.domain.model.GeminiNetwork;
import com.gemini.domain.model.GeminiServer;
import com.google.inject.Inject;
import org.dozer.DozerBeanMapper;

/**
 *
 * @author schari
 */
public class GeminiMapper {

    @Inject
    private DozerBeanMapper mapper;

    public Host getHostFromDTO(GeminiServerDTO srvDTO) {
        Host newHost = mapper.map(srvDTO, Host.class);
        return newHost;
    }

    public GeminiServerDTO getDTOFromHost(Host host) {
        GeminiServerDTO newSrvDTO = mapper.map(host, GeminiServerDTO.class);
        return newSrvDTO;
    }
        public GeminiNetwork getNetworkFromDTO(GeminiNetworkDTO netDTO) {
        GeminiNetwork net = mapper.map(netDTO, GeminiNetwork.class);
        return net;
    }

    public GeminiNetworkDTO getDTOFromNetwork(GeminiNetwork net) {
        GeminiNetworkDTO netDTO = mapper.map(net, GeminiNetworkDTO.class);
        return netDTO;
    }

    public GeminiServer getServerFromDTO(GeminiServerDTO srvDTO) {
        GeminiServer srv = mapper.map(srvDTO, GeminiServer.class);
        return srv;
    }

    public GeminiServerDTO getDTOFromServer(GeminiServer srv) {
        GeminiServerDTO srvDTO = mapper.map(srv, GeminiServerDTO.class);
        return srvDTO;
    }
    
    public GeminiEnvironment getEnvFromDTO (GeminiEnvironmentDTO envDTO) {
        GeminiEnvironment env = mapper.map(envDTO, GeminiEnvironment.class);
        return env;
    }
    public GeminiEnvironmentDTO getDTOFromEnv(GeminiEnvironment env) {
        GeminiEnvironmentDTO envDTO = mapper.map(env, GeminiEnvironmentDTO.class);
        return envDTO;        
    }


}
