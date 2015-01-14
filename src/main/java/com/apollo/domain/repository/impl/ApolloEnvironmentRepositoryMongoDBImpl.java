/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.domain.repository.impl;

import com.apollo.common.repository.impl.BaseRepositoryMongoDBImpl;
import com.apollo.domain.model.ApolloEnvironment;
import com.apollo.domain.model.ApolloApplication;
import com.apollo.domain.model.ApolloNetwork;
import com.apollo.domain.model.ApolloServer;
import com.apollo.domain.repository.ApolloEnvironmentRepository;
import com.mongodb.MongoClient;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.mongodb.morphia.Morphia;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
public class ApolloEnvironmentRepositoryMongoDBImpl extends BaseRepositoryMongoDBImpl<ApolloEnvironment, String>
        implements ApolloEnvironmentRepository {

    public ApolloEnvironmentRepositoryMongoDBImpl(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(ApolloEnvironment.class, mongoClient, morphia, dbName);
    }

    public ApolloEnvironment getEnvByName(String envName) {
        Logger.debug("get app by name :{}", envName);
        return findOne(getDatastore().createQuery(ApolloEnvironment.class).filter("name", envName));
    }

    public List<ApolloApplication> getEnvApps(String envName) {
        Logger.debug("get env applications: {}", envName);
        return getEnvByName(envName).getApplications();
    }

    public List<ApolloNetwork> getEnvNetworks(String envName) {
        Logger.debug("get env networks: {}", envName);
        return getEnvByName(envName).getNetworks();
    }

    public List<ApolloServer> getEnvServers(String envName) {
        Logger.debug("get env servers: {}", envName);
        return getEnvByName(envName).getServers();
    }
}
