package com.apollo.discover.nmap;

/**
 *
 * @author Dahai Li
 *
 * Host represent a host device
 */
public class Host {

    private String address = "";
    private String name = "";
    private String os = "";

    public Host(String address, String name, String os) {
        this.address = address;
        this.name = name;
        this.os = os;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (!(obj instanceof Host)) {
            return false;
        }
        Host other = (Host) obj;
        return address.equals(other.address)
                && name.equals(other.name)
                && os.equals(other.os);
    }
    
    @Override
    public String toString() {
        return "address:" + address + "name:" + name + "OS:" + os;
    }
}
