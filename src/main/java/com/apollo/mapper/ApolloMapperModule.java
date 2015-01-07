/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apollo.mapper;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.dozer.DozerBeanMapper;

/**
 *
 * @author schari
 */
public class ApolloMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        bind(DozerBeanMapper.class).toInstance(mapper);
        bind(ApolloMapper.class).in(Singleton.class);
    }
}
