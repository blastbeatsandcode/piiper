package com.ensointeractive.piiper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

        // Grab views
        Button btnSend = (Button) findViewById(R.id.btn_send);
        final TextView output = (TextView) findViewById(R.id.txtView_Output);
        output.setMovementMethod(new ScrollingMovementMethod()); // Allows for scrolling in text view
        final EditText txtCmd = (EditText) findViewById(R.id.txt_Command);
        txtCmd.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        // If any fields are incomplete, show Toast
        // Otherwise, continue connecting
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Grab input from EditText field
                String cmd = readText(txtCmd);

                if (cmd.equals("clear")) // Clear screen
                {
                    output.setText("");
                    txtCmd.setText("");
                }
                else
                {
                    // Create SSHPi object to connect to SSH
                    SSHPi ssh = new SSHPi(username, password, hostname);

                    // Execute command and wait for it to finish
                    AsyncTask task = ssh.execute(cmd);
                    try {
                        task.get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }

                    output.append(ssh.getOutput());
                    txtCmd.setText("");
                }
            }
        });


    }

    // Takes input to return to button Click Listener
    private String readText(EditText txtEdit)
    {
        return txtEdit.getText().toString();
    }

}
