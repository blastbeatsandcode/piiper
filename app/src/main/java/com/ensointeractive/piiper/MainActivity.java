package com.ensointeractive.piiper;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.library.PulseView;

public class MainActivity extends AppCompatActivity {

    PulseView pulseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTethering(getApplicationContext());


        // Add pulse to Piiper icon
        pulseView = (PulseView) findViewById(R.id.pv);
        pulseView.setPulseCount(2);
        pulseView.startPulse();
    }

    // Sends user to Connection Info activity and pings for IP
    public void goToConnectionInfo (View view) throws InterruptedException {
        Toast.makeText(MainActivity.this, "Searching for Hostname...", Toast.LENGTH_LONG).show();

        String reachable = ping();
        if (!reachable.equals("")) {
            // USB address was found
            Toast.makeText(MainActivity.this, "Located address " + reachable, Toast.LENGTH_LONG).show();
            Log.d("STATUS", "connected to something!");
        } else {
            // USB address was not found
            Toast.makeText(MainActivity.this, "Failed to connect to a hostname", Toast.LENGTH_LONG).show();
            Log.d("STATUS", "failed to connect to a hostname");
        }

        // Start connection activity
        Intent intent = new Intent(MainActivity.this, ConnectionInfo.class);
        intent.putExtra("hostname", reachable);
        startActivity(intent);
    }

    // Check if tethering is active
    private boolean isTethering(Context context) {
        // Run shell command
        try {
            // Query for "rndis" is /sys/class/net folder
            Process shell = Runtime.getRuntime().exec("ls /sys/class/net/");
            BufferedReader br = new BufferedReader(new InputStreamReader(shell.getInputStream()));

            // Add bufferedreader to string
            String match = "rndis0";
            String name = new String();
            for (String line; (line = br.readLine()) != null; name += line);

            // count if there are occurrences of the string
            int occurrences = name.indexOf(match, 0);

            // Regular expression to replace everything that's not "rndis"
            // If this exists, then USB tethering is connected
            name = name.replaceAll("(.*)rndis(.*)", match);

            // Change textviews to show that tethering is turned on or off
            View tvOff = findViewById(R.id.textView_off);
            View tvOn = findViewById(R.id.textView_on);
            View btn = findViewById(R.id.button_continue);


            // Change visibility of "continue" button
            if (occurrences > 0)
            {
                tvOff.setVisibility(View.INVISIBLE);
                tvOn.setVisibility(View.VISIBLE);
                btn.setEnabled(true);
                btn.setBackgroundColor(Color.parseColor("#4286f4"));
            }
            else
            {
                tvOff.setVisibility(View.VISIBLE);
                tvOn.setVisibility(View.INVISIBLE);
                btn.setEnabled(false);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error while retrieving Tether State", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    // searches for a live connection to given IP
    public String ping() {
        String str = "";
        try {
            // sends command to get information for tethering interface
            // rndis0 is the network interface android uses for a USB tethered device
            Process process = Runtime.getRuntime().exec("/system/bin/ifconfig rndis0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();

            // Add to output
            while ((i = reader.read(buffer)) > 0) {
                output.append(buffer, 0, i);
            }
            reader.close();

            str = output.toString();
            Log.d("STATE", str);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("STATE", "Error");
        }

        // Regex pattern that matches IP addresses
        String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(str);

        // Verify that it matched a correct IP
        if (matcher.find() && matcher.group().contains("192.168.42.")) {
            return matcher.group();
        } else{
            return "";
        }
    }

}