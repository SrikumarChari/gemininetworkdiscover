package com.apollo.discover.nmap;

/**
 * 
 * @author Dahai Li
 * 
 * Discover is an interface for discover network hosts.
 */
public interface Discover {
	Host[] discover (Object target);
}
