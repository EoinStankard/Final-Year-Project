package com.example.eoin.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CleanerHome extends AppCompatActivity {

    Button r431,r436,r593,logout;
    Socket client;
    PrintWriter printwriter;
    Boolean running = true;
    String send;
    String IP ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_home);
        r431=(Button)findViewById(R.id.button18);
        r436=(Button)findViewById(R.id.button19);
        r593=(Button)findViewById(R.id.button20);
        logout=(Button)findViewById(R.id.button21);
        r431.setEnabled(false);
        r436.setEnabled(false);
        r593.setEnabled(false);

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        String room431 = myBundle.getString("431");
        String room436 = myBundle.getString("436");
        String room593 = myBundle.getString("593");

        Home h = new Home();
        IP = h.getIP();
        if(room431.equals("yes")){
            r431.setEnabled(true);
            r431.setBackgroundColor(Color.rgb(22,75,165));
        }else if(room431.equals("no")){
            r431.setEnabled(false);
            r431.setBackgroundColor(Color.rgb(92,96,101));
        }
        if(room436.equals("yes")){
            r436.setEnabled(true);
            r436.setBackgroundColor(Color.rgb(22,75,165));
        }else if(room436.equals("no")){
            r436.setEnabled(false);
            r436.setBackgroundColor(Color.rgb(92,96,101));
        }
        if(room593.equals("yes")){
            r593.setEnabled(true);
            r593.setBackgroundColor(Color.rgb(22,75,165));
        }else if(room593.equals("no")){
            r593.setEnabled(false);
            r593.setBackgroundColor(Color.rgb(92,96,101));
        }



        r431.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send = "cleaner431";
                CleanerHome.sendData sendMessageTask = new CleanerHome.sendData();
                sendMessageTask.start();
            }
        });

        r436.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send = "cleaner436";
                CleanerHome.sendData sendMessageTask = new CleanerHome.sendData();
                sendMessageTask.start();
            }
        });

        r593.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send = "cleaner593";
                CleanerHome.sendData sendMessageTask = new CleanerHome.sendData();
                sendMessageTask.start();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CleanerHome.this,CleanerLogin.class);
                startActivity(i);
                finish();
            }
        });
    }

    private class sendData extends Thread {
        public void run() {
            try {

                client = new Socket(IP, 12345); // Iphone
                client.setKeepAlive(true);
                //while(running) {
                    printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                    printwriter.println(send); // write the message to output stream
                    printwriter.flush();
                    //Toast.makeText(CleanerHome.this, "Sent", Toast.LENGTH_SHORT).show();

               // }
            } catch(Exception e){

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try	{

        }
        catch (Exception e) {

        }
    }
}
