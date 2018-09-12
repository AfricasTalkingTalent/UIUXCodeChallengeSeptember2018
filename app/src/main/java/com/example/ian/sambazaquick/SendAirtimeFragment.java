package com.example.ian.sambazaquick;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

//for our UI widgets
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//For our firebase database
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//For Africa's talking API integration
import org.json.*;

public class SendAirtimeFragment extends Fragment {

    //For our UI widgets
    private ImageButton mBtnBack;
    private EditText mEditPhoneNo;
    private EditText mEditAmnt;
    private TextView mShwName, mShwTotalAmnt;
    private ProgressDialog progressDialog;
    private ImageView mImgError;
    private Button mBtnAdd, mBtnSend;

    //For our UI works/magic
    private boolean clickOnce = true;

    //For storing details of who the airtime is being sent to
    private String phoneNo;
    private String amnt;

    //To store the total amnt of credit to be bought
    private int mTotalAmnt = 0;

    //Storing our contacts List
    private List<MyContacts> contactsList = new ArrayList<>();

    //My JSONArray for recepients
    JSONArray recepients = new JSONArray();

    //for firebase
    private DatabaseReference mDbRef;
    private ValueEventListener dbListener;


    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //instantiate our mDbRef
        mDbRef = FirebaseDatabase.getInstance().getReference();

        //instantiate our listener
        dbListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //call method to load our contacts list
                loadContactsList((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getActivity(),"Error loading your contacts. Please check your internet connection",Toast.LENGTH_LONG).show();
            }
        };

        //set up our listener
        mDbRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_contacts").addValueEventListener(dbListener);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        container.removeAllViews();

        //Inflate our UI and wire it up
        View view = inflater.inflate(R.layout.send_airtime_fragment,container,false);
        mBtnBack = view.findViewById(R.id.btnBack);
        mEditPhoneNo = view.findViewById(R.id.keyNumber);
        mEditAmnt = view.findViewById(R.id.keyAmount);
        mShwName =  view.findViewById(R.id.shwName);
        mShwTotalAmnt = view.findViewById(R.id.shwTotalAirtime);
        mImgError = view.findViewById(R.id.imgError);
        mBtnAdd = view.findViewById(R.id.btnAddAnother);
        mBtnSend = view.findViewById(R.id.btnSendAirtime);

        //set the visibility of amount and imgError to gone
        mEditAmnt.setVisibility(View.GONE);
        mImgError.setVisibility(View.GONE);

        //set the button add text to Next
        mBtnAdd.setText("Next");
        mBtnAdd.setBackgroundColor(Integer.valueOf(R.color.orangeShade));

        //Listener for clicks on btnAdd
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyContacts c = null;

                if (clickOnce){

                    boolean found = false;
                    for (int i = 0; i < contactsList.size();i++){
                        c = contactsList.get(i);

                        if (c.getPhoneNo() == mEditPhoneNo.getText().toString()){
                            //This contact exists in the user's contact list
                            //Load the number in our string
                            phoneNo = mEditPhoneNo.getText().toString();

                            //show the amounts widget and change the next button to add new contact
                            mEditAmnt.setVisibility(View.VISIBLE);
                            mBtnAdd.setBackgroundResource(R.drawable.add_another_contact_btn_bg);
                            mBtnAdd.setBackgroundColor(Integer.valueOf(R.color.white));

                            clickOnce = !clickOnce;
                            found = !found;

                            break;
                        }
                    }

                    if (found){
                        //Show toast with error
                        Toast.makeText(getActivity(),"This number doesn't exist in your contacts",Toast.LENGTH_LONG).show();
                    }
                } else{ //dealing with amount here,

                        if (TextUtils.isEmpty(mEditAmnt.getText().toString())){
                            Toast.makeText(getActivity(),"Please key in the amount", Toast.LENGTH_LONG).show();
                        } else if (mEditAmnt.getText().toString().contains(" ")) {

                            //Do not allow for spaces
                            Toast.makeText(getContext(),"Leave no space in your text",Toast.LENGTH_LONG).show();
                        } else {

                            //amount has been keyed in
                            amnt = mEditAmnt.getText().toString();

                            //update the amount variable
                            mTotalAmnt += Integer.valueOf(amnt);

                            //feed this data to our JSONObjectArray
                            try {
                                recepients.put(new JSONObject().put("phoneNumber", phoneNo).put("amount", "KES " + amnt));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //change the clickOnce
                            clickOnce = !clickOnce;

                            //Update the textviews
                            mShwName.setText(mShwName.getText().toString() + c.getName() + ",");
                            mShwTotalAmnt.setText(String.valueOf(mTotalAmnt));

                            //set the button add text to Next
                            mBtnAdd.setText("Next");
                            mBtnAdd.setBackgroundColor(Integer.valueOf(R.color.orangeShade));
                            //set the visibility of amount and imgError to gone
                            mEditAmnt.setVisibility(View.GONE);

                            //Handle click on send
                        }
                }
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentHolder, new HomeScreenFragment()).commit();
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ensure our JSONarray is not empty
                if (recepients == null){
                    Toast.makeText(getContext(),"Please provide recepients and an amount",Toast.LENGTH_LONG).show();
                }else{

                    //invoke sendAirtimeMethod
                    sendAirtime();
                }

            }
        });

        return view;
    }

    private void loadContactsList(Map<String, Object> dbData){

        //Start our progress dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Retrieving your contacts list");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //create an instance of our node
        MyContacts myContacts = null;

        if (dbData == null){
            Toast.makeText(getContext(),"No contacts to load",Toast.LENGTH_LONG).show();
        } else {

            Map contact = null;
            //gather all contacts data
            for (Map.Entry<String, Object> entry: dbData.entrySet()){

                myContacts = new MyContacts();

                contact = (Map) entry.getValue();

                //Get the necessary data
                myContacts.setName((String) contact.get("Name"));
                myContacts.setPhoneNo((String) contact.get("Phone Number"));

                //push to the array list
                contactsList.add(myContacts);

            }
        }

        //Remove listener. Thus this will only fire once
        mDbRef.removeEventListener(dbListener);
        progressDialog.dismiss();

    }

    private void sendAirtime(){

        String username = "sandbox";
        String apiKey = "8d98d8c1b6efbcb51daa68a3dc93756b3ad3b7eddc68fd28668beb474a502ffd";

        String recipientStringFormat = recepients.toString();

        //Create an instance of GateWayClass
        AfricasTalkingGateway gateway = new AfricasTalkingGateway(username,apiKey,"sandbox");

        try {
            JSONArray results = gateway.sendAirtime(recipientStringFormat);
            int length = results.length();
            for(int i = 0; i < length; i++) {
                JSONObject result = results.getJSONObject(i);
                Log.e("Error",result.getString("status"));
                Log.e("Error",result.getString("amount"));
                Log.e("Error",result.getString("phoneNumber"));
                System.out.println(result.getString("discount"));
                System.out.println(result.getString("requestId"));
                //Error message is important when the status is not Success
                System.out.println(result.getString("errorMessage"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //an inner class to act as our node for contact list
    private class MyContacts{

        private String name;
        private String phoneNo;

        protected void setName(String name){
            this.name = name;
        }
        protected String getName(){
            return name;
        }

        protected void setPhoneNo(String phoneNo){
            this.phoneNo = phoneNo;
        }

        protected String getPhoneNo(){
            return phoneNo;
        }
    }
}
