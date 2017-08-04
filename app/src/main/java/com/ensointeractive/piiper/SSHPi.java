package com.ensointeractive.piiper;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Properties;

/**
 * Created by Ganon on 8/2/2017.
 */

public class SSHPi extends AsyncTask<String, Void, String> {

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

    /*
    // Execute a command
    protected String executeRemoteCommand(/*String username, String password, String hostname, int port*//*) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(this.username, this.hostname, this.port, 22);
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "Error creating initial session");
            e.printStackTrace();
        }
        session.setPassword(this.password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        try {
            session.connect();
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "session.connect() failed");
            e.printStackTrace();
        }

        // SSH Channel
        ChannelExec channelssh = null;
        try {
            channelssh = (ChannelExec) session.openChannel("exec");
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "session.openChannel('exec') failed");
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand("touch ~/Desktop/test.txt");
        try {
            channelssh.connect();
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "channelssh.connect() failed");
            e.printStackTrace();
        }

        channelssh.disconnect();

        return baos.toString()/* + io.toString() *//* + "\nThis should follow any commands";
    }
    */

    @Override
    protected String doInBackground(String... params) {
        // TODO: pass in custom commands
        // String[] command = params;
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(this.username, this.hostname, this.port);
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "Error creating initial session");
            e.printStackTrace();
        }
        session.setPassword(this.password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        try {
            session.connect();
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "session.connect() failed");
            e.printStackTrace();
        }

        // SSH Channel
        ChannelExec channelssh = null;
        try {
            channelssh = (ChannelExec) session.openChannel("exec");
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "session.openChannel('exec') failed");
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        // TODO: make this take the params passed to this function
        channelssh.setCommand("echo \"Looks like it worked! B)\" > ~/Desktop/test.txt");
        try {
            channelssh.connect();
        } catch (JSchException e) {
            Log.d("SSH_CONNECT", "channelssh.connect() failed");
            e.printStackTrace();
        }

        channelssh.disconnect();

        return "";
    }
}
