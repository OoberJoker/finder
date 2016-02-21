package com.example.swara.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/*
MainActivity
LatLongTest
PageSelection
CategorySelection
PostedServicesList
InfoActivity
MapsActivity2

 */

public class InfoActivity extends AppCompatActivity {

    private static String enteredUserInfo;

    private void setUserServiceInfo(String info){
        enteredUserInfo = info;
        Log.i("enteredUserInfo: ",enteredUserInfo);
    }
    public static String getUserServiceInfo(){
//        Log.i("getUserServiceInfo-> ",enteredUserInfo);
        return enteredUserInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle extras = getIntent().getExtras();
        ArrayList<String> category = new ArrayList<>();


        if(extras!=null){
            if(extras.getString("Delivery") != null) {
                category.add(extras.getString("Delivery"));
                Log.i("Delivery: ", category.get(0));
            }
            if(extras.getString("Maintenance") != null) {
                category.add(extras.getString("Maintenance"));
                Log.i("Maintenance: ", category.get(1));
            }


        }



        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), LatLongTest.class);
                final EditText infoText = (EditText) findViewById(R.id.editText);
                String userInput = infoText.getText().toString();
                setUserServiceInfo(userInput);
                try {
                    Log.i("WhatareYou-orig:",""+UserCustomAdapter.getEditTextFiled());
                    if(myStaticMembers.buttonPressedWas.equals("Add")) {//second
                        Log.i("WhatareYou:","insert data");
                        new PostAsync("insertData").execute();
                    }
                    else if (myStaticMembers.buttonPressedWas.equals("Edit")){
                        Log.i("WhatareYou:","edit data");
                        new PostAsync("editData").execute();
                    }


                }
                catch(Exception e){
                    e.printStackTrace();
                }
                Intent intentToStartMapsActivity = new Intent(v.getContext(),PostedServicesList.class);
                startActivityForResult(intentToStartMapsActivity, 0);//changed 2nd!!!!

                //  startActivityForResult(intent, 0);
              //  Log.i("testing->", "reached");
            }
        });
    }


}
