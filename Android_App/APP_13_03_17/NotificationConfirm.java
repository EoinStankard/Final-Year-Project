package com.example.eoin.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationConfirm extends AppCompatActivity {
    TextView details;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_confirm);
        details=  (TextView)findViewById(R.id.textView23);
        done = (Button)findViewById(R.id.button12);

        String Room = getIntent().getStringExtra("Room");
        String Time = getIntent().getStringExtra("Time");
        String Table = getIntent().getStringExtra("Table");
        details.setText(Room +"\n"+Time+"\n"+Table);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
