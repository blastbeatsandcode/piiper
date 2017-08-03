package com.ensointeractive.piiper;

import android.content.Context;
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
        txtIP.setText(hostname);
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
                    // Show Toast
                    Toast.makeText(ConnectionInfo.this, "Connect to RaspberryPi", Toast.LENGTH_LONG).show();
                }
            }
        });

        /* // Floating button, might be of use later
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
