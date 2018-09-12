package com.example.ian.sambazaquick;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;

//For our UI widgets
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

//For our firebase authentication
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment {

    //For our firebase authentication
    private FirebaseAuth mAuth;

    //For our UI widgets
    private EditText mEditEmail, mEditPass;
    private Button mBtnLogIn, mBtnSignUp;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiate FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //go direct to homepage if we have a current user logged in
        if (mAuth.getCurrentUser() != null) {

            //load the home screen fragment
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_top, R.anim.top_bottom).replace(R.id.fragmentHolder, new HomeScreenFragment()).commit();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //remove previous views
        container.removeAllViews();

        //inflate our UI
        View view = inflater.inflate(R.layout.log_in_fragment, container,false);
        //instantiate our UI widgets
        mEditEmail = view.findViewById(R.id.editEmail);
        mEditPass = view.findViewById(R.id.editPass);
        mBtnLogIn = view.findViewById(R.id.loginBtn);
        mBtnSignUp = view.findViewById(R.id.signupBtn);
        progressDialog = new ProgressDialog(getContext());

        //Set up click event listeners for log in button and sign up button
        mBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //attempt logging in the user
                logInUser();
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //load the sign up fragment
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_left, R.anim.expand_left_right).replace(R.id.fragmentHolder, new SignUpFragment()).commit();
            }
        });

        return view;
    }

    //Function to log in user
    private void logInUser() {

        //get the email and password put in
        String email = mEditEmail.getText().toString();
        String pass = mEditPass.getText().toString();

        //Ensure no string is empty
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

            if (TextUtils.isEmpty(email)) {

                //Show a toast warning that email cannot be empty
                Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_LONG).show();
            }

            if (TextUtils.isEmpty(pass)) {

                //Show a toast that password cannot be empty
                Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();
            }

        } else {

            //attempt to log in the user
            progressDialog.setMessage("Please wait as we log you in");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //dismiss progress dialog
                        progressDialog.dismiss();

                        //load the homescreen fragment
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_top, R.anim.top_bottom).replace(R.id.fragmentHolder, new HomeScreenFragment()).commit();

                    } else {

                        //dismiss progress dialog
                        progressDialog.dismiss();

                        //Show a toast indicating failure
                        Toast.makeText(getActivity(), "Failed to log you in. Ensure you have a good network connection and your details are okay", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
