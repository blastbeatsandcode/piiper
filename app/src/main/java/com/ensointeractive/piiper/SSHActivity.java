package com.ensointeractive.piiper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class SSHActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssh);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Grab Pi logon information here
        Bundle bundle = getIntent().getExtras();
        final String hostname = bundle.getString("hostname");
        final String username = bundle.getString("username");
        final String password = bundle.getString("password");


        Button btnSend = (Button) findViewById(R.id.btn_send);

        // If any fields are incomplete, show Toast
        // Otherwise, continue connecting
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create SSHPi object to connect to SSH
                SSHPi ssh = new SSHPi(username, password, hostname);
                sendCommand(ssh);
            }
        });


    }

    // Takes SSHPi object to send command to
    private void sendCommand (SSHPi ssh)
    {
        try {
            Toast.makeText(SSHActivity.this, "Test before SSH attempt", Toast.LENGTH_LONG).show();
            String exec = ssh.executeRemoteCommand();
            Toast.makeText(SSHActivity.this, exec, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(SSHActivity.this, "Could not SSH properly", Toast.LENGTH_LONG).show();
            Log.d("REMOTE_CONNECT", "Could not connect error");
            e.printStackTrace();
        }
    }

}
