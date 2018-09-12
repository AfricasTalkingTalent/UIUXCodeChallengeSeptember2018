package com.example.ian.sambazaquick;

import android.os.Bundle;
import android.support.v4.app.Fragment;

//For firebase authentication, to log the user out
import com.google.firebase.auth.FirebaseAuth;

//For our UI
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeScreenFragment extends Fragment {

    //For our UI
    private Button mBtnAddNewContacts, mBtnSendAirtime, mBtnLogOut;

    //For firebase authentication
    private FirebaseAuth mAuth;

    //initialize mAuth in onCreate
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    //Override onCreateView and load UI, wire it up
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //remove previous views
        container.removeAllViews();

        //inflate UI
        View view = inflater.inflate(R.layout.mainpage_fragment,container,false);

        //get references to our widgets
        mBtnAddNewContacts = view.findViewById(R.id.btnAddNewContacts);
        mBtnSendAirtime = view.findViewById(R.id.btnSendAirtime);
        mBtnLogOut = view.findViewById(R.id.btnLogOut);

        //wire up click event listeners
        mBtnAddNewContacts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                //load the add new contacts fragment
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.fragmentHolder,new AddNewContactsFragment()).commit();
            }
        });

        mBtnSendAirtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //load the send airtime fragment
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.fragmentHolder,new SendAirtimeFragment()).commit();
            }
        });

        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //invoke method to logout user
                logoutUser();
            }
        });

        return view;
    }

    //method to log out user
    private void logoutUser(){

        mAuth.signOut();

        //load log in fragment
        getFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new LogInFragment()).commit();
    }
}
