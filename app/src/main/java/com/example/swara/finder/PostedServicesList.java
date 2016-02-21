package com.example.swara.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostedServicesList extends AppCompatActivity {


    ListView userList;
    public  UserCustomAdapter userAdapter;
    public  ArrayList<User> userArray = new ArrayList<User>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_services_list);


        /**
         * add item in arraylist
         */
        try {
            new PostAsync("getData").execute().get();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Log.i("UserArraySizePosted: ", "" + PostAsync.getDescriptionArrayList().size());


        for(int i=0;i<PostAsync.getDescriptionArrayList().size();i++){
            userArray.add(new User(PostAsync.getDescriptionArrayList().get(i), "Spain", "Spain"));
        }

        userAdapter = new UserCustomAdapter(PostedServicesList.this, R.layout.row,
                userArray);
        userList = (ListView) findViewById(R.id.listView);
        userList.setItemsCanFocus(false);
        userList.setAdapter(userAdapter);
        /**
         * get on item click listener
         */
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(PostedServicesList.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });






        ImageButton imageButtonAdd = (ImageButton)findViewById(R.id.imageButtonAdd);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStaticMembers.buttonPressedWas = "Add";//third
                Intent intent = new Intent(v.getContext(), CategorySelection.class);
                startActivityForResult(intent, 0);
                Log.i("testing->", "reached");
            }
        });



        ImageButton imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (myStaticMembers.buttonPressedWas){
                    case "CheckPosting":
                        intent = new Intent(v.getContext(), PageSelection.class);
                        startActivityForResult(intent, 0);
                        break;
                    case "Add":
                        intent = new Intent(v.getContext(), CategorySelection.class);
                        startActivityForResult(intent, 0);
                        break;
                    case "Edit":
                        intent = new Intent(v.getContext(), InfoActivity.class);
                        startActivityForResult(intent, 0);
                        Log.i("EditField", UserCustomAdapter.getEditTextFiled());
                        break;
                    default: Log.i("Default Switch Case->", "reached");

                }

            }
        });


    }




}
