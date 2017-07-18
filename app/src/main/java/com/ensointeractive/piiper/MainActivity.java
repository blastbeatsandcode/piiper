package com.ensointeractive.piiper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import android.content.Context;
import java.net.InetAddress;
import java.util.Enumeration;
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

        /*
        View constraintLayout = (ConstraintLayout)findViewById(R.id.textView);
        constraintLayout.setId(R.id.all);
        setContentView(constraintLayout);
        */

        Context context = getApplicationContext();
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        isTethering(wifi);


//        pulseView = (PulseView) findViewById(R.id.pv);
//        pulseView.startPulse();
    }

    // Check if tethering is active
    private boolean isTethering(Context context) {
        Toast.makeText(getApplicationContext(), "TEST FOR EXECUTION 1", Toast.LENGTH_LONG).show();
        /*
        try{
            Toast.makeText(getApplicationContext(), "TEST FOR EXECUTION 1.5", Toast.LENGTH_LONG).show();
            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                NetworkInterface intf=en.nextElement();
                Toast.makeText(getApplicationContext(), "TEST FOR EXECUTION 2", Toast.LENGTH_LONG).show();
                for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    Toast.makeText(getApplicationContext(), "TEST FOR EXECUTION 3", Toast.LENGTH_LONG).show();
                    InetAddress inetAddress=enumIpAddr.nextElement();
                    if(!intf.isLoopback()){
                        if(intf.getName().contains("rndis")){
                            TextView text = new TextView(this);
                            text.setText("ON");
                            Toast.makeText(getApplicationContext(), "Tethering is ON", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        Toast.makeText(getApplicationContext(), "TEST FOR EXECUTION 4", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }catch(Exception e){e.printStackTrace();}
        TextView text = new TextView(this);
        text.setText("OFF");
        // constraintLayout.addView(text);
        Toast.makeText(getApplicationContext(), "Tethering is OFF", Toast.LENGTH_LONG).show();
        return false;
        */


    }
}
