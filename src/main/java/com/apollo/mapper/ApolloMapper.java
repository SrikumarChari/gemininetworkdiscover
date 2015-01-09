/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.mapper;

import com.apollo.discover.nmap.Host;
import com.apollo.domain.dto.ApolloEnvironmentDTO;
import com.apollo.domain.dto.ApolloNetworkDTO;
import com.apollo.domain.dto.ApolloServerDTO;
import com.apollo.domain.model.ApolloEnvironment;
import com.apollo.domain.model.ApolloNetwork;
import com.apollo.domain.model.ApolloServer;
import com.google.inject.Inject;
import org.dozer.DozerBeanMapper;

/**
 *
 * @author schari
 */
public class ApolloMapper {

    @Inject
    private DozerBeanMapper mapper;

    public Host getHostFromDTO(ApolloServerDTO srvDTO) {
        Host newHost = mapper.map(srvDTO, Host.class);
        return newHost;
    }

    public ApolloServerDTO getDTOFromHost(Host host) {
        ApolloServerDTO newSrvDTO = mapper.map(host, ApolloServerDTO.class);
        return newSrvDTO;
    }
        public ApolloNetwork getNetworkFromDTO(ApolloNetworkDTO netDTO) {
        ApolloNetwork net = mapper.map(netDTO, ApolloNetwork.class);
        return net;
    }

    public ApolloNetworkDTO getDTOFromNetwork(ApolloNetwork net) {
        ApolloNetworkDTO netDTO = mapper.map(net, ApolloNetworkDTO.class);
        return netDTO;
    }

    public ApolloServer getServerFromDTO(ApolloServerDTO srvDTO) {
        ApolloServer srv = mapper.map(srvDTO, ApolloServer.class);
        return srv;
    }

    public ApolloServerDTO getDTOFromServer(ApolloServer srv) {
        ApolloServerDTO srvDTO = mapper.map(srv, ApolloServerDTO.class);
        return srvDTO;
    }
    
    public ApolloEnvironment getEnvFromDTO (ApolloEnvironmentDTO envDTO) {
        ApolloEnvironment env = mapper.map(envDTO, ApolloEnvironment.class);
        return env;
    }
    public ApolloEnvironmentDTO getDTOFromEnv(ApolloEnvironment env) {
        ApolloEnvironmentDTO envDTO = mapper.map(env, ApolloEnvironmentDTO.class);
        return envDTO;        
    }


}
