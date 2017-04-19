package com.example.eoin.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CleanerRoom extends AppCompatActivity {
    Button cleaned,back;
    TextView room,time,pillow,towel;
    String r;


    Socket client;
    String send;
    boolean running = true;
    PrintWriter printwriter;
    String IP ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_room);
        cleaned=(Button)findViewById(R.id.button22);
        back=(Button)findViewById(R.id.button23);
        room=(TextView)findViewById(R.id.textView32);
        time=(TextView)findViewById(R.id.textView33);
        pillow=(TextView)findViewById(R.id.textView34);
        towel=(TextView)findViewById(R.id.textView35);

        final Intent myLocalIntent = getIntent();
        final Bundle myBundle = myLocalIntent.getExtras();
        r = myBundle.getString("ROOM");
        String tim = myBundle.getString("TIME");
        String Pill = myBundle.getString("PILLOWS");
        String Towel = myBundle.getString("TOWELS");

        room.setText("Room: "+r);
        time.setText("Time: "+tim);
        pillow.setText("Pillows: "+Pill);
        towel.setText("Towels: "+Towel);
        Home h = new Home();
        IP = h.getIP();
        cleaned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send = "Cleaned"+r;
                CleanerRoom.sendData sendMessageTask = new CleanerRoom.sendData();
                sendMessageTask.start();
                //finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private class sendData extends Thread {
        public void run() {
            try {

                client = new Socket(IP, 12345); // Iphone
                client.setKeepAlive(true);
                while(running) {
                    printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                    printwriter.println(send); // write the message to output stream
                    printwriter.flush();
                    Toast.makeText(CleanerRoom.this, "Sent", Toast.LENGTH_SHORT).show();

                }
            } catch(Exception e){

            }
        }
    }
}
