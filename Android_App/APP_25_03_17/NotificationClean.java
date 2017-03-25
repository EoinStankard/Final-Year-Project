package com.example.eoin.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationClean extends AppCompatActivity {
    TextView detail;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_clean);
        detail=  (TextView)findViewById(R.id.textView28);
        done = (Button)findViewById(R.id.button14);

        String Room = getIntent().getStringExtra("ROOM");
        //String Room = getIntent().getStringExtra("Roomnum");

        detail.setText("Room: "+Room );

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
