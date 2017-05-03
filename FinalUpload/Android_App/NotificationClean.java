package com.example.eoin.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationClean extends AppCompatActivity {
    TextView detail;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_clean);
        detail=  (TextView)findViewById(R.id.textView28);
        done = (Button)findViewById(R.id.button14);

       // Intent myLocalIntent = getIntent();
        //Bundle myBundle = myLocalIntent.getExtras();
        //String Room = myBundle.getString("clean");
        //String Room = getIntent().getStringExtra("clean");
        //String Room = getIntent().getStringExtra("Roomnum");
       // Toast.makeText(NotificationClean.this,Room, Toast.LENGTH_SHORT).show();
       // detail.setText("Room: "+Room );

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
