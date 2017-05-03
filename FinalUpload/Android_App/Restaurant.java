package com.example.eoin.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;

public class Restaurant extends AppCompatActivity {
    Spinner spinner,spinner1,spinner2;
    Button confirm,back;
    TextView num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        spinner1 = (Spinner)findViewById(R.id.spinner5);
        spinner2 = (Spinner)findViewById(R.id.spinner6);
        confirm = (Button)findViewById(R.id.button10);
        back = (Button)findViewById(R.id.button11);
        num = (TextView)findViewById(R.id.textView16);

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        int RoomNo = myBundle.getInt("RoomNo: ");
        num.setText(""+RoomNo);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.times, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.table, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner1.getSelectedItem().equals("---LUNCH---")
                        || spinner1.getSelectedItem().equals("---DINNER---")
                        || spinner1.getSelectedItem().equals("CHOOSE TIME")
                        || spinner2.getSelectedItem().equals("CHOOSE TABLE SIZE")){
                    //DO NOTHING
                    Toast.makeText(Restaurant.this,"PLEASE SELECT TIME & TABLE SIZE", Toast.LENGTH_SHORT).show();

                }else{
                    String Time = spinner1.getSelectedItem().toString();
                    String table = spinner2.getSelectedItem().toString();
                    String Room = num.getText().toString();
                    //Toast.makeText(Restaurant.this,Time + "  "+table, Toast.LENGTH_SHORT).show();
                    String Reserve = "Restaurant "+Room+" "+Time+" "+table;

                    Intent returnIntent = getIntent();
                    Bundle myBundle =  returnIntent.getExtras();
                    myBundle.putString("reserve",Reserve);
                    returnIntent.putExtras(myBundle);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
