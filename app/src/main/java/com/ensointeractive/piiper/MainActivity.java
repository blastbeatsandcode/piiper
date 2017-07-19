package com.ensointeractive.piiper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.gigamole.library.PulseView;

public class MainActivity extends AppCompatActivity {

    PulseView pulseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTethering(getApplicationContext());


        pulseView = (PulseView) findViewById(R.id.pv);
        pulseView.setPulseCount(2);
        pulseView.startPulse();
    }

    // Sends user to Connection Info activity
    public void goToConnectionInfo (View view) {
        Intent intent = new Intent(MainActivity.this, ConnectionInfo.class);
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

            if (occurrences > 0)
            {
                tvOff.setVisibility(View.INVISIBLE);
                tvOn.setVisibility(View.VISIBLE);
                btn.setEnabled(true);
                btn.setBackgroundColor(Color.parseColor("#4286f4"));
                // Toast.makeText(getApplicationContext(), "Tethering is turned ON: " + name, Toast.LENGTH_LONG).show();
            }
            else
            {
                tvOff.setVisibility(View.VISIBLE);
                tvOn.setVisibility(View.INVISIBLE);
                btn.setEnabled(false);
                // Toast.makeText(getApplicationContext(), "Tethering is turned OFF: " + name, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error while retrieving Tether State", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
