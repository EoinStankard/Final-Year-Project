package com.example.eoin.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class entertainment extends AppCompatActivity {
    Button back;
    Socket client;
    PrintWriter printwriter;
    String send;
    TextView name,time,location;
    boolean running = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        back = (Button)findViewById(R.id.button4);
        name =(TextView)findViewById(R.id.textView10);
        time =(TextView)findViewById(R.id.textView12);
        location =(TextView)findViewById(R.id.textView14);
        entertainment.sendData sendMessageTask = new entertainment.sendData();
        sendMessageTask.start();

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

                //client = new Socket("192.168.0.9", 12345); // connect to the server
                //client = new Socket("10.12.18.8", 12345); // connect to the server
                client = new Socket("172.20.10.4", 12345); // connect to the server

                client.setKeepAlive(true);
                printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                printwriter.println("ENTERTAINMENT"); // write the message to output stream
                printwriter.flush();
                while(running) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String clientCommand = in.readLine();
                    String entertainment[]= clientCommand.split(" $");
                }
            } catch(Exception e){

            }

        }
    }
}
