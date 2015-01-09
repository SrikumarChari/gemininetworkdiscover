/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.domain.model;

import com.apollo.common.repository.EntityMongoDB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.pmw.tinylog.Logger;

/**
 *
 * @author schari
 */
@Entity
public class ApolloEnvironment extends EntityMongoDB {

    private ApolloEnvironmentType type;
    private String name;

    @Embedded
    private List<ApolloApplication> applications = Collections.synchronizedList(new ArrayList());

    @Embedded
    private List<ApolloNetwork> networks;

    @Embedded
    private List<ApolloServer> servers;

    public ApolloEnvironmentType getType() {
        return type;
    }

    public void setType(ApolloEnvironmentType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApolloApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<ApolloApplication> applications) {
        this.applications = applications;
    }

    public boolean addApplication(ApolloApplication app) {
        if (applications.contains(app)) {
            Logger.info("Did not add application:{}  already exists in environment {}", app.getName(), getName());
            return false;
        } else {
            if (!applications.add(app)) {
                Logger.debug("Failed to add application: {} into environment {}", app.getName(), getName());
                return false;
            } else {
                //s.setApp(this);
                Logger.debug("Successfully added server: {} to application: {}", app.getName(), getName());
                return true;
            }
        }
    }

    public boolean deleteApplication(ApolloApplication app) {
        if (applications.contains(app)) {
            if (!applications.remove(app)) {
                Logger.error("Failed to delete application: {} from environment: {}", app.getName(), getName());
                return false;
            } else {
                //remove the connection between this application and the deleted server
                //s.setApp(null);
                Logger.debug("Successfull deleted application: {} from environment: {}", app.getName(), getName());
                return true;
            }
        } else {
            Logger.error("Did not delete application: {} - does not exist in environment {}", app.getName(), getName());
            return false;
        }
    }

    public List<ApolloNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(List<ApolloNetwork> networks) {
        this.networks = networks;
    }

    public boolean addNetwork(ApolloNetwork n) {
        if (networks.contains(n)) {
            Logger.error("Did not add network start: {} end: {}, already exists in environment {}", n.getStart(), n.getEnd(), getName());
            return false;
        } else {
            if (!networks.add(n)) {
                Logger.error("Failed to add network, start: {} end: {} from environment {}", n.getStart(), n.getEnd(), getName());
                return false;
            } else {
                //n.setApp(this);
                Logger.debug("Successfully added network, start: {} end: {} to environment {}", n.getStart(), n.getEnd(), getName());
                return true;
            }
        }
    }

    public boolean deleteNetwork(ApolloNetwork n) {
        if (networks.contains(n)) {
            if (!networks.remove(n)) {
                Logger.error("Failed to delete network, start: {} end: {} from environment {}", n.getStart(), n.getEnd(), getName());
                return false;
            } else {
                //remove the connection between this application and the deleted network
                //n.setApp(null);
                Logger.debug("Successfully deleted network, start: {} end: {} from environment {}", n.getStart(), n.getEnd(), getName());
                return true;
            }
        } else {
            Logger.info("Did not delete network, start: {} end: {} - network does not exist in environment {}", n.getStart(), n.getEnd(), getName());
            return false;
        }
    }

    public List<ApolloServer> getServers() {
        return servers;
    }

    public void setServers(List<ApolloServer> servers) {
        this.servers = servers;
    }

    public boolean addServer(ApolloServer s) {
        if (servers.contains(s)) {
            Logger.info("Did not add server:{}  already exists in environment {}", s.getName(), getName());
            return false;
        } else {
            if (!servers.add(s)) {
                Logger.debug("Failed to add server: {} to environment: {}", s.getName(), getName());
                return false;
            } else {
                //s.setApp(this);
                Logger.debug("Successfully added server: {} to environment: {}", s.getName(), getName());
                return true;
            }
        }
    }

    public boolean deleteServer(ApolloServer s) {
        if (servers.contains(s)) {
            if (!servers.remove(s)) {
                Logger.error("Failed to delete server: {} from environment: {}", s.getName(), getName());
                return false;
            } else {
                //remove the connection between this application and the deleted server
                //s.setApp(null);
                Logger.debug("Successfull deleted server: {} from environment {}", s.getName(), getName());
                return true;
            }
        } else {
            Logger.error("Did not delete server: {} - server does not exist in environment {}", s.getName(), getName());
            return false;
        }
    }
}
