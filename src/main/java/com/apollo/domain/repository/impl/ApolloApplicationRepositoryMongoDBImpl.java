/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.domain.repository.impl;

import com.apollo.common.repository.impl.BaseRepositoryMongoDBImpl;
import com.apollo.domain.model.ApolloApplication;
import com.apollo.domain.model.ApolloNetwork;
import com.apollo.domain.model.ApolloServer;
import com.apollo.domain.repository.ApolloApplicationRepository;
import com.mongodb.MongoClient;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.mongodb.morphia.Morphia;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class ApolloApplicationRepositoryMongoDBImpl extends BaseRepositoryMongoDBImpl<ApolloApplication, String>
        implements ApolloApplicationRepository {

    public ApolloApplicationRepositoryMongoDBImpl(MongoClient mongoClient, Morphia morphia, String dbName) {
        //create the database and collection
        super(ApolloApplication.class, mongoClient, morphia, dbName);
    }

    //find an applicaiton by name
    public ApolloApplication getAppByName(String appName) {
        Logger.debug("get app by name :{}", ToStringBuilder.reflectionToString(appName, ToStringStyle.MULTI_LINE_STYLE));
        return findOne(getDatastore().createQuery(ApolloApplication.class).filter("name", appName));
    }

    public List<ApolloNetwork> getAppNetworks(String appName) {
        ApolloApplication a = getAppByName(appName);
        return a.getNetworks();
    }

    public List<ApolloServer> getAppServers(String appName) {
        ApolloApplication a = getAppByName(appName);
        return a.getServers();
    }
    
    public List<ApolloServer> getNetworkServers (String appName, String netStart, String netEnd) {
        List<ApolloNetwork> networks = getAppNetworks(appName);
        
        List<ApolloNetwork> net = networks.stream()
                .filter(n -> n.getStart().getHostAddress().equals(netStart))
                .filter(n -> n.getEnd().getHostAddress().equals(netEnd))
                .collect(Collectors.toList());

        if (net != null) {
            return net.get(0).getServers();
        }

//        for (ApolloNetwork n : networks) {
//            if (n.getStart().getHostAddress().equals(netStart) && n.getEnd().getHostAddress().equals(netEnd)) {
//                return n.getServers();
//            }
//        }

        return null;
    }
}
