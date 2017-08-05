package com.ensointeractive.piiper;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class ConnectionInfo extends AppCompatActivity {

    // Max length an IPv4 can be
    final int MAX_IP_LENGTH = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_connection_info);

        // Grab bundle from MainActivity to get Rpi Address if it was found
        Bundle bundle = getIntent().getExtras();
        String hostname = bundle.getString("hostname");

        // Autofill text inputs with defaults
        final EditText txtIP = (EditText) findViewById(R.id.rpi_ip);

        // Checks to make sure we aren't being sent a bunch irrelevant connection info from previous activity
        if (hostname.length() <= MAX_IP_LENGTH) {
            txtIP.setText(hostname);
        }

        // Default usernames and passwords for RPi
        final EditText txtUser = (EditText) findViewById(R.id.rpi_default_user);
        txtUser.setText(R.string.default_username);
        final EditText txtPass = (EditText) findViewById(R.id.rpi_default_pass);
        txtPass.setText(R.string.default_password);

        Button btnConnect = (Button) findViewById(R.id.btn_connect);

        // If any fields are incomplete, show Toast
        // Otherwise, continue connecting
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtIP.getText().length() == 0 || txtUser.getText().length() == 0 || txtPass.getText().length() == 0)
                {
                    Toast.makeText(ConnectionInfo.this, "Please complete all fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Go to ssh activity
                    Intent intent = new Intent(ConnectionInfo.this, SSHActivity.class);
                    intent.putExtra("hostname", txtIP.getText().toString());
                    intent.putExtra("username", txtUser.getText().toString());
                    intent.putExtra("password", txtPass.getText().toString());
                    startActivity(intent);
                }
            }
        });

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
    }



}
