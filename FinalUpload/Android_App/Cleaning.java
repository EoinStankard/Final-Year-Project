package com.example.eoin.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Cleaning extends AppCompatActivity {
    Spinner spinner,spinner2,spinner3;
    Button butConfirm,butBack;
    TextView roomnum,tv;
    String towel,pillow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        butConfirm = (Button)findViewById(R.id.button2);
        butBack = (Button)findViewById(R.id.button3);
        roomnum=(TextView)findViewById(R.id.textView8);
        tv = (TextView)findViewById(R.id.textView3);


        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        int RoomNum = myBundle.getInt("RoomNum: ");
        roomnum.setText(""+RoomNum);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.pillow_arrays, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.towel_arrays, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter2);

        butConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedItem().equals("CHOOSE A TIME")) {
                    Toast.makeText(Cleaning.this,"Please Select Cleaning Time",
                            Toast.LENGTH_SHORT).show();
                }else {
                    String time = String.valueOf(spinner.getSelectedItem());

                    if(String.valueOf(spinner3.getSelectedItem()).equals("TOWELS")){
                         towel = "N/A           ";
                    }else{
                         towel =String.valueOf(spinner3.getSelectedItem());
                    }

                    if(String.valueOf(spinner2.getSelectedItem()).equals("PILLOWS")){
                         pillow = "N/A           ";
                    }else{
                         pillow =String.valueOf(spinner2.getSelectedItem());
                    }

                    Intent returnIntent = getIntent();
                    Bundle myBundle =  returnIntent.getExtras();
                    myBundle.putString("time",time);
                    myBundle.putString("towel",towel);
                    myBundle.putString("pillow",pillow);
                    returnIntent.putExtras(myBundle);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
