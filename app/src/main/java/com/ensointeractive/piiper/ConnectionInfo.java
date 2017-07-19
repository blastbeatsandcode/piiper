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
import java.net.UnknownHostException;

public class ConnectionInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_connection_info);

        // Autofill text inputs with defaults
        final EditText txtIP = (EditText) findViewById(R.id.rpi_ip);
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
                    WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    String ip = Integer.toString(wm.getConnectionInfo().getIpAddress());

                    // Run command
                    Process shell = null;
                    try {
                        shell = Runtime.getRuntime().exec("grep e");

                        BufferedReader br = new BufferedReader(new InputStreamReader(shell.getInputStream()));

                        // Add bufferedreader to string
                        String name = new String();

                        for (String line; (line = br.readLine()) != null; name += line);

                        Toast.makeText(ConnectionInfo.this, "ifconfig Results: " + name, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(ConnectionInfo.this, "Could not connect", Toast.LENGTH_LONG).show();
                    }

                    // Show Toast
                    Toast.makeText(ConnectionInfo.this, "IP Address: " + ip, Toast.LENGTH_LONG).show();
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
