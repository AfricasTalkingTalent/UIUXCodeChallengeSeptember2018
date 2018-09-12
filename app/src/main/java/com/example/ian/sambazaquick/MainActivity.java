package com.example.ian.sambazaquick;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load the log in fragment
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_bottom,R.anim.bottom_top).replace(R.id.fragmentHolder,new LogInFragment()).commit();

    }
}
