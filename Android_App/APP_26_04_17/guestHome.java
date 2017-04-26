package com.example.eoin.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;

public class guestHome extends AppCompatActivity {
    TextView Roomnum;
    Button clean,logOut, About,RoomLock,restaurant;
    int RoomNo;
    Socket client;
    PrintWriter printwriter;
    String send,Reserve;
    boolean running = true;
    String time,pillow,towel;
    String IP="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        clean = (Button) findViewById(R.id.buttonClean);
        logOut = (Button) findViewById(R.id.button9);
        About = (Button) findViewById(R.id.buttonLock);
        Roomnum = (TextView) findViewById(R.id.textView3);
        RoomLock = (Button) findViewById(R.id.button8);
        restaurant = (Button) findViewById(R.id.button7);

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        RoomNo = myBundle.getInt("RoomNo: ");
        Roomnum.setText("" + RoomNo);

        Home h = new Home();
        IP = h.getIP();

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cleanPage = new Intent(guestHome.this,Cleaning.class);
                Intent IntentC = getIntent();
                Bundle bundle = IntentC.getExtras();
                bundle.putInt("RoomNum: ",RoomNo);
                cleanPage.putExtras(bundle);
                startActivityForResult(cleanPage,101);
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cleanPage = new Intent(guestHome.this,Home.class);
                startActivity(cleanPage);
                finish();
            }
        });

        RoomLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent roomConfirm = new Intent(guestHome.this,roomConfirm.class);
                Bundle myData = new Bundle();
                myData.putString("RoomNo: ", Roomnum.getText().toString());
                roomConfirm.putExtras(myData);
                startActivity(roomConfirm);
            }
        });
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rest = new Intent(guestHome.this,Restaurant.class);
                Bundle myData = new Bundle();
                myData.putInt("RoomNo: ",RoomNo);
                rest.putExtras(myData);
                startActivityForResult(rest,102);
            }
        });
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(guestHome.this,About.class);
                startActivity(about);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try	{
            if ((requestCode == 101 ) && (resultCode == Activity.RESULT_OK)){
                Bundle myResultBundle = data.getExtras();
                time = myResultBundle.getString("time");
                pillow = myResultBundle.getString("pillow");
                towel = myResultBundle.getString("towel");

                String num = Integer.toString(RoomNo);
                send="\t\tCleaning Service \t       "+num+"                                 "
                        +time+"\t       "+pillow+"\t                         "+towel;
                guestHome.sendData sendMessageTask = new guestHome.sendData();
                sendMessageTask.start();
            }else if((requestCode == 102 ) && (resultCode == Activity.RESULT_OK)){
                Bundle myResultBundle = data.getExtras();
                Reserve = myResultBundle.getString("reserve");
                Toast.makeText(guestHome.this,Reserve,
                        Toast.LENGTH_SHORT).show();
                send=Reserve;
                guestHome.sendData sendMessageTask = new guestHome.sendData();
                sendMessageTask.start();
            }
        }
        catch (Exception e) {

        }
    }//onActivityResult
    private class sendData extends Thread {
        public void run() {
            try {
                client = new Socket(IP, 12345); // Iphone
                client.setKeepAlive(true);
                    while(running) {
                        printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                        printwriter.println(send); // write the message to output stream
                        printwriter.flush();
                        Toast.makeText(guestHome.this, "Sent", Toast.LENGTH_SHORT).show();

                    }
                } catch(Exception e){

            }
        }
    }
}

