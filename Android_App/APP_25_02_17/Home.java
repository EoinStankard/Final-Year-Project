package com.example.eoin.projectapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class Home extends AppCompatActivity {

    NotificationManager notificationManager;
    final int NOTIFICATION_ID = 12345;
    IntentFilter filter;

    Button login;
    EditText RoomNo,Password;
    Socket client;
    PrintWriter printwriter;
    boolean running = true;
    boolean noti;
    Intent GuestPage;
    Context context;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = getApplicationContext();

        login = (Button)findViewById(R.id.button);
        RoomNo = (EditText)findViewById(R.id.editText);
        Password=(EditText)findViewById(R.id.editText2);
        Home.Connect sendMessageTask = new Home.Connect();
        //sendMessageTask.setContext(context);
        sendMessageTask.start();
        noti = false;
        //Intent Service= new Intent(this, MyService.class);
        //startService(Service);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                num = RoomNo.getText().toString();
                int numm = Integer.parseInt(num);
                String pWord = Password.getText().toString();
                if (num.equals("431")  || num.equals("436") || num.equals("593") ) {
                    try {
                        if(pWord.equals("")){
                            Toast.makeText(Home.this,"Please Enter Room Number & Password", Toast.LENGTH_SHORT).show();
                            RoomNo.setText("");
                            Password.setText("");
                        }
                        GuestPage = new Intent(Home.this, guestHome.class);
                        Bundle myData = new Bundle();
                        myData.putInt("RoomNo: ", numm);

                        GuestPage.putExtras(myData);
                        printwriter.println(num+" "+pWord);
                        printwriter.flush();

                    } catch(Exception e){
                    }
                }else{
                    Toast.makeText(Home.this,"Please Enter Room Number & Password", Toast.LENGTH_SHORT).show();
                    RoomNo.setText("");
                    Password.setText("");
                }

            }
        });
    }
    private class Connect extends Thread {
        Context context;
        public void setContext(Context context1){
            context=context1;
        }
        public void run() {

            try {
                client = new Socket("192.168.0.9", 12345); // Home
               // client = new Socket("10.12.18.8", 12345); // EduRoam
                //client = new Socket("172.20.10.4", 12345); // Iphone
                client.setKeepAlive(true);
                printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                printwriter.println("Password "); // write the message to output stream
                printwriter.flush();

                while(running){
                    printwriter.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    final String clientCommand = in.readLine();

                    if(clientCommand.equals("TRUE")){
                        noti=true;

                        printwriter.println("App Room: " + RoomNo.getText().toString());
                        printwriter.flush();
                        startActivityForResult(GuestPage, 2);
                        finish();
                    }else if(clientCommand.equals("connected")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Home.this,"Connected To Server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    /////////////////////////NOTIFICATIONS//////////////////////////////////////////////////////////////
                    if(clientCommand.startsWith("NotificationTrue 431") && noti == true && num.equals("431")
                            || clientCommand.startsWith("NotificationTrue 436") && noti == true && num.equals("436")
                            || clientCommand.startsWith("NotificationTrue 593") && noti == true && num.equals("593")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Home.this,"NotificationTrue 431", Toast.LENGTH_SHORT).show();
                                String[] str = clientCommand.split("\\s+");
                                String notiConfirm = "Room: "+str[1]+" Time: "+str[2];
                                notificationConfirmed(notiConfirm);

                            }
                        });
                    } else if(clientCommand.startsWith("NotificationFalse 431")&& noti == true && num.equals("431")
                            || clientCommand.startsWith("NotificationFalse 436") && noti == true && num.equals("436")
                            || clientCommand.startsWith("NotificationFalse 593") && noti == true && num.equals("593")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Home.this,"NotificationFalse", Toast.LENGTH_SHORT).show();
                                String[] str = clientCommand.split("\\s+");
                                String notiDecline = "Room: "+str[1]+" Time: "+str[2];
                                notificationDeclined(notiDecline);
                            }
                        });
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    public void notificationConfirmed(String s){
        Notification.Builder baseNotification = new Notification.Builder(Home.this)
                .setContentTitle("Restaurant Booking Confirmation")
                .setContentText(s)
                .setTicker("Restaurant Booking Confirmed")
                .setSmallIcon(R.drawable.icon0)
                .setLights(0xffcc00, 1000, 500);

        Notification noti = new Notification.InboxStyle(baseNotification).setSummaryText("SUMMARY-Line-1 here").build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        // notification ID is 12345
        notificationManager.notify(12345, noti);
    }

    public void notificationDeclined(String s){
        Notification.Builder baseNotification = new Notification.Builder(Home.this)
                .setContentTitle("Restaurant Booking Declined")
                .setContentText("Please Contact Reception")
                .setTicker("Restaurant Booking Declined")
                .setSmallIcon(R.drawable.icon0)
                .setLights(0xffcc00, 1000, 500);

        Notification noti = new Notification.InboxStyle(baseNotification).setSummaryText("SUMMARY-Line-1 here").build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        // notification ID is 12345
        notificationManager.notify(12345, noti);

    }
}
