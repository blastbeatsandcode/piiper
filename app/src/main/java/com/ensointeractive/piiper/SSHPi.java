package com.ensointeractive.piiper;

import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Created by Ganon on 8/2/2017.
 */

public class SSHPi {

    String username;
    String password;
    String hostname;
    int port;


    public SSHPi (String username, String password, String hostname)
    {
        this.username = username;
        this.password = password;
        this.hostname = hostname;

        // Change this if the port number needs to be changed
        this.port = 22;
    }


    protected String executeRemoteCommand(/*String username, String password, String hostname, int port*/) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(this.username, this.hostname, this.port);
        session.setPassword(this.password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);
        Log.d("PROGRESSION", "STEP THREE");

        // Execute command
        channelssh.setCommand("ls");
        channelssh.connect();
        channelssh.disconnect();
        Log.d("PROGRESSION", "STEP FOUR");

        return baos.toString();
    }

}
