package com.example.swara.finder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserCustomAdapter extends ArrayAdapter<User> {


    Context context;
    int layoutResourceId;
    ArrayList<User> data = new ArrayList<User>();
    private static String TextFiled = null;



   public UserCustomAdapter(Context context, int layoutResourceId,
                             ArrayList<User> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.textName = (TextView) row.findViewById(R.id.textView1);
            holder.textAddress = (TextView) row.findViewById(R.id.textView2);
            holder.textLocation = (TextView) row.findViewById(R.id.textView3);
            holder.btnEdit = (Button) row.findViewById(R.id.button1);
            holder.btnDelete = (Button) row.findViewById(R.id.button2);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();

        }

        User user = data.get(position);
        holder.textName.setText(user.getName());
        holder.textAddress.setText(user.getAddress());
        holder.textLocation.setText(user.getLocation());
        holder.btnEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Edit Button Clicked", "**********");
                Toast.makeText(context, "Edit button Clicked",
                        Toast.LENGTH_LONG).show();


                RelativeLayout rl = (RelativeLayout)v.getParent();
                TextView tv = (TextView)rl.findViewById(R.id.textView1);
                TextFiled = tv.getText().toString();
                myStaticMembers.buttonPressedWas = "Edit";//fourth
                //Log.i("Check This: ",""+text);
                Intent intent = new Intent(v.getContext(),InfoActivity.class);
               // intent.putExtra("Edit",editText);
                context.startActivity(intent);

            }
        });
        holder.btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {//fifth
                // TODO Auto-generated method stub
                myStaticMembers.buttonPressedWas = "Delete";
                RelativeLayout rl = (RelativeLayout)v.getParent();
                TextView tv = (TextView)rl.findViewById(R.id.textView1);
                TextFiled = tv.getText().toString();
                Log.i("DeleteField ",TextFiled);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Confirm Delete")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    new PostAsync("deleteData").execute().get();
                                    data.remove(position);
                                    notifyDataSetChanged();
                                    /*UserCustomAdapter userAdapter = new UserCustomAdapter(UserCustomAdapter.this, R.layout.row,
                                            PostAsync.getDescriptionArrayList());
                                    userAdapter.notifyDataSetChanged();*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Alert", "Delete Cancelled");
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                Log.i("Delete Button Clicked", "**********");
                Toast.makeText(context, "Delete button Clicked",
                        Toast.LENGTH_LONG).show();
            }
        });
        return row;

    }

    static class UserHolder {
        TextView textName;
        TextView textAddress;
        TextView textLocation;
        Button btnEdit;
        Button btnDelete;
    }
    public static void setEditTextField(String inputText){
        //Log.i("checking-setEdit: ","Reached");
        if(!inputText.isEmpty()){
            Log.i("now",inputText);
            TextFiled = inputText;
        }
        else {
            TextFiled = null;
        }
    }
    public static String getEditTextFiled(){
        return TextFiled;
    }
}


