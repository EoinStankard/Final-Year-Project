/**
 * Created by h on 29/11/2016.
 */

import net.miginfocom.swing.MigLayout;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;

public class ProjectServer {
    JTextArea Txt1,Txt2,Txt3,Txt4,Txt5,hist;
    JTextArea P2_Txt1,display;
    JPasswordField pWord;
    JTextField rn, RLText,ErrorText,DSText,CText,RoomNoText;
    JButton DoorLock,Database,Clear,unLock,Lock,DataSearch,CleanerLock,RoomNo,UpdateChange,confirm,CheckOut,CleanerPWord,GuestButton,logout,history;
    JLabel label,labelR,labelC,p2_label;
    String number431="431",number436="436",number593="593";
    ClientServiceThread[] CST;
    Connection conn;
    int clientCount=0;
    JTabbedPane tabbedPane = new javax.swing.JTabbedPane();
    String room1="436";
    String room2="593";
    String room3="431";
    int lineNumber=0,restCount=0;
    int RestNum=1,cleanNum=1;
    int CheckIn431=0,CheckIn436=0,CheckIn593=0;

    String room431Pword,room436Pword,room593Pword;
    String Pword431,Pword436,Pword593;
    String appRoom431,appRoom436,appRoom593;
    String BoardRNum;
    int id,endIndexRest,startIndexRest,endIndex431,endIndex436,endIndex593;
    int room431ID=-1,appID,room436ID=-1,room593ID=-1;
    Highlighter.HighlightPainter painter;
    JPanel tabPane3;
    JPanel tabPane;
    JPanel tabPane1;
    JPanel tabPane2;
    JPanel tabPane4;
    JLabel status431,status436,status593;
    String CleanerApp = "admin";
    String Cleaner431="no",Cleaner436="no",Cleaner593="no";
    String CleaningDetail431,CleaningDetail436,CleaningDetail593;
    String OldPassword,NewPassword,Username;
    String restString2;


    public static void main(String[] args) {
        try{
           UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");//"com.jtattoo.plaf.acryl.AcrylLookAndFeel"
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        new ProjectServer();
    }


    /*****************************************************************************************************
     *
     ****************************************************************************************************/
    public ProjectServer() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                GUI();
            }
        });
    }
    /*****************************************************************************************************
     *Main GUI
     ****************************************************************************************************/
    private void GUI() {
        JFrame frame = new JFrame("Hotel Server");
        frame.setSize(1372,738);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ////////TABS//////////////////////
        tabPane=new JPanel(new BorderLayout());
        tabPane1=new JPanel(new BorderLayout());
        tabPane2 =new JPanel(new BorderLayout());
        tabPane3 = new JPanel (new MigLayout("center"));
        tabPane4 =new JPanel(new BorderLayout());
        /////////////Panels////////////////////////
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel pane1Panel = new JPanel();
        //**********************************************  TAB HOME*****************************************************************

        pane1Panel.setLayout(new GridLayout(12,1));
        JLabel name =new JLabel("<html>Eoin Stankard<br></html>", SwingConstants.CENTER);
        name.setFont(new Font("Calibri", Font.BOLD, 30));
        JLabel course =new JLabel("<html>Computer and Electronic Engineering<br></html>", SwingConstants.CENTER);
        course.setFont(new Font("Calibri", Font.BOLD, 30));
        JLabel fyp =new JLabel("<html>Final Year Project<br></html>", SwingConstants.CENTER);
        fyp.setFont(new Font("Calibri", Font.BOLD, 30));
        JLabel roomN =new JLabel("<html>Room Number<br></html>", SwingConstants.CENTER);
        roomN.setFont(new Font("Calibri", Font.ITALIC, 24));
        JLabel password =new JLabel("<html>Password<br></html>", SwingConstants.CENTER);
        password.setFont(new Font("Calibri", Font.ITALIC, 24));

        confirm = new JButton("Check-In");
        confirm.setFont(new Font("Calibri", Font.ITALIC, 24));
        CheckOut = new JButton("Check-Out");
        CheckOut.setFont(new Font("Calibri", Font.ITALIC, 24));
        JPanel panel =new JPanel(new FlowLayout());
        panel.add(confirm);
        panel.add(CheckOut);

        JPanel p = new JPanel(new GridLayout(1,4));
        rn = new JTextField();
        rn.setFont(new Font("Calibri", Font.PLAIN, 24));
        rn.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lll =new JLabel();
        JLabel ll =new JLabel();
        p.add(lll);p.add(rn);p.add(ll);

        JPanel r =new JPanel(new GridLayout(1,4));
        pWord = new JPasswordField();
        pWord.setFont(new Font("Calibri", Font.PLAIN, 24));
        pWord.setHorizontalAlignment(SwingConstants.CENTER);
        pWord.setEchoChar('*');
        JLabel rr =new JLabel();
        JLabel rrr =new JLabel();
        r.add(rr);
        r.add(pWord);
        r.add(rrr);

        JPanel err = new JPanel(new MigLayout("Center"));
        JTextField tf = new JTextField(30);
        tf.setFont(new Font("Calibri", Font.PLAIN, 16));
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        tf.setEditable(false);
        err.add(tf,"wrap");

        JPanel conn = new JPanel(new MigLayout("Center"));

        status431 = new JLabel("Room 431 Status - Offline");
        status431.setFont(new Font("Calibri", Font.PLAIN, 14));
        err.add(status431,"center,wrap");
        status436 = new JLabel("Room 436 Status - Offline");
        status436.setFont(new Font("Calibri", Font.PLAIN, 14));
        conn.add(status436,"center,wrap");
        status593 = new JLabel("Room 593 Status - Offline");
        status593.setFont(new Font("Calibri", Font.PLAIN, 14));
        conn.add(status593,"center,wrap");

        pane1Panel.add(name);
        pane1Panel.add(course);
        pane1Panel.add(fyp);
        pane1Panel.add(roomN);
        pane1Panel.add(p);
        pane1Panel.add(password);
        pane1Panel.add(r);
        pane1Panel.add(panel);
        pane1Panel.add(err);
        pane1Panel.add(conn);
        tabPane.add(pane1Panel);


        frame.getContentPane().add(northPanel, "North");
        frame.getContentPane().add(westPanel, "West");
        frame.getContentPane().add(eastPanel, "Center");
        eastPanel.setLayout(new MigLayout("center"));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JFrame frame3 = new JFrame("Login");
        frame3.setSize(330,160);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel Panel = new JPanel(new MigLayout("center"));
        JLabel old = new JLabel("Username:*");
        old.setFont(new Font("Calibri", Font.PLAIN, 14));
        JLabel newP = new JLabel("Password:*");
        newP.setFont(new Font("Calibri", Font.PLAIN, 14));
        JTextField user = new JTextField(15);
        user.setFont(new Font("Calibri", Font.PLAIN, 14));
        user.setHorizontalAlignment(SwingConstants.CENTER);
        JPasswordField newTA = new JPasswordField(15);
        newTA.setFont(new Font("Calibri", Font.PLAIN, 14));
        newTA.setHorizontalAlignment(SwingConstants.CENTER);

        JButton con = new JButton("Confirm");
        con.setFont(new Font("Calibri", Font.ITALIC, 14));
        JButton forgot = new JButton("Forgot Password?");
        forgot.setFont(new Font("Calibri", Font.ITALIC, 14));
        JTextField Err = new JTextField(30);
        Err.setFont(new Font("Calibri", Font.PLAIN, 14));
        Err.setHorizontalAlignment(SwingConstants.CENTER);
        Err.setEditable(false);
        con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPassword = newTA.getText();
                Username=user.getText();
                if(NewPassword.equals("")||Username.equals("")){
                    Err.setText("Please Enter Username and Password");
                    newTA.setText("");
                    user.setText("");
                }else if(Username.equals("admin")&& NewPassword.equals(CleanerApp)){
                    frame.setVisible(true);
                    frame3.setVisible(false);
                    Err.setText("");
                    newTA.setText("");
                    user.setText("");
                }else{
                    Err.setText("Incorrect Username or Password");
                    newTA.setText("");
                    user.setText("");
                }

            }
        });
        forgot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Err.setText("New Password Has Been Sent to Email");
                    newTA.setText("");
                    user.setText("");
                    SendMail();
                }catch (MessagingException m){

                }
            }
        });

        Panel.add(old);
        Panel.add(user,"wrap");
        Panel.add(newP);
        Panel.add(newTA,"wrap");
        Panel.add(con,"span, split 2, center");
        Panel.add(forgot,"wrap");
        Panel.add(Err,"span,center,wrap");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame3.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame3.getHeight()) / 2);
        frame3.setLocation(x, y);
        frame3.add(Panel);
        frame3.setVisible(true);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = pWord.getText();
                String room =rn.getText();
                String date_time = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss").format(Calendar.getInstance().getTime());
                if (password.equals("")) {
                    tf.setText("ENTER ROOM NUMBER & PASSWORD");
                }else if(room.equals("431") && CheckIn431==0){

                    if (room431ID ==-1){
                        tf.setText("ROOM: 431 NOT CONNECTED");
                    }else{
                        DatabaseCheckIn("431",date_time);
                        tf.setText("ROOM: 431 CHECKED IN");
                        CST[room431ID].SendData("LockClean..");
                        room431Pword = room+" "+password;
                        Pword431=password;
                        appRoom431 = "roomPWORD431"+" "+password;
                        System.out.println("Confirm Room 431:"+room431Pword);
                        CheckIn431=1;
                    }
                }else if(room.equals("431") && CheckIn431==1){
                    tf.setText("ROOM: 431 NOT CHECKED OUT");
                }else if(room.equals("436")&& CheckIn436==0){

                    if (room436ID ==-1){
                        tf.setText("ROOM: 436 NOT CONNECTED");
                    }else{
                        DatabaseCheckIn("436",date_time);
                        tf.setText("ROOM: 436 CHECKED IN");
                        CST[room436ID].SendData("LockClean..");
                        room436Pword=room+" "+password;
                        Pword436=password;
                        appRoom436 = "roomPWORD436"+" "+password;
                        System.out.println("Confirm Room 436:"+room436Pword);
                        CheckIn436=1;
                    }
                }else if(room.equals("436") && CheckIn436==1){
                    tf.setText("ROOM: 436 NOT CHECKED OUT");
                }else if(room.equals("593")&& CheckIn593==0){

                    if (room593ID ==-1){
                        tf.setText("ROOM: 593 NOT CONNECTED");
                    }else{
                        DatabaseCheckIn("593",date_time);
                        tf.setText("ROOM: 593 CHECKED IN");
                        CST[room593ID].SendData("LockClean..");
                        room593Pword=room +" "+password;
                        Pword593=password;
                        appRoom593 = "roomPWORD593"+" "+password;
                        System.out.println("Confirm Room 593:"+room593Pword);
                        CheckIn593=1;
                    }
                }else if(room.equals("593") && CheckIn593==1){
                    tf.setText("ROOM: 593 NOT CHECKED OUT");
                }else{
                    tf.setText("INVALID ROOM");
                }
                System.out.println("appID: "+appID);
                pWord.setText("");
                rn.setText("");
            }
        });

        CheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String room =rn.getText();
                String password ="NotSet";
                String date_time = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss").format(Calendar.getInstance().getTime());
                if(room.equals("431") && CheckIn431==1){

                    if (room431ID ==-1){
                        tf.setText("ROOM: 431 NOT CONNECTED");
                    }else{
                        DatabaseCheckOut("431",date_time);
                        tf.setText("ROOM: 431 CHECKED OUT");
                        CST[room431ID].SendData("UnLocked..");
                        room431Pword = room+" "+password;
                        Pword431=password;
                        appRoom431 = "roomPWORD431"+" "+password;
                        System.out.println("Confirm Room 431:"+room431Pword);
                        Cleaner431="no";
                        CheckIn431=0;
                    }
                }else if(room.equals("431") && CheckIn431==0){
                    tf.setText("ROOM: 431 NOT CHECKED IN");
                }else if(room.equals("436")&& CheckIn436==1){

                    if (room436ID ==-1){
                        tf.setText("ROOM: 436 NOT CONNECTED");
                    }else{
                        DatabaseCheckOut("436",date_time);
                        tf.setText("ROOM: 436 CHECKED OUT");
                        CST[room436ID].SendData("UnLocked..");
                        room436Pword=room+" "+password;
                        Pword436=password;
                        appRoom436 = "roomPWORD436"+" "+password;
                        System.out.println("Confirm Room 436:"+room436Pword);
                        Cleaner436="no";
                        CheckIn436=0;
                    }
                }else if(room.equals("436") && CheckIn436==0){
                    tf.setText("ROOM: 436 NOT CHECKED IN");
                }else if(room.equals("593")&& CheckIn593==1){

                    if (room593ID ==-1){
                        tf.setText("ROOM: 593 NOT CONNECTED");
                    }else{
                        DatabaseCheckOut("593",date_time);
                        tf.setText("ROOM: 593 CHECKED OUT");
                        CST[room593ID].SendData("UnLocked");
                        room593Pword=room +" "+password;
                        Pword593=password;
                        appRoom593 = "roomPWORD593"+" "+password;
                        System.out.println("Confirm Room 593:"+room593Pword);
                        Cleaner593="no";
                        CheckIn593=0;
                    }
                }else if(room.equals("593") && CheckIn593==0){
                    tf.setText("ROOM: 593 NOT CHECKED IN");
                }else{
                    tf.setText("INVALID ROOM");
                }
                System.out.println("appID: "+appID);
                pWord.setText("");
                rn.setText("");
            }
        });

        //**********************************************  TAB ROOM CONFIG*****************************************************************
        westPanel.setLayout(new MigLayout());

        JLabel Header =new JLabel("Automated Hotel\n");
        Clear = new JButton("CLEAR");
        Database = new JButton("DATABASE");
        DoorLock = new JButton("LOCK/UNLOCK ROOM");
        RoomNo = new JButton("ROOM NUMBER");
        GuestButton = new JButton("GUEST PASSWORD");
        CleanerPWord = new JButton("MASTER PASSWORD");
        history = new JButton("ROOM HISTORY");
        logout = new JButton("LOG-OUT");
        Clear.setFont(new Font("Calibri", Font.BOLD, 14));
        Database.setFont(new Font("Calibri", Font.BOLD, 14));
        RoomNo.setFont(new Font("Calibri", Font.BOLD, 14));
        DoorLock.setFont(new Font("Calibri", Font.BOLD, 14));
        CleanerPWord.setFont(new Font("Calibri", Font.BOLD, 14));
        GuestButton.setFont(new Font("Calibri", Font.BOLD, 14));
        history.setFont(new Font("Calibri", Font.BOLD, 14));
        logout.setFont(new Font("Calibri", Font.BOLD, 14));

        westPanel.add(RoomNo,"center,width 80:150:150,wrap");
        westPanel.add(DoorLock,"center,width 80:150:150,wrap");
        westPanel.add(CleanerPWord,"center,width 80:150:150,wrap");
        westPanel.add(GuestButton,"center,width 80:150:150,wrap");
        westPanel.add(history,"center,width 80:150:150,wrap");
        westPanel.add(Database,"center,width 80:150:150,wrap");
        westPanel.add(Clear,"center,width 80:150:150,wrap");
        westPanel.add(logout,"center,width 80:150:150,wrap");
        Header.setFont(new Font("Calibri", Font.BOLD, 32));
        northPanel.add(Header);
        northPanel.setBackground(Color.red);

        Txt1 = new JTextArea(38, 32);
        Txt1.setEditable(false);
        Txt1.setLineWrap(true);
        Txt1.setWrapStyleWord(true);
        Txt1.setFont(new Font("Calibri", Font.PLAIN, 16));
        Txt1.append("\n");

        Txt5 = new JTextArea(38, 32);
        Txt5.setEditable(false);
        Txt5.setLineWrap(true);
        Txt5.setWrapStyleWord(true);
        Txt5.setFont(new Font("Calibri", Font.PLAIN, 16));
        Txt5.append("\n");

        Txt2 = new JTextArea(38, 32);
        Txt2.setEditable(false);
        Txt2.setLineWrap(true);
        Txt2.setWrapStyleWord(true);
        Txt2.setFont(new Font("Calibri", Font.PLAIN, 16));
        Txt2.append("\n");


        JLabel headers = new JLabel(" Status:                                     Date:              Time:" +
                "                Room Config:                                                                          Connections:");
        headers.setFont(new Font("Calibri", Font.BOLD, 17));
        eastPanel.add(headers,"wrap");
        eastPanel.add(new JScrollPane(Txt1),"span, split 3, center");
        eastPanel.add(new JScrollPane(Txt5));
        eastPanel.add(new JScrollPane(Txt2));
        JPanel pCol = new JPanel();
        tabPane1.add((pCol),BorderLayout.SOUTH);
        tabPane1.add(westPanel,BorderLayout.LINE_START);
        tabPane1.add(eastPanel,BorderLayout.CENTER);

        tabbedPane.addTab("Check-In",tabPane);
        tabbedPane.addTab("Room Config",tabPane1);
        tabbedPane.addTab("Cleaning Service",tabPane2);
        tabbedPane.addTab("Restaurant Service",tabPane3);
        tabbedPane.setFont(new Font("Calibri", Font.ITALIC, 16));

        Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Txt1.setText("");
                Txt5.setText("");
                Txt2.setText("");
                //Txt1.append("Status:\t\tDate:              Time:\n\n");
            }
        });

        DoorLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        RoomLockGUI();
                    }
                });
            }
        });

        Database.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        DatabaseGUI();
                    }
                });
            }
        });

        RoomNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        RoomNoGUI();
                    }
                });
            }
        });

        CleanerPWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        CleanerPassword();
                    }
                });
            }
        });

        GuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        GuestPassword();
                    }
                });
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History();
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.setVisible(true);
                frame.setVisible(false);
            }
        });
        //**********************************************  TAB CLEANED*****************************************************************
        P2_Txt1 = new JTextArea(38, 500);
        P2_Txt1.setEditable(false);
        P2_Txt1.setLineWrap(true);
        P2_Txt1.setWrapStyleWord(true);
        P2_Txt1.append("\n");
        P2_Txt1.setFont(new Font("Calibri", Font.PLAIN, 16));

        p2_label=new JLabel("<html>CLEANING SERVICE<br></html>", SwingConstants.CENTER);
        p2_label.setFont(new Font("Calibri", Font.BOLD, 30));

        JPanel tabpanel2=new JPanel(new MigLayout("Center"));
        JLabel number = new JLabel("Room Cleaned(ID)");
        number.setFont(new Font("Calibri", Font.BOLD, 24));
        JTextField CleanedNum = new JTextField(6);
        CleanedNum.setHorizontalAlignment(SwingConstants.CENTER);
        CleanedNum.setFont(new Font("Calibri", Font.PLAIN, 24));
        JButton cleaned = new JButton("Confirm");
        cleaned.setFont(new Font("Calibri", Font.ITALIC, 24));

        JLabel header = new JLabel(" ID                     Date:                                      Service:                                     " +
                "   Room:                             Time:                                            Extra Pillows:" +
                "                                          Extra Towels:");
        header.setFont(new Font("Calibri", Font.BOLD, 17));

        tabpanel2.add(p2_label,"span,center,wrap");
        tabpanel2.add(header,"span,wrap");
        tabpanel2.add(new JScrollPane(P2_Txt1),"span,center,wrap");
        tabpanel2.add(number,"span,split 3,center");
        tabpanel2.add(CleanedNum);
        tabpanel2.add(cleaned);

        tabPane2.add(tabpanel2);
        cleaned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numCleaned = CleanedNum.getText();

                try {
                    if(numCleaned.equals("")){

                    }else {
                        int lineNumber = Integer.parseInt(numCleaned.trim());
                        lineNumber = lineNumber + lineNumber + 1;
                        Highlighter.HighlightPainter painter;
                        int startIndex = P2_Txt1.getLineStartOffset(lineNumber);
                        int endIndex = P2_Txt1.getLineEndOffset(lineNumber);
                        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                        P2_Txt1.getHighlighter().addHighlight(startIndex, endIndex, painter);
                        String restString = P2_Txt1.getText().substring(startIndex, endIndex);
                        if(restString.contains("431")){
                            CST[room431ID].SendData("LockClean..");
                            Cleaner431="no";
                        }else if(restString.contains("436")){
                            CST[room436ID].SendData("LockClean..");
                            Cleaner436="no";
                        }else if(restString.contains("593")){
                            CST[room593ID].SendData("LockClean..");
                            Cleaner436="no";
                        }else{

                        }
                    }
                }catch (Exception i){

                }
            }
        });
        //******************************************** TAB RESTAURANT *******************************************************************

        JLabel title = new JLabel("RESTAURANT SERVICE");
        title.setFont(new Font("Calibri", Font.BOLD, 30));

        display = new JTextArea (16, 75);
        display.setEditable ( false ); // set textArea non-editable
        display.setFont(new Font("Calibri", Font.PLAIN, 16));
        JScrollPane scroll = new JScrollPane ( display);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        display.append("\n");

        JLabel lab = new JLabel("ID:");
        lab.setFont(new Font("Calibri", Font.BOLD, 26));
        JTextField t = new JTextField(4);
        t.setFont(new Font("Calibri", Font.PLAIN, 24));
        JButton but = new JButton("Add");
        but.setFont(new Font("Calibri", Font.ITALIC, 24));


        JLabel l = new JLabel("Room:");
        l.setFont(new Font("Calibri", Font.BOLD, 26));
        JTextField roomt = new JTextField(6);
        roomt.setFont(new Font("Calibri", Font.PLAIN, 24));
        roomt.setEditable ( false ); // set textArea non-editable
        JLabel tim = new JLabel("Time:");
        tim.setFont(new Font("Calibri", Font.BOLD, 26));
        JTextField roomtime = new JTextField(6);
        roomtime.setFont(new Font("Calibri", Font.PLAIN, 24));
        roomtime.setEditable ( false ); // set textArea non-editable
        JLabel Table = new JLabel("Table For:");
        Table.setFont(new Font("Calibri", Font.BOLD, 26));
        JTextField tableamount = new JTextField(6);
        tableamount.setEditable ( false ); // set textArea non-editable
        tableamount.setFont(new Font("Calibri", Font.PLAIN, 24));


        JButton Rbutton1= new JButton("Confirm");
        Rbutton1.setFont(new Font("Calibri", Font.ITALIC, 24));
        JButton Rbutton2= new JButton("Decline");
        Rbutton2.setFont(new Font("Calibri", Font.ITALIC, 24));

        JTextField restErr = new JTextField(30);
        restErr.setFont(new Font("Calibri", Font.PLAIN, 16));
        restErr.setHorizontalAlignment(SwingConstants.CENTER);
        restErr.setEditable(false);

        JLabel headerr = new JLabel(" ID                                   Room Number " +
                "                                Time                                 Table For" +
                "                   Booking Status");
        headerr.setFont(new Font("Calibri", Font.BOLD, 17));

        tabPane3.add (title,"span,center,wrap");
        tabPane3.add (headerr,"span,wrap");
        tabPane3.add (scroll,"span,center,wrap");
        tabPane3.add(lab,"span, split 3, center");
        tabPane3.add(t);
        tabPane3.add(but,"span,wrap");
        tabPane3.add(new JLabel("   "),"span,wrap");
        tabPane3.add(l,"span, split 6, center");
        tabPane3.add(roomt);
        tabPane3.add(tim);
        tabPane3.add(roomtime);
        tabPane3.add(Table);
        tabPane3.add(tableamount,"wrap");
        tabPane3.add(new JLabel(" "),"center,wrap");
        tabPane3.add(Rbutton1,"span, split 3, center");
        tabPane3.add(new JLabel("   "));
        tabPane3.add(Rbutton2,"center,wrap");
        tabPane3.add(new JLabel(" "),"center,wrap");
        tabPane3.add(restErr,"center,wrap");


        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String RoomRest = t.getText();

                try {
                    if(RoomRest.equals("")){
                        restErr.setText("PLEASE ENTER ROOM ID");
                    }else {
                        int lineNumber = Integer.parseInt(RoomRest);
                        lineNumber = lineNumber + lineNumber + 1;

                        startIndexRest = display.getLineStartOffset(lineNumber);
                        endIndexRest = display.getLineEndOffset(lineNumber);

                        restString2 = display.getText().substring(startIndexRest, endIndexRest);
                        String[] parts = restString2.split("\\s+");
                        roomt.setText("" + parts[2]);
                        roomtime.setText("" + parts[3]);
                        tableamount.setText("" + parts[4]);

                        restErr.setText("");
                        if(restString2.contains("431")){
                            endIndex431=endIndexRest;
                        }else if(restString2.contains("436")){
                            endIndex436=endIndexRest;
                        }else if(restString2.contains("593")){
                            endIndex593=endIndexRest;
                        }
                    }
                }catch (Exception i){

                }
            }
        });
        Rbutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String room = roomt.getText().toString();
                    String restTime = roomtime.getText().toString();
                    String people = tableamount.getText().toString();
                    if(room.equals("")) {
                        restErr.setText("PLEASE ENTER ROOM ID");
                    }else{
                        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                        String notificationTrue = "NotificationTrue " + room + " " + restTime + " " + people;
                        CST[appID].SendData(notificationTrue);
                        restErr.setText("ROOM: "+room+" - BOOKING CONFIRMED");

                        if(room.equals("431")){
                            display.insert("\t        Booking Confirmed",endIndex431-1);
                        }else if(room.equals("436")){
                            display.insert("\t        Booking Confirmed",endIndex436-1);
                        }else if(room.equals("593")){
                            display.insert("\t        Booking Confirmed",endIndex593-1);
                        }
                    }
                }catch (Exception x){

                }

            }
        });

        Rbutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String room = roomt.getText().toString();
                    String restTime = roomtime.getText().toString();
                    String people = tableamount.getText().toString();
                    if(room.equals("")){
                        restErr.setText("PLEASE ENTER ROOM ID");
                    }else {
                        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
                        String notificationFalse = "NotificationFalse " + room + " " + restTime + " " + people;
                        CST[appID].SendData(notificationFalse);
                        restErr.setText("ROOM: "+room+" - BOOKING DECLINED");
                        if(room.equals("431")){
                            display.insert("\t        Booking Declined",endIndex431-1);
                        }else if(room.equals("436")){
                            display.insert("\t        Booking Declined",endIndex436-1);
                        }else if(room.equals("593")){
                            display.insert("\t        Booking Declined",endIndex593-1);
                        }
                    }

                }catch (Exception x){

                }

            }
        });

        //**********************************************  DISPLAY GUI*****************************************************************
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(tabbedPane);
        Socket();
    }
    /*****************************************************************************************************
     *Gui that connects to the K64f
     ****************************************************************************************************/
    private void RoomLockGUI(){
        JFrame frame3 = new JFrame("Lock Room");
        frame3.setSize(350,140);
        JPanel Panel = new JPanel(new MigLayout("center"));


        RLText = new JTextField("");
        RLText =new JTextField(7);
        RLText.setEditable(true);
        label = new JLabel("Room No.*");
        Lock = new JButton("Lock Room");
        unLock = new JButton("Unlock Room");
        CleanerLock=new JButton("Cleaner Lock");
        JButton close=new JButton("Close");
        ErrorText = new JTextField("");
        ErrorText=new JTextField(15);
        ErrorText.setEditable(false);

        RLText.setHorizontalAlignment(SwingConstants.CENTER);
        ErrorText.setHorizontalAlignment(SwingConstants.CENTER);
        Lock.setFont(new Font("Calibri", Font.ITALIC, 14));
        unLock.setFont(new Font("Calibri", Font.ITALIC, 14));
        CleanerLock.setFont(new Font("Calibri", Font.ITALIC, 14));
        close.setFont(new Font("Calibri", Font.ITALIC, 14));


        Panel.add(label,"span,split 2,center");
        Panel.add(RLText,"wrap");
        Panel.add(Lock,"span,split 4,center");
        Panel.add(unLock);
        Panel.add(CleanerLock);
        Panel.add(close,"wrap");
        Panel.add(ErrorText,BorderLayout.SOUTH);

        frame3.add(Panel);
        frame3.setVisible(true);
        Lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Result = RLText.getText().toString();

                if(number431.equals(Result) && room431ID !=-1){
                    CST[room431ID].SendData("Lock..");
                    RLText.setText("");
                    ErrorText.setText("Room Locked");
                    Txt5.append("Room Number "+number431 +" Locked\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number431.equals(Result) && room431ID ==-1){
                    ErrorText.setText("Room 431 Not Connected");
                }else if(number436.equals(Result) && room436ID !=-1){
                    CST[room436ID].SendData("Lock..");
                    RLText.setText("");
                    ErrorText.setText("Room Locked");
                    Txt5.append("Room Number "+number436 +" Locked\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number436.equals(Result) && room436ID ==-1){
                    ErrorText.setText("Room 436 Not Connected");
                }else if(number593.equals(Result) && room593ID !=-1){
                    CST[room593ID].SendData("Lock..");
                    RLText.setText("");
                    ErrorText.setText("Room Locked");
                    Txt5.append("Room Number "+number593 +" Locked\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number593.equals(Result) && room593ID ==-1){
                    ErrorText.setText("Room 593 Not Connected");
                }else{
                    ErrorText.setText("Invaild Room Number");
                    RLText.setText("");
                }
            }
        });
        CleanerLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Result = RLText.getText().toString();

                if(number431.equals(Result) && room431ID !=-1){
                    CST[room431ID].SendData("LockClean..");
                    RLText.setText("");
                    ErrorText.setText("Room Locked From Cleaner");
                    Txt5.append("Room Number "+number431 +" Locked From Cleaner\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number431.equals(Result) && room431ID ==-1){
                    ErrorText.setText("Room 431 Not Connected");
                }else if(number436.equals(Result)&& room436ID !=-1){
                    CST[room436ID].SendData("LockClean..");
                    RLText.setText("");
                    ErrorText.setText("Room Locked From Cleaner");
                    Txt5.append("Room Number "+number436 +" Locked From Cleaner\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number436.equals(Result) && room436ID ==-1){
                    ErrorText.setText("Room 436 Not Connected");
                }else if(number593.equals(Result)&& room593ID !=-1){
                    CST[room593ID].SendData("LockClean..");
                    RLText.setText("");
                    ErrorText.setText("Room Locked From Cleaner");
                    Txt5.append("Room Number "+number593 +" Locked From Cleaner\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number593.equals(Result) && room593ID ==-1){
                    ErrorText.setText("Room 593 Not Connected");
                }else{
                    ErrorText.setText("Invaild Room Number");
                    RLText.setText("");
                }
            }
        });
        unLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Result = RLText.getText().toString();

                if(number431.equals(Result)&& room431ID !=-1){
                    CST[room431ID].SendData("UnLocked..");
                    RLText.setText("");
                    ErrorText.setText("Room Unlocked");
                    Txt5.append("Room Number "+number431 +" Unlocked\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number431.equals(Result) && room431ID ==-1){
                    ErrorText.setText("Room 431 Not Connected");
                }else if(number436.equals(Result)&& room436ID !=-1){
                    CST[room436ID].SendData("UnLocked..");
                    RLText.setText("");
                    ErrorText.setText("Room Unlocked");
                    Txt5.append("Room Number "+number436 +" Unlocked\n\n");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number436.equals(Result) && room436ID ==-1){
                    ErrorText.setText("Room 436 Not Connected");
                }else if(number593.equals(Result)&& room593ID !=-1){
                    CST[room593ID].SendData("UnLocked..");
                    RLText.setText("");
                    ErrorText.setText("Room Unlocked");
                    Txt5.append("Room Number "+number593 +" Unlocked\n\n");
                    RLText.setText("");
                    frame3.setVisible(false);
                    frame3.dispose();
                }else if(number593.equals(Result) && room593ID ==-1){
                    ErrorText.setText("Room 593 Not Connected");
                }else{
                    ErrorText.setText("Invaild Room Number");
                    RLText.setText("");
                }
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.dispose();
            }
        });

    }
    /*****************************************************************************************************
     *Gui that displays the contents of the Database
     ****************************************************************************************************/
    private void DatabaseGUI(){
        JFrame frame2 = new JFrame("Database Search");

        frame2.setSize(1200,768);

        JPanel upperPanel = new JPanel();
        JPanel lowerPanel = new JPanel(new MigLayout());
        frame2.getContentPane().add(upperPanel, "North");
        frame2.getContentPane().add(lowerPanel, "South");
        upperPanel.setSize(500,100);
        upperPanel.setLayout(new FlowLayout());
        DSText = new JTextField("");
        DSText=new JTextField(10);
        DSText.setEditable(true);

        DataSearch = new JButton("Search Database");
        DataSearch.setFont(new Font("Calibri", Font.ITALIC, 16));
        upperPanel.add(DataSearch);
        upperPanel.add(DSText);
        upperPanel.setBackground(Color.red);
        Txt3 = new JTextArea(25, 30);
        Txt3.setEditable(false);
        Txt3.setLineWrap(true);
        Txt3.setWrapStyleWord(true);
        Txt3.setFont(new Font("Calibri", Font.PLAIN, 16));
        Txt3.append("\n");

        Txt4 = new JTextArea(25, 30);
        Txt4.setEditable(false);
        Txt4.setLineWrap(true);
        Txt4.setWrapStyleWord(true);
        Txt4.setFont(new Font("Calibri", Font.PLAIN, 16));
        Txt4.append("\n");

        JButton close = new JButton("Close");
        close.setFont(new Font("Calibri", Font.ITALIC, 14));

        JLabel label = new JLabel(" Contents of Database:                                                       Search Results:");
        JLabel label2 = new JLabel(" Status:                                    Date:              Time:            Status:                                    Date:              Time:");
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label2.setFont(new Font("Calibri", Font.BOLD, 16));
        lowerPanel.add(label,"wrap");
        lowerPanel.add(label2,"wrap");
        lowerPanel.add(new JScrollPane(Txt3),"span,split2,center");
        lowerPanel.add(new JScrollPane(Txt4),"wrap");
        lowerPanel.add(close,"span,center");
        frame2.pack();
        frame2.setVisible(true);
        DatabaseSearch();

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2.dispose();
            }
        });
        DataSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = DSText.getText();
                if(search.equals("")){

                }else {
                    String[] lines = Txt3.getText().split("\\n");
                    for (int i = 0; i < lines.length; i++) {
                        if (lines[i].contains(search)) {
                            Txt4.append(lines[i] + "\n\n");
                        }
                    }
                }

            }
        });

    }
    /*****************************************************************************************************
     *Gets the Contents of everything that is stored in the Database
     ****************************************************************************************************/
    public void DatabaseSearch() {
        (new dataSearch()).execute();
    }
    class dataSearch extends SwingWorker<Void, String> {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                System.out.print("Database");
                String myDriver = "com.mysql.jdbc.Driver";
                Class.forName(myDriver);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
                Statement statement = conn.createStatement();
                String sql = ("SELECT * FROM hotel;");
                PreparedStatement st = conn.prepareStatement(sql);
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()) {
                    String str1 = rs.getString("card_scanned");
                    String str2 = rs.getString("date_time");
                    Txt3.append(str1+"\t\t"+str2+"\n\n");
                }
                conn.close();


            }catch(Exception io){}
            return null;
        }
    }
    /*****************************************************************************************************
     *
     ****************************************************************************************************/
    public void DatabaseHistory() {
        (new Search()).execute();
    }
    class Search extends SwingWorker<Void, String> {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                System.out.print("Database");
                String myDriver = "com.mysql.jdbc.Driver";
                Class.forName(myDriver);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
                Statement statement = conn.createStatement();

                String sql = ("SELECT * FROM checkinout;");
                PreparedStatement st = conn.prepareStatement(sql);
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()) {
                    String str1 = rs.getString("room");
                    String str2 = rs.getString("checkin");
                    String str3 = rs.getString("checkout");
                    hist.append(str1+"\t\t"+str2+"\t\t"+str3+"\n\n");
                }
                conn.close();


            }catch(Exception io){}
            return null;
        }
    }
    /*****************************************************************************************************
     * Change a room number if there is noone checked into that room at the time
     ****************************************************************************************************/
    private void RoomNoGUI(){
        JFrame frame3 = new JFrame("Room No. Change");
        frame3.setSize(350,160);
        JPanel Panel = new JPanel(new MigLayout("center"));

        RoomNoText = new JTextField("");
        RoomNoText =new JTextField(15);
        RoomNoText.setEditable(true);
        CText = new JTextField("");
        CText =new JTextField(15);
        CText.setEditable(true);
        labelR = new JLabel("Room No.*   ");
        labelC = new JLabel("Change To*  ");
        UpdateChange = new JButton("Update Room Number");
        JButton close = new JButton("Close");
        ErrorText = new JTextField("");
        ErrorText=new JTextField(30);
        ErrorText.setEditable(false);

        RoomNoText.setHorizontalAlignment(SwingConstants.CENTER);
        CText.setHorizontalAlignment(SwingConstants.CENTER);
        ErrorText.setHorizontalAlignment(SwingConstants.CENTER);
        UpdateChange.setFont(new Font("Calibri", Font.ITALIC, 14));
        close.setFont(new Font("Calibri", Font.ITALIC, 14));


        Panel.add(labelR,"span, split 2, center");
        Panel.add(RoomNoText,"wrap");
        Panel.add(labelC,"span, split 2, center");
        Panel.add(CText,"wrap");
        Panel.add(UpdateChange,"center,split 2");
        Panel.add(close, "wrap");
        Panel.add(ErrorText,"wrap");
        frame3.add(Panel);
        frame3.setVisible(true);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.dispose();
            }
        });
        UpdateChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String RoomNo = RoomNoText.getText().toString();
                String RoomNoChange = CText.getText().toString();

                if(RoomNo.equals("")||RoomNoChange.equals("")||RoomNo.equals("")&&RoomNoChange.equals("")) {
                    ErrorText.setText("Please Enter Room Numbers");
                }else if(RoomNo.equals("431")&& room431ID==-1 ||RoomNo.equals("436")&& room436ID==-1 ||RoomNo.equals("593")&& room593ID==-1){
                    ErrorText.setText("Room "+RoomNo+" is not Connected");
                }else if(RoomNo.equals(RoomNoChange)){
                    ErrorText.setText("Invaild Room Number");
                }else{
                    if (number431.equals(RoomNo) && CheckIn431 ==0) {
                        if(RoomNoChange.equals("593") && room593ID ==-1 ||RoomNoChange.equals("436") && room436ID ==-1 ) {
                            ErrorText.setText("Room Updated");
                            RoomNoText.setText("");
                            Txt5.append("Room Number Changed From " + number431 + " to " + RoomNoChange + "\n\n");
                            CST[room431ID].SendData(RoomNoChange + "");
                            clientCount = 0;
                            frame3.setVisible(false);
                            frame3.dispose();
                        }else{
                            ErrorText.setText("Room "+RoomNoChange+" is already Connected");
                            RoomNoText.setText("");
                            CText.setText("");
                        }
                    }else if(number431.equals(RoomNo) && CheckIn431 ==1){
                        ErrorText.setText("Please Check-Out Guest before Changing");
                    }else if (number436.equals(RoomNo)&& CheckIn436 ==0) {
                        if(RoomNoChange.equals("593") && room593ID ==-1 ||RoomNoChange.equals("431") && room431ID ==-1 ) {
                            ErrorText.setText("Room Updated");
                            RoomNoText.setText("");
                            Txt5.append("Room Number Changed From "+number436 +" to "+RoomNoChange+"\n\n");
                            clientCount = 0;
                            CST[room436ID].SendData(RoomNoChange + "");
                            frame3.setVisible(false);
                            frame3.dispose();
                        } else {
                            ErrorText.setText("Room "+RoomNoChange+" is already Connected");
                            RoomNoText.setText("");
                            CText.setText("");
                        }
                    }else if(number436.equals(RoomNo) && CheckIn436 ==1){
                        ErrorText.setText("Please Check-Out Guest before Changing");
                    }else if (number593.equals(RoomNo)&& CheckIn593 ==0) {
                        if(RoomNoChange.equals("436") && room436ID ==-1 ||RoomNoChange.equals("431") && room431ID ==-1 ) {
                            ErrorText.setText("Room Updated");
                            RoomNoText.setText("");
                            Txt5.append("Room Number Changed From "+number593 +" to "+RoomNoChange+"\n\n");
                            clientCount = 0;
                            CST[room593ID].SendData(RoomNoChange + "");
                            frame3.setVisible(false);
                            frame3.dispose();
                        } else {
                            ErrorText.setText("Room "+RoomNoChange+" is already Connected");
                            RoomNoText.setText("");
                            CText.setText("");
                        }
                    }else if(number593.equals(RoomNo) && CheckIn593 ==1){
                        ErrorText.setText("Please Check-Out Guest before Changing");
                    } else {
                        ErrorText.setText("Invaild Room Number");
                        RoomNoText.setText("");
                        CText.setText("");
                    }
                }
                RoomNoText.setText("");
                CText.setText("");
            }
        });
    }
    /*****************************************************************************************************
     * this is where you can make changes to the master password
     ****************************************************************************************************/
    private void CleanerPassword(){
        JFrame frame3 = new JFrame("Master Password Change");
        frame3.setSize(330,160);
        JPanel Panel = new JPanel(new MigLayout("center"));
        JLabel old = new JLabel("Old Password:*");
        old.setFont(new Font("Calibri", Font.PLAIN, 14));
        JLabel newP = new JLabel("New Password:*");
        newP.setFont(new Font("Calibri", Font.PLAIN, 14));
        JPasswordField oldTA = new JPasswordField(15);
        oldTA.setFont(new Font("Calibri", Font.PLAIN, 14));
        oldTA.setHorizontalAlignment(SwingConstants.CENTER);
        JPasswordField newTA = new JPasswordField(15);
        newTA.setFont(new Font("Calibri", Font.PLAIN, 14));
        newTA.setHorizontalAlignment(SwingConstants.CENTER);

        JButton con = new JButton("Confirm Password");
        con.setFont(new Font("Calibri", Font.ITALIC, 14));

        JButton close = new JButton("Close");
        close.setFont(new Font("Calibri", Font.ITALIC, 14));
        JTextField Err = new JTextField(30);
        Err.setFont(new Font("Calibri", Font.PLAIN, 14));
        Err.setHorizontalAlignment(SwingConstants.CENTER);
        Err.setEditable(false);
        con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPassword = newTA.getText();
                OldPassword=oldTA.getText();
                if(NewPassword.equals("")||OldPassword.equals("")){
                    Err.setText("Please Enter Old and New Password");
                    newTA.setText("");
                    oldTA.setText("");
                }else if(NewPassword.equals(OldPassword)){
                    Err.setText("Passwords Are The Same");
                    newTA.setText("");
                    oldTA.setText("");
                }else if(OldPassword.equals(CleanerApp)){
                    Err.setText("Password Changed");
                    newTA.setText("");
                    oldTA.setText("");
                    CleanerApp = NewPassword;
                }else{
                    Err.setText("Incorrect Old Password");
                    newTA.setText("");
                    oldTA.setText("");
                }

            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.dispose();
            }
        });

        Panel.add(old);
        Panel.add(oldTA,"wrap");
        Panel.add(newP);
        Panel.add(newTA,"wrap");
        Panel.add(con,"span, split 2, center");
        Panel.add(close,"wrap");
        Panel.add(Err,"span,center,wrap");
        frame3.add(Panel);
        frame3.setVisible(true);
    }
    /*****************************************************************************************************
     *Change the guest password
     ****************************************************************************************************/
    private void GuestPassword(){
        JFrame frame3 = new JFrame("Guest Password Change");
        frame3.setSize(330,160);
        JPanel Panel = new JPanel(new MigLayout());
        JLabel roomNumber = new JLabel("Room Number:*");
        roomNumber.setFont(new Font("Calibri", Font.PLAIN, 14));
        JLabel newP = new JLabel("New Password:*");
        newP.setFont(new Font("Calibri", Font.PLAIN, 14));

        JTextField num = new JTextField(15);
        num.setFont(new Font("Calibri", Font.PLAIN, 14));
        num.setHorizontalAlignment(SwingConstants.CENTER);

        JPasswordField newPass = new JPasswordField(15);
        newPass.setFont(new Font("Calibri", Font.PLAIN, 14));
        newPass.setHorizontalAlignment(SwingConstants.CENTER);

        JButton con = new JButton("Confirm Password");
        con.setFont(new Font("Calibri", Font.ITALIC, 14));

        JButton close = new JButton("Close");
        close.setFont(new Font("Calibri", Font.ITALIC, 14));

        JTextField Err = new JTextField(30);
        Err.setFont(new Font("Calibri", Font.PLAIN, 14));
        Err.setHorizontalAlignment(SwingConstants.CENTER);
        Err.setEditable(false);

        Panel.add(roomNumber);
        Panel.add(num,"wrap");
        Panel.add(newP);
        Panel.add(newPass,"wrap");
        Panel.add(con,"span,center,split 2");
        Panel.add(close,"wrap");
        Panel.add(Err,"span,center,wrap");

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.dispose();
            }
        });
        con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ROOM = num.getText();
                String PASS = newPass.getText();
                if(ROOM.equals("")||PASS.equals("")){
                    Err.setText("PLEASE ENTER ROOM NUMBER AND PASSWORD");
                    num.setText("");
                    newPass.setText("");
                }else if(ROOM.equals("431") && CheckIn431 == 1){
                    appRoom431 = "roomPWORD431"+" "+PASS;
                    room431Pword = "431 "+PASS;
                    Err.setText("431 PASSWORD CHANGED");
                    num.setText("");
                    newPass.setText("");
                }else if(ROOM.equals("431") && CheckIn431 == 0){
                    Err.setText("ROOM 431 NOT CHECKED IN");
                    num.setText("");
                    newPass.setText("");
                }else if(ROOM.equals("436")&& CheckIn436 == 1){
                    appRoom436 = "roomPWORD436"+" "+PASS;
                    room436Pword = "436 "+PASS;
                    Err.setText("436 PASSWORD CHANGED");
                    num.setText("");
                    newPass.setText("");
                }else if(ROOM.equals("436")&& CheckIn436 == 0){
                    Err.setText("ROOM 436 NOT CHECKED IN");
                    num.setText("");
                    newPass.setText("");
                }else if(ROOM.equals("593")&& CheckIn593 == 1){
                    appRoom593 = "roomPWORD593"+" "+PASS;
                    room593Pword = "593 "+PASS;
                    Err.setText("593 PASSWORD CHANGED");
                    num.setText("");
                    newPass.setText("");
                }else if(ROOM.equals("593")&& CheckIn593 == 0){
                    Err.setText("ROOM 593 NOT CHECKED IN");
                    num.setText("");
                    newPass.setText("");
                }else{
                    Err.setText("INVALID ROOM");
                    num.setText("");
                    newPass.setText("");
                }

            }
        });

        frame3.add(Panel);
        frame3.setVisible(true);
    }
    /*****************************************************************************************************
     * Displays the contents of the database and shows if a room is checked in or out
     ****************************************************************************************************/
    private void History(){
        JFrame frame2 = new JFrame();
        frame2.setSize(700,500);
        JPanel Panel = new JPanel(new MigLayout("center"));

        hist = new JTextArea(30, 70);
        hist.setEditable(false);
        hist.setLineWrap(true);
        hist.setWrapStyleWord(true);
        hist.setFont(new Font("Calibri", Font.PLAIN, 16));
        hist.append("\n");
        JLabel label = new JLabel("Guest History");
        label.setFont(new Font("Calibri", Font.BOLD, 24));
        JButton close = new JButton("Close");
        JLabel label1 = new JLabel(" Room:                                     Checked-In:                                                    Checked-Out:");
        label1.setFont(new Font("Calibri", Font.BOLD, 16));
        Panel.add(label,"span,center,wrap");
        Panel.add(label1,"span,wrap");
        Panel.add(new JScrollPane(hist),"center,wrap");
        Panel.add(close,"span,center");
        DatabaseHistory();
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2.dispose();
            }
        });
        frame2.add(Panel);
        frame2.setVisible(true);
    }
    /*****************************************************************************************************
     *Looks for a new client connection
     ****************************************************************************************************/
    public void Socket() {
        (new Sock()).execute();
    }
    class Sock extends SwingWorker<Void, String> {
        @Override
        protected Void doInBackground() throws Exception {
            ServerSocket m_ServerSocket = new ServerSocket(12345);

            CST = new ClientServiceThread[50];
            for(id=0;id<50;id++) {
                clientCount=0;
                System.out.println("...1...");
                Socket clientSocket = m_ServerSocket.accept();
                CST[id] = new ClientServiceThread(clientSocket, id);
                CST[id].start();
            }
            return null;
        }
    }
    /*****************************************************************************************************
     *
     ****************************************************************************************************/
    class ClientServiceThread extends Thread {
        Socket clientSocket;
        int clientID = -1;
        boolean running = true;
        int count=0;

        ClientServiceThread(Socket s, int i) {
            clientSocket = s;
            clientID = i;
        }

        public void run() {
            /*****************************************************************************************************
             *All incoming commands are read and parsed here
             ****************************************************************************************************/
            System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
            try {
                BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (running) {
                    String date_time = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss").format(Calendar.getInstance().getTime());
                    count++;
                    String clientCommand = in.readLine();
                    System.out.println("Client Says :" + clientCommand);
                    /***************************************************************************************************
                     * Connecting The K64F and the Room number
                     * Room Choices are 431,436 and 593
                     **************************************************************************************************/
                    if(clientCommand.equals(null)){

                    }else if(clientCommand.equals("431") && clientCount==0){
                        if(room436ID==clientID){
                            System.out.println("Changing client id");
                            room436ID=-1;
                        }else if(room593ID==clientID){
                            room593ID=-1;
                        }
                        room431ID=clientID;
                        BoardRNum=clientCommand;
                        Txt2.append("Room " + clientCommand + " is Connected\n\n");
                        System.out.print("Connected" + clientCommand);
                        clientCount++;

                    }else if(clientCommand.equals("436") && clientCount==0){
                        if(room431ID==clientID){
                            System.out.println("Changing client id");
                            room431ID=-1;
                        }else if(room593ID==clientID){
                            room593ID=-1;
                        }
                        room436ID=clientID;
                        BoardRNum=clientCommand;
                        Txt2.append("Room " + clientCommand + " is Connected\n\n");
                        System.out.print("Connected" + clientCommand);
                        clientCount++;
                    }else if(clientCommand.equals("593") && clientCount==0){
                        if(room431ID==clientID){
                            System.out.println("Changing client id");
                            room431ID=-1;
                        }else if(room436ID==clientID){
                            room436ID=-1;
                        }
                        room593ID=clientID;
                        BoardRNum=clientCommand;
                        Txt2.append("Room " + clientCommand + " is Connected\n\n");
                        System.out.print("Connected" + clientCommand);
                        clientCount++;
                    }
                    /***************************************************************************************************
                     * Master Key Scans on bedroom Door
                     **************************************************************************************************/
                    if(clientCommand.contains("431Cleaning")){
                        Txt1.append("431 Master Scanned"+"\t" +date_time+"\n\n");
                        Database("Master 431",date_time);
                        CST[appID].SendData("Cleaning 431");
                    }else if(clientCommand.contains("436Cleaning")){
                        Txt1.append("436 Master Scanned"+"\t" +date_time+"\n\n");
                        Database("Master 436",date_time);
                        CST[appID].SendData("Cleaning 436");
                    }else if(clientCommand.contains("593Cleaning")){
                        Txt1.append("593 Master Scanned"+"\t" +date_time+"\n\n");
                        Database("Master 593",date_time);
                        CST[appID].SendData("Cleaning 593");
                    }
                    /***************************************************************************************************
                     * Key Scans on bedroom Door
                     **************************************************************************************************/
                    if(clientCommand.contains("en "+Pword431+"431")){
                        CST[room431ID].SendData("TRUE");
                        Txt1.append("431" +" Scanned"+"\t\t" +date_time+"\n\n");
                        Database("431",date_time);
                    }else if(clientCommand.contains("en "+Pword436+"436")){
                        CST[room436ID].SendData("TRUE");
                        Txt1.append("436" +" Scanned"+"\t\t" +date_time+"\n\n");
                        Database("436",date_time);
                    }else if(clientCommand.contains("en "+Pword593+"593")){
                        CST[room593ID].SendData("TRUE");
                        Txt1.append("593" +" Scanned"+"\t\t" +date_time+"\n\n");
                        Database("593",date_time);
                    }else if(clientCommand.contains("en") && clientCommand.endsWith("593")){
                        CST[room593ID].SendData("FALSE");
                    }else if(clientCommand.contains("en") && clientCommand.endsWith("431")){
                        CST[room431ID].SendData("FALSE");
                    }else if(clientCommand.contains("en") && clientCommand.endsWith("436")){
                        CST[room436ID].SendData("FALSE");
                    }
                    /***************************************************************************************************
                     * Commands that are recieved from the Android App
                     **************************************************************************************************/
                    if(clientCommand.contains("App")){
                        Txt2.append(clientCommand +"\n\n");

                    } else if(clientCommand.contains("Password")){
                        appID=clientID;
                        CST[appID].SendData("connected");
                        clientCount++;
                    } else if(clientCommand.contains("Cleaning Service ")){
                        clientCount++;
                        if(clientCommand.contains(room1)){//room1 =436
                            System.out.print("Room 1;"+room1+".."+room436ID);
                            CST[room436ID].SendData("UnLocked..");
                            Cleaner436="yes";
                            String str[] =clientCommand.split("\\s+");
                            if(str[5].equals("N/A")&&str[6].equals("N/A")){
                                CleaningDetail436 = str[3] + " " + str[4] + " " + str[5] + " " + str[6];
                            }else {
                                CleaningDetail436 = str[3] + " " + str[4] + " " + str[5] + " " + str[8];
                            }
                            System.out.print("Details-----"+CleaningDetail436);
                        }else if(clientCommand.contains(room2)){//room2 =593
                            System.out.print("Room 2;"+room2+".."+room593ID);
                            CST[room593ID].SendData("UnLocked..");
                            Cleaner593="yes";
                            String str[] =clientCommand.split("\\s+");
                            if(str[5].equals("N/A")&&str[6].equals("N/A")){
                                CleaningDetail593 = str[3] + " " + str[4] + " " + str[5] + " " + str[6];
                            }else {
                                CleaningDetail593 = str[3] + " " + str[4] + " " + str[5] + " " + str[8];
                            }
                            System.out.print("Room 2 clean");
                        }else if(clientCommand.contains(room3)){//room3 =431
                            System.out.print("Room 3;"+room3+".."+room431ID);
                            CST[room431ID].SendData("UnLocked..");
                            Cleaner431="yes";
                            String str[] =clientCommand.split("\\s+");
                            if(str[5].equals("N/A")&&str[6].equals("N/A")){
                                CleaningDetail431 = str[3] + " " + str[4] + " " + str[5] + " " + str[6];
                            }else {
                                CleaningDetail431 = str[3] + " " + str[4] + " " + str[5] + " " + str[8];
                            }
                            System.out.print("Room 3 clean");
                        }
                        String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                        P2_Txt1.append(lineNumber+"\t"+date+clientCommand+"\n\n");
                        cleanNum++;
                        lineNumber++;
                    }//End Cleaning Service
                    /***************************************************************************************************
                     * App sends the room number and password and if they are correct the server returns true to the
                     * app to allow access.
                     **************************************************************************************************/
                    if (clientCommand.equals(room431Pword)){
                        CST[appID].SendData("TRUE");
                    }else if(clientCommand.startsWith("431 ")){
                        CST[appID].SendData("FALSE");
                    }else if(clientCommand.equals(room436Pword)){
                        CST[appID].SendData("TRUE");
                    }else if(clientCommand.startsWith("436 ")){
                        CST[appID].SendData("FALSE");
                    }else if(clientCommand.equals(room593Pword)){
                        CST[appID].SendData("TRUE");
                    }else if(clientCommand.startsWith("593 ")){
                        CST[appID].SendData("FALSE");
                    }
                    /***************************************************************************************************
                     *App sends Details for restaurant booking
                     ***************************************************************************************************/
                    if (clientCommand.startsWith("Restaurant 431")){

                        String[] stringspl = clientCommand.split("\\s+");
                        String restaurantDisp = restCount+"\t\t"+stringspl[1]+"\t\t"+stringspl[2]+"\t\t"+stringspl[3];
                        display.append(" "+restaurantDisp+"\n\n");
                        restCount++;
                        RestNum++;
                    }else if (clientCommand.startsWith("Restaurant 436")){

                        String[] stringspl = clientCommand.split("\\s+");
                        String restaurantDisp = restCount+"\t\t"+stringspl[1]+"\t\t"+stringspl[2]+"\t\t"+stringspl[3];
                        display.append(" "+restaurantDisp+"\n\n");
                        restCount++;
                        RestNum++;
                    }else if (clientCommand.startsWith("Restaurant 593")){

                        String[] stringspl = clientCommand.split("\\s+");
                        String restaurantDisp = restCount+"\t\t"+stringspl[1]+"\t\t"+stringspl[2]+"\t\t"+stringspl[3];
                        display.append(" "+restaurantDisp+"\n\n");
                        restCount++;
                        RestNum++;
                    }else
                    /***************************************************************************************************
                    *App sents request to unlock the door. if the password entered is correct return true
                    ***************************************************************************************************/
                    if(clientCommand.equals(appRoom431)){
                        //appID = clientID;
                        CST[appID].SendData("roomtrue");
                        System.out.print("....TRUE.....");
                        CST[room431ID].SendData("apptrue");
                        Txt1.append("431" +" App Unlock"+"\t" +date_time+"\n\n");
                    }else if(clientCommand.equals(appRoom436) ){
                        //appID = clientID;
                        CST[appID].SendData("roomtrue");
                        System.out.print("....TRUE.....");
                        CST[room436ID].SendData("apptrue");
                        Txt1.append("436" +" App Unlock"+"\t" +date_time+"\n\n");
                    }else if(clientCommand.equals(appRoom593) ){
                        //appID = clientID;
                        CST[appID].SendData("roomtrue");
                        System.out.print("....TRUE.....");
                        CST[room593ID].SendData("apptrue");
                        Txt1.append("593" +" App Unlock"+"\t" +date_time+"\n\n");
                    }else if(clientCommand.contains("roomPWORD431") || clientCommand.contains("roomPWORD436") ||clientCommand.contains("roomPWORD593")){
                        //appID = clientID;
                        CST[appID].SendData("roomfalse");
                        System.out.print("....FALSE.....");
                    }
                    /***************************************************************************************************
                     *Cleaner Login from the app
                     ***************************************************************************************************/
                    if(clientCommand.equals("admin"+CleanerApp)){
                        appID = clientID;
                        CST[appID].SendData("CLEANTRUE "+Cleaner431+" "+Cleaner436+" "+Cleaner593);
                        Txt2.append("Master App\n\n");

                    }else if(clientCommand.startsWith("admin")){
                        appID = clientID;
                        CST[appID].SendData("CLEANFALSE");
                    }
                    if(clientCommand.equals("cleaner431")){
                        CST[appID].SendData(CleaningDetail431);
                    }else if(clientCommand.equals("cleaner436")){
                        CST[appID].SendData(CleaningDetail436);
                    }else if(clientCommand.equals("cleaner593")){
                        CST[appID].SendData(CleaningDetail593);
                    }





                    if(clientCommand.equals("Cleaned431")){
                        Highlighter("431");
                        CST[room431ID].SendData("LockClean..");
                        Cleaner431="no";
                        CST[appID].SendData("CLEANTRUE "+Cleaner431+" "+Cleaner436+" "+Cleaner593);
                    }else if(clientCommand.equals("Cleaned593")){
                        Highlighter("593");
                        CST[room593ID].SendData("LockClean..");
                        Cleaner593="no";
                        CST[appID].SendData("CLEANTRUE "+Cleaner431+" "+Cleaner436+" "+Cleaner593);
                    }else if(clientCommand.equals("Cleaned436")){
                        System.out.println("...............CLEANED.........");
                        Highlighter("436");
                        CST[room436ID].SendData("LockClean..");
                        Cleaner436="no";
                        CST[appID].SendData("CLEANTRUE "+Cleaner431+" "+Cleaner436+" "+Cleaner593);
                    }
                    /***************************************************************************************************
                     *Room Status
                     ***************************************************************************************************/
                    if(room431ID==-1) {
                        status431.setFont(new Font("Calibri", Font.PLAIN, 14));
                        status431.setText("Room 431 Status - Offline");
                    }else if(room431ID!=-1) {
                        status431.setFont(new Font("Calibri", Font.ITALIC, 14));
                        status431.setText("Room 431 Status - Online");
                    }
                    if(room436ID==-1) {
                        status436.setFont(new Font("Calibri", Font.PLAIN, 14));
                        status436.setText("Room 436 Status - Offline");
                    }else if(room436ID!=-1) {
                        status436.setFont(new Font("Calibri", Font.ITALIC,14));
                        status436.setText("Room 436 Status - Online");
                    }
                    if(room593ID==-1) {
                        status593.setFont(new Font("Calibri", Font.PLAIN, 14));
                        status593.setText("Room 593 Status - Offline");
                    }else if(room593ID!=-1) {
                        status593.setFont(new Font("Calibri", Font.ITALIC, 14));
                        status593.setText("Room 593 Status - Online");
                    }

                    /***************************************************************************************************
                     *Cleaner unlock from app
                     ***************************************************************************************************/
                    if(clientCommand.equals("CleanUnlock431")){
                        CST[room431ID].SendData("apptrue");
                        Txt1.append("App Master 431\t"+date_time+"\n\n");
                        //Database("App-Master 431",date_time);
                    }else if(clientCommand.equals("CleanUnlock436")){
                        CST[room436ID].SendData("apptrue");
                        Txt1.append("App Master 436\t"+date_time+"\n\n");
                        //Database("App-Master 436",date_time);
                    }else if(clientCommand.equals("CleanUnlock593")){
                        CST[room593ID].SendData("apptrue");
                        Txt1.append("App Master 593\t"+date_time+"\n\n");
                        //Database("App-Master 593",date_time);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*****************************************************************************************************
         *Sends a String of data to the guests Bedroom(K64F) or the Android App
         ****************************************************************************************************/
        private void SendData(String s){
            try {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                System.out.print("\nSend data: "+ s +"\n");
                out.println(s);
                out.flush();
            }catch(IOException io){

            }
        }
        /*****************************************************************************************************
         *Sends all key scans to be saved in a mySQL database
         ****************************************************************************************************/
        private void Database(String card,String DT){
            try {
                String myDriver = "com.mysql.jdbc.Driver";
                Class.forName(myDriver);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                String query = " insert into hotel (card_scanned, date_time)" + " values (?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1,card );
                preparedStmt.setString (2, DT);
                preparedStmt.execute();
                conn.close();
            }catch(Exception io){

            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    /*****************************************************************************************************
     *Sends and email if the forgot password button is pressed
     ****************************************************************************************************/
    private void SendMail() throws javax.mail.MessagingException {
        final String username = "";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Random r = new Random();
            int newpassword= r.nextInt((5000 - 1000) + 1) + 1000;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("automatedhotelsystem@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("eoinstankardcollege@gmail.com"));
            message.setSubject("New Password");
            message.setText("Dear Eoin," + "\n\nYour new Password is: "+newpassword+"\n\nThank You\nRegards\nAutomated Hotel System");

            Transport.send(message);
            CleanerApp =Integer.toString(newpassword);
            System.out.println("Done"+CleanerApp);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    /*****************************************************************************************************
     *Updates the database when the guest is checked in
     ****************************************************************************************************/
    private void DatabaseCheckIn(String room,String DT){
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

            String query = " insert into checkinout (room, checkin,checkout)" + " values (?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1,room);
            preparedStmt.setString (2, DT);
            preparedStmt.setString (3, "Pending..");
            preparedStmt.execute();
            conn.close();

        }catch(Exception io){

        }
    }

    /*****************************************************************************************************
     *Updates the database when the guest is checked out
     ****************************************************************************************************/
    private void DatabaseCheckOut(String room,String DT){
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

            String query = "update checkinout set checkout = ? where room = ? and checkout = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1,DT);
            preparedStmt.setString (2,room);
            preparedStmt.setString (3,"Pending..");
            preparedStmt.execute();
            conn.close();
        }catch(Exception io){

        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void Highlighter(String room){
        try {
            System.out.println("................HIGHLIGHTER.........");
            int lines  =  P2_Txt1.getLineCount();
            for(int i = 0; i <= lines; i ++) {
                System.out.println("................LINES:  "+i);
                Highlighter.HighlightPainter painter;
                int startIndex = P2_Txt1.getLineStartOffset(i);
                int endIndex = P2_Txt1.getLineEndOffset(i);
                String restString = P2_Txt1.getText().substring(startIndex, endIndex);
                if(restString.contains(room)) {
                    System.out.println("im in");
                    painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                    P2_Txt1.getHighlighter().addHighlight(startIndex, endIndex, painter);
                }


            }


        }catch(Exception io){

        }
    }
}