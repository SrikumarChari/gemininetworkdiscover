package com.apollo.discover.nmap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Dahai Li
 *
 * Nmap uses nmap command to discover network hosts
 * @see Host
 */
public class NMap implements Discover {
	private String nmapCmd;

	public NMap () {
		this("/usr/local/bin/nmap");
	}
	
	public NMap (String nmapCmd) {
		this.nmapCmd = nmapCmd;
	}

    /**
     *
     * @param fileName string, the filename containing the output from running
     * "nmap -oX <filename> <network>"
     * @return list of Host's
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public List<Host> readXMLFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Host> result = new ArrayList<>();

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(fileName));

        /**
         * * A sample host element in the XML file:
         * <host starttime="1415838190" endtime="1415838197">
         * <status state="up" reason="syn-ack" reason_ttl="249" />
         * <address addr="172.23.204.252" addrtype="ipv4" />
         * <hostnames>
         * <hostname name="asav" type="user" />
         * <hostname name="asav" type="PTR" />
         * </hostnames>
         * <ports>
         * <extraports state="filtered" count="98">
         * <extrareasons reason="no-responses" count="98" />
         * </extraports>
         * <port protocol="tcp" portid="23">
         * <state state="open" reason="syn-ack" reason_ttl="249" />
         * <service name="telnet" method="table" conf="3" />
         * </port>
         * <port protocol="tcp" portid="443">
         * <state state="open" reason="syn-ack" reason_ttl="249" />
         * <service name="https" method="table" conf="3" />
         * </port>
         * </ports>
         * <os>
         * <portused state="open" proto="tcp" portid="23" />
         * <osmatch name="Cisco Adaptive Security Appliance (PIX OS 8.4)"
         * accuracy="100" line="16369">
         * <osclass type="firewall" vendor="Cisco" osfamily="PIX OS"
         * osgen="8.X" accuracy="100">
         * <cpe>cpe:/o:cisco:pix_os:8</cpe>
         * </osclass>
         * </osmatch>
         * </os>
         * <tcpsequence index="258" difficulty="Good luck!"
         * values="CD463246,6C71F087,4194D3F9,7184CECD,45F00C,B1A694D2" />
         * <ipidsequence class="Randomized" values="D952,E7DC,9400,8664,B2AA,D92A" />
         * <tcptssequence class="none returned (unsupported)" />
         * <times srtt="3381" rttvar="1971" to="100000" />
         * </host>
		**
         */
        NodeList hosts = doc.getElementsByTagName("host");
        for (int i = 0; i < hosts.getLength(); i++) {
            Element host = (Element) hosts.item(i);
            result.add(new Host(getHostAddress(host), getHostName(host),
                    getHostOS(host)));
        }
        Host[] r = new Host[result.size()];
        result.toArray(r);
        return result;
    }

    public List<Host> runCommand(String network) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        List<Host> result = null;
        String fileName = getTempFileName();
        /**
         * *
         * -O option is to detect OS. It can only be run under privileged mode.
         */
        // nmap does not take target in the form of "host1,host2", but "host1" "host2".
        String[] targets = network.split(",");
        String[] argv = new String[5+targets.length];
    	System.arraycopy(targets, 0, argv, 5, targets.length);
    	argv[0] = nmapCmd;
    	argv[1] = "-F";
    	argv[2] = "-O";
    	argv[3] = "-oX";
    	argv[4] = fileName;

    	//assuming you are able to run "sudo nmap" without password. See /etc/sudoers
        if (new Shell().sudoRun(null, argv) == 0) {
            result = readXMLFile(fileName);
        }
        return result;
    }

    static private String getTempFileName() throws IOException {
        File f = File.createTempFile("nmap", null);
        String result = f.getAbsolutePath();
        f.deleteOnExit();
        return result;
    }

    /**
     * Format of the XML:
     * <address addr="172.23.204.252" addrtype="ipv4" />
     *
     * @param host Element
     * @return String, the host IP address
     */
    private String getHostAddress(Element host) {
        Element address = (Element) host.getElementsByTagName("address").item(0);
        return address.getAttribute("addr").trim();
    }

    /**
     * Format of the XML:
     * <os>
     * <portused state="open" proto="tcp" portid="23" />
     * <osmatch name="Cisco Adaptive Security Appliance (PIX OS 8.4)"
     * accuracy="100" line="16369">
     * <osclass type="firewall" vendor="Cisco" osfamily="PIX OS"
     * osgen="8.X" accuracy="100">
     * <cpe>cpe:/o:cisco:pix_os:8</cpe>
     * </osclass>
     * </osmatch>
     * </os>
     *
     * @param host Element
     * @return String the OS of the host
     */
    private String getHostOS(Element host) {
        Element os = (Element) host.getElementsByTagName("os").item(0);
        if (os == null) {
            return null;
        }
        Element osMatch = (Element) os.getElementsByTagName("osmatch").item(0);
        return osMatch.getAttribute("name").trim();
    }

    private String getHostName(Element host) {
        Element hostnames = (Element) host.getElementsByTagName("hostnames")
                .item(0);
        Element hostname = (Element) hostnames.getElementsByTagName("hostname")
                .item(0);
        return hostname != null ? hostname.getAttribute("name").trim() : null;
    }

    @Override
    public List<Host> discover(Object target) {
        try {
            return runCommand((String) target);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void printHelp() {
        System.out.print("Usage: \n  java nmap -f <filename>, or\n  java nmap -n <network>\n"
                + "Example:\n  java nmap nmap-output.xml\n  java nmap -n www.cisco.com/24\n");
    }

    /**
     * *
     * For test only
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                printHelp();
                System.exit(-1);
            }

            List<Host> hosts = null;
            NMap nmap = new NMap();
            switch ((args[0])) {
                case "-f":
                    hosts = nmap.readXMLFile(args[1]);
                    break;
                case "-n":
                    hosts = nmap.discover(args[1]);
                    break;
                default:
                    printHelp();
                    System.exit(-1);
            }
            if (hosts == null || hosts.isEmpty()) {
                System.out.println("No host detected.");
                System.exit(1);
            }
            for (Host host : hosts) {
                System.out.println(host.toString());
            }
            System.exit(0);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //e.printStackTrace();
            System.exit(-1);
        }
    }
}
