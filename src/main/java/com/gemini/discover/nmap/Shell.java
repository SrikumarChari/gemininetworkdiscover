package com.gemini.discover.nmap;

//import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * *
 * Shell to execute external command
 *
 * @author Dahai Li
 *
 * @see Runtime, ProcessBuilder(JDK7)
 * http://www.google.com/search?q=java+execute+shell+command
 *
 *
 */
public class Shell {

    public Process process = null;

    /**
     *
     * @param args String[] arguments
     * @return exit code of running the command.
     * @throws IOException
     * @throws InterruptedException
     */
    public int run(String... args) throws IOException, InterruptedException {
        process = Runtime.getRuntime().exec(args);

        //wait for the process to finish
        process.waitFor();
        return process.exitValue();
    }

    /**
     * Run a command with sudo, i.e. in root privilege.
     * @param password String, the password for running sudo. null if you have setup /etc/sudoers to not prompt for password.
     * @param args String[] arguments
     * @return exit value of running the command.
     * @throws IOException
     * @throws InterruptedException
     */
    public int sudoRun(String password, String... args) throws IOException, InterruptedException {
    	String[] argv;
    	if (password != null) {
	    	argv = new String[args.length+2];
	    	argv[0] = "sudo";
	    	argv[1] = "-S";
	    	System.arraycopy(args, 0, argv, 2, args.length);
    	} else {
	    	argv = new String[args.length+1];
	    	argv[0] = "sudo";
	    	System.arraycopy(args, 0, argv, 1, args.length);
    	}
        process = Runtime.getRuntime().exec(argv);

        if (password != null) {
        //needed to supply the password
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
	        writer.write(password);
	        writer.write("\n");
	        writer.flush();
        }
        
        //wait for the process to finish
        process.waitFor();
        return process.exitValue();
    }
    
    
    /**
     *
     * @return the exit value from the last invokation of run
     */
    public int getExitValue() {
        return process.exitValue();
    }

    public String getStdOut() throws IOException {
        return readStream(process.getInputStream());
    }

    public String getStdErr() throws IOException {
        return readStream(process.getErrorStream());
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = "";
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }
        return output.toString();
    }
}
