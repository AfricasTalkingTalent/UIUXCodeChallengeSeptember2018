package com.example.ian.sambazaquick;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

//For our UI widgets
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

//For our firebase authentication
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    //For our firebase authentication
    private FirebaseAuth mAuth;

    //For our UI widgets
    private EditText mEditEmail, mEditPass, mConfirmPass;
    private Button mBtnSignUp;
    private ImageButton mBtnBack;
    private ProgressDialog progressDialog;

    //Overriding onCreate to instantiate mAuth
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //mAuth = FirebaseAuth.getInstance();
    }

    //now, override onCreateView to load the UI and wire it up
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //remove the previous views. Sometimes necessary
        container.removeAllViews();

        //load our view
        View view = inflater.inflate(R.layout.sign_up_fragment,container,false);

        //get our UI references
        mEditEmail = view.findViewById(R.id.editEmail);
        mEditPass = view.findViewById(R.id.editPass);
        mConfirmPass = view.findViewById(R.id.confirmPass);
        mBtnSignUp = view.findViewById(R.id.btnSignUp);
        mBtnBack = view.findViewById(R.id.back);
        progressDialog = new ProgressDialog(getContext());

        //Wire our click event listener for the sign up button
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //attempt to sign up the user
                signUpNewUser();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //load the log in fragment
                getFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new LogInFragment()).commit();
            }
        });

        return view;
    }

    //Method to sign up our new user
    private void signUpNewUser(){

        //confirm that none of the fields are empty
        if (TextUtils.isEmpty(mEditEmail.getText().toString()) || TextUtils.isEmpty(mEditPass.getText()) || TextUtils.isEmpty(mConfirmPass.getText().toString())){

            //Show a message based on which one exactly is empty
            if (TextUtils.isEmpty(mEditEmail.getText().toString())){
                Toast.makeText(getContext(),"Email field cannot be empty",Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(mEditPass.getText().toString())){
                Toast.makeText(getContext(),"Password field cannot be empty",Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(mConfirmPass.getText().toString())){
                Toast.makeText(getContext(),"Please confirm your password",Toast.LENGTH_LONG).show();
            }
        } else if (!(mEditPass.getText().toString()).equals(mConfirmPass.getText().toString())){ //A test to ensure the password fields match

            Toast.makeText(getContext(),"Your passwords don't match",Toast.LENGTH_LONG).show();
        } else{ //No empty field, passwords match

            String email = mEditEmail.getText().toString();
            String pass = mEditPass.getText().toString();
            progressDialog.setMessage("Signing you up");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(  new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()){

                        //get the current user
                        mAuth.getCurrentUser();
                        progressDialog.dismiss();

                        //load the home screen fragment
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_top,R.anim.top_bottom).replace(R.id.fragmentHolder,new HomeScreenFragment()).commit();
                    }else{

                        //use toast to show error in signing up user

                        Toast.makeText(getContext(),"Failed to sign up. Make sure you have a good internet connection and your " +
                                "password is at least 8 characters long" + task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}
