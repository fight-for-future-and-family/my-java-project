package com.hoolai.loader.util;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;

class Wl{
	public static void main(String [] wl) throws IOException{
		Integer i=100;
		List wl_list=new List();
        System.out.println("inside the ssh function");
        try
        {
            Connection conn = new Connection("192.168.90.108",22016);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword("liyapeng", "liyapeng.2017");
            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");        
            ch.ethz.ssh2.Session sess = conn.openSession();
            sess.execCommand("pwd;echo 'hahahhaha____';date");  
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            System.out.println("the output of the command is");
            while (true)
            {
                String line = br.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
            System.out.println("ExitCode: " + sess.getExitStatus());
            sess.close();
            conn.close();
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
    }
}
