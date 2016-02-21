package com.example.swara.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    private TextView mTextDetails;
    private CallbackManager mCallbackManger;
    public static String userId = new String();
    public static String userName = new String();
   /* private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;*/
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            //startActivity(new Intent(getActivity().getApplicationContext(), InfoActivity.class));
            if(profile!=null){
                Log.i("Welcome!!!!!!!!!!!!!->", profile.getName());
                Log.i("Welcome!!!!!!!!!!!!!->", profile.getId());
                userId = profile.getId();
                userName = profile.getName();
                startActivity(new Intent(getActivity().getApplicationContext(),LatLongTest.class));//changed 3rd!!!!
                 // startActivity(new Intent(getActivity().getApplicationContext(),PageSelection.class));
               // startActivity(new Intent(getActivity().getApplicationContext(),PostedServicesList.class));
              //  startActivity(new Intent(getActivity().getApplicationContext(),CategorySelection.class));
            }

           // displayWelcomeMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };
    public static String getUserId(){
        return userId;
    }
    public static String getUserName(){
        return userName;
    }


    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManger = CallbackManager.Factory.create();


      /*  AccessTokenTracker tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
      mTokenTracker.startTracking();
      mProfileTracker.startTracking();*/
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


  /*  public void displayWelcomeMessage(Profile profile){
        if(profile!=null){
            mTextDetails.setText("Welcome"+profile.getName());
        }
    }*/

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        LoginButton loginButton = (LoginButton)view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManger, mCallback);
    }


    @Override
    public void onResume(){
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
      //  displayWelcomeMessage(profile);
    }

   /* @Override
    public void onStop(){
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        mCallbackManger.onActivityResult(requestCode,resultCode,data);
    }

}
