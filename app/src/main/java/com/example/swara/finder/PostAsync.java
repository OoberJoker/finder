package com.example.swara.finder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
//what would you like to do? Check your listings? Post a service?
public class PostAsync extends AsyncTask<String, String, JSONObject> {
    JSONParser jsonParser = new JSONParser();
    private String dataUrl=  new String();//"http://ec2-54-213-52-202.us-west-2.compute.amazonaws.com/finderQueries.php";
    private String editFieldText = new String();
    private String deleteField = new String();
    private String phpPage = new String();
    public PostAsync(String RetreiveOrInsert){
        phpPage = RetreiveOrInsert;
        if(RetreiveOrInsert.equals("getData")){
            Log.i("checkingReach", " Reached");
            dataUrl = "http://ec2-54-213-52-202.us-west-2.compute.amazonaws.com/finderQueries.php";
        }
        else if(RetreiveOrInsert.equals("insertData")){
            dataUrl ="http://ec2-54-213-52-202.us-west-2.compute.amazonaws.com/insertQueries.php";
        }
        else if (RetreiveOrInsert.equals("editData")){//this is edit data

            editFieldText = UserCustomAdapter.getEditTextFiled();
            dataUrl ="http://ec2-54-213-52-202.us-west-2.compute.amazonaws.com/editQueries.php";
        }
        else if (RetreiveOrInsert.equals("deleteData")){//this is edit data

            deleteField = UserCustomAdapter.getEditTextFiled();
            dataUrl ="http://ec2-54-213-52-202.us-west-2.compute.amazonaws.com/deleteQueries.php";
        }
    }
    private ProgressDialog pDialog;
    private static ArrayList<String>descriptionArrayList = new ArrayList<>();
    private Set<String> hashSet = new HashSet<>();

    private final String TAG_SUCCESS = "success";
    private final String TAG_MESSAGE = "message";


  /*  @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Attempting login...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }*/

    @Override
    protected JSONObject doInBackground(String... args) {
        HashMap<String, String> params = new HashMap<>();
        try {
            if(phpPage.equals("insertData") || phpPage.equals("editData")) {

                //  MainFragment.getProfileAttributed();


             //   double[] latitudeandlongitudeTest =latitudeLongitude.getLatitudeLongitude();
               /* if(latitudeandlongitudeTest.length>0) {
                    Log.i("locationObject:", "" + latitudeandlongitudeTest[0]);
                    Log.i("locationObject:", "" + latitudeandlongitudeTest[1]);
                }*/
                //LatLongTest latLongTest = new LatLongTest();
               /* double[] latitudeandlongitudeTest = latLongTest.getLatitudeLongitude();
                if(latitudeandlongitudeTest.length>0) {
                    Log.i("locationObject:", "" + latitudeandlongitudeTest[0]);
                    Log.i("locationObject:", "" + latitudeandlongitudeTest[1]);
                }*/

                params.put("id", MainFragment.getUserId());
                params.put("name", MainFragment.getUserName());
                params.put("description", InfoActivity.getUserServiceInfo());
                params.put("money_offered", "" + 100);
                params.put("type", CategorySelection.getSelectedCategory());
                double[] latitudeandlongitude = LatLongTest.getLatNlong();
                params.put("latitude", "" + latitudeandlongitude[0]);
                params.put("longitude", "" + latitudeandlongitude[1]);
                if (!editFieldText.isEmpty()) {
                    Log.i("editFieldText",editFieldText);
                    params.put("userEditString", "" + editFieldText);
                   /*
                   the  TextField in the UserCustomAdapter class needs to be set to the users new input so in case the user presses the
                   back button instead of the edit button on the screen, the same field will be edited.
                   */
                    UserCustomAdapter.setEditTextField(InfoActivity.getUserServiceInfo());

                }
                Log.d("request", "starting");
                Log.d("id-post", MainFragment.getUserId());
                Log.d("name-post", MainFragment.getUserName());
                Log.d("description-post", InfoActivity.getUserServiceInfo());
                Log.d("type", CategorySelection.getSelectedCategory());
                Log.d("latitude", "" + latitudeandlongitude[0]);
                Log.d("longitude", "" + latitudeandlongitude[1]);
                Log.d("userEditString", "" + editFieldText);
            }
            else{
                 if (!deleteField.isEmpty()) {
                              Log.i("deletein ", deleteField);
                              params.put("userDeleteField", "" + deleteField);
                 }
            }
            JSONObject jsonObj = new JSONObject();

            JSONObject json = jsonParser.makeHttpRequest(
                    dataUrl, "POST", params);
            Log.i("JSON:","Reached");
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json.toString());
                JSONArray jArray = jsonObject.getJSONArray("service");
                Log.i("jarray:",""+jArray);

                Log.i("UserArraySize 1: ",""+jArray.length());
               for(int i=0; i<jArray.length(); i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                 //  Log.i("String-1: ",""+descriptionArrayList.get(i));
                            hashSet.add(json_data.getString("description"));
                //   Log.i("String 2: ",""+json_data.getString("description"));


                    Log.i("This->", "" + json_data.getString("description"));

                }
                //well...this is a really cheap and dirty way to get around this problem. Take care of this in a more professional way later.
                descriptionArrayList.clear();
                descriptionArrayList.addAll(hashSet );

                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject json) {
        Log.i("onPost: ","Reached");
        int success = 0;
        String message = "";

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        if (json != null) {
           /* Toast.makeText(MainActivity.this, json.toString(),
                    Toast.LENGTH_LONG).show();
*/
            try {
                success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (success == 1) {
            Log.d("Success!!", message);
        }else{
            Log.d("Failure!!", message);
        }
    }

public static ArrayList<String>getDescriptionArrayList(){
    Collections.sort(descriptionArrayList);

    return descriptionArrayList;
}


}
