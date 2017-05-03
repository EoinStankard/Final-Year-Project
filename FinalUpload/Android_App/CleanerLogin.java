package com.example.eoin.projectapp;

import android.app.Activity;
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

public class CleanerLogin extends AppCompatActivity {
    Socket client;
    PrintWriter printwriter;
    boolean running = true;
    Button login,back;
    EditText username,password;
    Intent cleanerPage;
    String send;
    String str2[];
    String IP ="";
    String room431,room436,room593;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_login);

        login = (Button)findViewById(R.id.button16);
        username = (EditText)findViewById(R.id.editText4);
        password = (EditText)findViewById(R.id.editText5);
        back =(Button)findViewById(R.id.button17);
        Home h = new Home();
        IP = h.getIP();
        CleanerLogin.Connect connect = new CleanerLogin.Connect();
        connect.start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pass =password.getText().toString();
                if (name.equals("admin") ) {
                    try {
                        if(pass.equals("")) {
                            Toast.makeText(CleanerLogin.this, "Please Enter Username & Password", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                        }else {


                            cleanerPage = new Intent(CleanerLogin.this, CleanerHome.class);
                            printwriter.println(name+pass);
                            printwriter.flush();
                            send = name + pass;
                        }

                    } catch(Exception e){
                    }
                }else{
                    Toast.makeText(CleanerLogin.this,"Please Enter Username & Password", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CleanerLogin.this,Home.class);
                startActivity(i);
                finish();
            }
        });

    }

    private class Connect extends Thread {
        public void run() {
            try {
                client = new Socket(IP, 12345); // connect to the server
                client.setKeepAlive(true);
                printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                while(running) {
                    printwriter.flush();
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    final String clientCommand = in.readLine();

                    if(clientCommand.startsWith("CLEANTRUE")){
                        str2= clientCommand.split("\\s+");
                        Bundle b = new Bundle();
                        room431 = str2[1];
                        room436 = str2[2];
                        room593 = str2[3];
                        b.putString("431",room431);
                        b.putString("436",room436);
                        b.putString("593",room593);
                        cleanerPage.putExtras(b);
                        startActivityForResult(cleanerPage, 2);
                        finish();
                    }else if(clientCommand.equals("CLEANFALSE")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(CleanerLogin.this,"Login Failed", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                password.setText("");
                            }
                        });
                    }else if(clientCommand.startsWith("431 ")||clientCommand.startsWith("436 ")||clientCommand.startsWith("593 ")){
                        Intent i = new Intent(CleanerLogin.this,CleanerRoom.class);
                        String str[] =clientCommand.split("\\s+");
                        Bundle b = new Bundle();
                        b.putString("ROOM",str[0]);
                        b.putString("TIME",str[1]);
                        b.putString("PILLOWS",str[2]);
                        b.putString("TOWELS",str[3]);
                        i.putExtras(b);
                        startActivityForResult(i,101);
                    }
                }
            } catch(Exception e){

            }
        }
    }
}
