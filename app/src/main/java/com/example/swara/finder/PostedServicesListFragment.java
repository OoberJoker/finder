package com.example.swara.finder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostedServicesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostedServicesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostedServicesListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView userList;
    public  UserCustomAdapter userAdapter;
    public ArrayList<User> userArray = new ArrayList<User>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PostedServicesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostedServicesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostedServicesListFragment newInstance(String param1, String param2) {
        PostedServicesListFragment fragment = new PostedServicesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posted_services_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
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
//PostedServicesListFragment.this
        userAdapter = new UserCustomAdapter(getContext().getApplicationContext(), R.layout.row,
                userArray);
        userList = (ListView) getView().findViewById(R.id.listView);
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
                Toast.makeText(getContext().getApplicationContext(),
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });






        ImageButton imageButtonAdd = (ImageButton)getView().findViewById(R.id.imageButtonAdd);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStaticMembers.buttonPressedWas = "Add";//third
                Intent intent = new Intent(v.getContext(), CategorySelection.class);
                startActivityForResult(intent, 0);
                Log.i("testing->", "reached");
            }
        });



        ImageButton imageButtonBack = (ImageButton)getView().findViewById(R.id.imageButtonBack);
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



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
