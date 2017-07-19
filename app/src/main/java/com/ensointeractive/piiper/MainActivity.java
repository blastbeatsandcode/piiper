package com.ensointeractive.piiper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import android.content.Context;
import java.net.InetAddress;
import java.util.Enumeration;

import android.view.View;
import android.widget.TextView;
// import android.support.constraint.ConstraintLayout;
import android.widget.Toast;
import android.net.wifi.WifiManager;

// import com.gigamole.library.PulseView;

public class MainActivity extends AppCompatActivity {

//    PulseView pulseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Context context = getApplicationContext();
        // WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        isTethering(getApplicationContext());


//        pulseView = (PulseView) findViewById(R.id.pv);
//        pulseView.startPulse();
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

            // Regular expression to replace everything that's not "rndis0"
            // If this exists, then USB tethering is connected
            // !!! FIX THIS !!!

            // count if there are occurences of the string
            int occurrences = name.indexOf(match, 0);

            // Change textviews to show that tethering is turned on or off
            View tvOff = findViewById(R.id.textView_off);
            View tvOn = findViewById(R.id.textView_on);

            if (occurrences > 0)
            {
                tvOff.setVisibility(View.INVISIBLE);
                tvOn.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Tethering is turned ON: " + name, Toast.LENGTH_LONG).show();
            }
            else
            {
                tvOff.setVisibility(View.VISIBLE);
                tvOn.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Tethering is turned OFF", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error while retrieving Tether State", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
