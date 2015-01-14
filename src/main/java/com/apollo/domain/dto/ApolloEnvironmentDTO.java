/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.domain.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author schari
 */
public class ApolloEnvironmentDTO extends BaseDTO {

    private String type;
    private String name;

    @Embedded
    private List<ApolloApplicationDTO> applications = Collections.synchronizedList(new ArrayList());

    @Embedded
    private List<ApolloNetworkDTO> networks;

    @Embedded
    private List<ApolloServerDTO> servers;

}
