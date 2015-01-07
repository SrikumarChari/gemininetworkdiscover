package com.apollo.discover.nmap;

//import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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

        //needed to supply the password
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        
        //TODO: change the code below to retrieve password from an application settings object
        String password = "insert password here";
        writer.write(password);
        writer.write("\n");
        writer.flush();
        
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
