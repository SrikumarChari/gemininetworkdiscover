package com.apollo.discover.nmap;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.apollo.discover.nmap.Shell;

/**
 *
 * @author Dahai Li
 *
 * Test Shell class.
 */
public class ShellTest {

    @Test
    public void testRun() {
        Shell sh = new Shell();
        try {
            assertEquals("Exit code of pwd is 0", sh.run("pwd"), 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("testRun throws exception");
        }
    }

    @Test
    public void testGetStdOut() {
        Shell sh = new Shell();
        try {
            sh.run("echo", "hello");
            assertEquals("Stdout of 'echo hello' is 'hello'", sh.getStdOut(), "hello\n");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("testGetStdOut throws exception");
        }
    }

    @Test
    public void testGetStdErr() {
        Shell sh = new Shell();
        try {
            assertFalse(sh.run("ls", "/junk") == 0);
            assertEquals(sh.getStdErr(), "ls: /junk: No such file or directory\n");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("testGetStdErr throws exception");
        }
    }

    @Ignore
    public void testSudoRunWithPassword() {
        Shell sh = new Shell();
        String password = "<changeme>";
        try {
            sh.run("bash", "-c", "echo '" + password + "\n' | sudo -S /usr/local/bin/nmap belize");
            System.out.println(sh.getStdOut());
            System.out.println(sh.getStdErr());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("testSudoRunWithPassword throws exception");
        }
    }
    

    @Ignore
    public void testNmapWithPassword2() {
        Shell sh = new Shell();
        String nmapCmd = "/usr/local/bin/nmap";
        String password = "<changeme>";
        try {
            sh.sudoRun(password, nmapCmd, "-O", "belize");
//            System.out.println(sh.getStdErr());
            System.out.println(sh.getStdOut());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("testNmapWithPassword2 throws exception");
        }
    }

    @Test
    public void testNmapWithoutPassword() {
        Shell sh = new Shell();
        String nmapCmd = "/usr/local/bin/nmap";
        try {
            sh.sudoRun(null, nmapCmd, "-O", "belize");
//            System.out.println(sh.getStdErr());
            System.out.println(sh.getStdOut());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("testNmapWithoutPassword throws exception");
        }
    }
}
