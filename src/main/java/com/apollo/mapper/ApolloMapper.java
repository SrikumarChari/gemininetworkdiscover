/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.mapper;

import com.apollo.discover.nmap.Host;
import com.apollo.domain.dto.ApolloServerDTO;
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
}
