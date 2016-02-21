package com.example.swara.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PageSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_selection);

        Button checkPostingButton = (Button)findViewById(R.id.checkPostingButton);
        checkPostingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStaticMembers.buttonPressedWas = "CheckPosting";
                Intent intent = new Intent(v.getContext(), PostedServicesList.class);
                startActivityForResult(intent, 0);
        }
        });

        Button postButton = (Button)findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MainActivity.buttonPressedWas = "Add"; //first
                myStaticMembers.buttonPressedWas = "Add";
                Intent intent = new Intent(v.getContext(), CategorySelection.class);
                startActivityForResult(intent, 0);
            }
        });


    }




}
