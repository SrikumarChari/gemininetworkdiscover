package com.apollo.discover.nmap;

import java.util.List;

/**
 * 
 * @author Dahai Li
 * 
 * Discover is an interface for discover network hosts.
 */
public interface Discover {
	List<Host> discover (Object target);
}
