package com.example.swara.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class CategorySelection extends AppCompatActivity {

    public static String selectedCategory = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button NextButtonInCategory = (Button)findViewById(R.id.NextButtonInCategory);
        NextButtonInCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),InfoActivity.class);
                int counter=0;
               if(((CheckBox) findViewById(R.id.checkBox)).isChecked()){
                    final CheckBox deliveryCheckBox = (CheckBox)findViewById(R.id.checkBox);
                    selectedCategory = deliveryCheckBox.getText().toString();
                    counter++;

                }
               if(((CheckBox) findViewById(R.id.checkBox2)).isChecked()){
                    final CheckBox maintenanceCheckBox = (CheckBox)findViewById(R.id.checkBox2);
                    selectedCategory = maintenanceCheckBox.getText().toString();
                    counter++;
                }
                if(counter==1){
                    Log.i("Selection", "CORRECT SELECTION");
                    startActivityForResult(intent, 0);
                }
                else{
                    Toast.makeText(getApplication().getApplicationContext(), "Please select only 1 option", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public static String getSelectedCategory(){
        return selectedCategory;
    }

}
