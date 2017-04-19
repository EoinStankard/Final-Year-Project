package com.example.eoin.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationDecline extends AppCompatActivity {
    TextView details;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_decline);

        details=  (TextView)findViewById(R.id.textView26);
        done = (Button)findViewById(R.id.button13);

        String Room = getIntent().getStringExtra("Room");
        String Time = getIntent().getStringExtra("Time");
        String Table = getIntent().getStringExtra("Table");
        details.setText(Room +"\n"+Time+"\n"+Table+"\n"+"Please Contact Reception For Assistance");

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
