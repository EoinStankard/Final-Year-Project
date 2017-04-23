package com.example.eoin.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class roomConfirm extends AppCompatActivity {
    Button confirm,back;
    EditText password;
    Socket client;
    String send;
    boolean running = true;
    PrintWriter printwriter;
    String IP ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_confirm);
        confirm=(Button)findViewById(R.id.button5);
        back = (Button)findViewById(R.id.button6);
        password=(EditText)findViewById(R.id.editText3);
        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        final String RoomNo = myBundle.getString("RoomNo: ");
        Home h = new Home();
        IP =h.getIP();
        sendData sendMessageTask = new sendData();
        sendMessageTask.start();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(roomConfirm.this,"Confirm", Toast.LENGTH_SHORT).show();
                String pWord = password.getText().toString();
                if(pWord.equals("")) {
                    Toast.makeText(roomConfirm.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }else {
                    String send = "roomPWORD" + RoomNo + " " + pWord;
                    printwriter.println(send); // write the message to output stream
                    printwriter.flush();
                    finish();
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(roomConfirm.this,"TRUE", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private class sendData extends Thread {
        public void run() {
            try {
                client = new Socket(IP, 12345); // Iphone
                client.setKeepAlive(true);
                printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                while(running) {
                    printwriter.flush();
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String clientCommand = in.readLine();

                    if(clientCommand.equals("roomtrue")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(roomConfirm.this,"TRUE", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();

                    }else if(clientCommand.equals("roomfalse")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(roomConfirm.this,"Incorrect Password. Contact Reception", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent home = new Intent(roomConfirm.this,Home.class);
                        startActivity(home);
                        finish();
                    }
                }
            } catch(Exception e){

            }

        }
    }
}
