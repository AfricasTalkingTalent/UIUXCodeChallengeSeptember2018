package com.example.ian.sambazaquick;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

//for firebase database access
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

//for firebase authentication to get
import com.google.firebase.auth.FirebaseAuth;

//for our UI widgets
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddNewContactsFragment extends Fragment {

    //for our db references
    private DatabaseReference mDbRef;

    //for getting our current user
    private FirebaseAuth mAuth;

    //for our UI widgets
    private ImageButton mBtnBck;
    private EditText mEditName, mEditPhone;
    private Button mBtnAdd;
    private TextView mTxtShwName, mTxtShwNo;
    private ProgressDialog progressDialog;

    //Override onCreate and initialize our DatabaseReference here
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mDbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    //Now, to load our UI and wire it up
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //remove previous views
        container.removeAllViews();

        //load our UI
        View view = inflater.inflate(R.layout.add_contacts_fragment,container,false);

        //get widget references
        mBtnBck = view.findViewById(R.id.btnBack);
        mEditName = view.findViewById(R.id.editName);
        mEditPhone = view.findViewById(R.id.editPhone);
        mBtnAdd = view.findViewById(R.id.btnAdd);
        mTxtShwName = view.findViewById(R.id.shwName);
        mTxtShwNo = view.findViewById(R.id.shwPhoneNo);

        progressDialog = new ProgressDialog(getContext());

        //Wire up click event listeners
        mBtnBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //load the main screen fragment
                getFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new HomeScreenFragment()).commit();
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //attempt to save the data
                saveContact();
            }
        });

        return view;
    }

    //method to save our contacts on the online db
    public void saveContact(){

        //make sure the form has no blanks
        if (TextUtils.isEmpty(mEditName.getText().toString()) || TextUtils.isEmpty(mEditPhone.getText().toString())){

            //Show error message based on exact error
            if (TextUtils.isEmpty(mEditName.getText().toString())){
                Toast.makeText(getContext(),"Please key in a name",Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(mEditPhone.getText().toString())){
                Toast.makeText(getContext(),"Please key in the phone number",Toast.LENGTH_LONG).show();
            }
        }else{ //data set is okay

            //show saving progress dialog
            progressDialog.setMessage("Saving contact...");
            progressDialog.setCanceledOnTouchOutside(false);

            //create a hash to save this data
            HashMap<String, String> contactData = new HashMap<>();
            contactData.put("Name",mEditName.getText().toString());
            contactData.put("Phone Number", mEditPhone.getText().toString());

            //Now send it to FirebaseDB
            //Interesting thing learnt. Putting / jumps us to a new child.
            //To use authentication, make sure you've enabled identity toolkit in Google APIs console
            mDbRef.child(mAuth.getCurrentUser().getUid() + "_contacts").child("save_" + new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()))
                    .setValue(contactData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                        //dismiss dialog
                        progressDialog.dismiss();

                        //show success toast
                        Toast.makeText(getContext(),"Contact added successfully",Toast.LENGTH_LONG).show();

                        //update the text view widgets
                        mTxtShwName.setText(mEditName.getText().toString());
                        mTxtShwNo.setText(mEditPhone.getText().toString());

                        //clear the edit text widgets
                        mEditName.setText("");
                        mEditPhone.setText("");

                    } else{

                        //dismiss dialog
                        progressDialog.dismiss();

                        //show error message
                        Toast.makeText(getContext(),"Failed to add contact. Please check your internet connection",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
