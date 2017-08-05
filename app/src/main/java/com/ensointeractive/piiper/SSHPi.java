package com.ensointeractive.piiper;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.Properties;

/**
 * Created by Alex Silcott on 8/2/2017.
 */

public class SSHPi extends AsyncTask<String, Void, String> {

    String username;
    String password;
    String hostname;
    String output;
    int port;


    public SSHPi (String username, String password, String hostname)
    {
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.output = "";

        // Change this if the port number needs to be changed
        this.port = 22;
    }


    // Sends commands to terminal
    @Override
    protected String doInBackground(String... params) {
        // Accepts parameters and concatenates them into a single command
        String[] commandArr = params;
        String command = "";

        for (int i = 0; i < commandArr.length; i++)
        {
            command += commandArr[i];
        }

        JSch jsch = new JSch();
        Session session = null;

        try {
            session = jsch.getSession(this.username, this.hostname, this.port);
            session.setPassword(this.password);

            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "Error creating initial session");
            e.printStackTrace();
        }

        try {
            session.connect();

            // SSH Channel
            ChannelExec channelssh = null;

            channelssh = (ChannelExec) session.openChannel("exec");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // channelssh.setOutputStream(baos);
            InputStream in = channelssh.getInputStream();

            // Execute command
            channelssh.setCommand(command);
            channelssh.setInputStream(null);

            channelssh.connect();

            // Attempt to read input stream
            String output = "";
            byte[] tmp = new byte[1024];
            while(true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    output += (new String(tmp, 0, i));
                }
                if (channelssh.isClosed()) {
                    if (in.available() > 0) continue;
                    System.out.println("exit-status: " + channelssh.getExitStatus());
                    break;
                }
            }

            // String output = new String(baos.toByteArray());

            channelssh.disconnect();
            this.output = output;
            Log.d("SSH_CONNECT_NEW", output);
            Log.d("SSH_CONNECT_NEW", this.output);

        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "session.connect() failed");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.d("SSH_CONNECT", "Failed to get input stream");
            e.printStackTrace();
        }

        return this.output;
    }

    public String getOutput()
    {
        return this.output + "\n>>\n";
    }
}
