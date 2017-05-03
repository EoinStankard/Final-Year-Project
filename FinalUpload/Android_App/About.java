package com.example.eoin.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class About extends AppCompatActivity {
    Button back;
    ScrollView scroll;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        details = (TextView)findViewById(R.id.textView11);
        scroll = (ScrollView)findViewById(R.id.scrollView);
        back = (Button)findViewById(R.id.button4);
        details.setText("The Automated hotel is designed to make the hotels guests stay easier, straight forward and " +
                "more relaxed than any other hotel. The idea is simple. The guest can download an app from the hotel and " +
                "from that they can book a table at the hotel restaurant, request their room to be cleaned and even unlock" +
                " their bedroom door if they have lost or misplaced their room key. The guest also doesnt have to worry about the cleaner" +
                " entering the room as you decide when the master card can be used on you bedroom door. The guests security is first " +
                "and if the off chance that someone enters your room without permission, the card that was used to unlock the door " +
                "would be recorded and saved on the hotel system so it would be very simple to find out what card was scanned and at " +
                "what time.");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
