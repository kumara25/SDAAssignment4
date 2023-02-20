package com.example.sdaassign4_2022;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {


    EditText mUserName;
    EditText mBorrowerID;
    EditText mEmail;
    Button mButtonSave;
    Button mButtonReset;

    /**
     * settings
     */
    public Settings() {

    }

    /**
     * create view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mUserName = (EditText)root.findViewById(R.id.userName);
        mBorrowerID = (EditText)root.findViewById(R.id.borrowerID);
        mEmail = (EditText)root.findViewById(R.id.email);
        mButtonSave=(Button) root.findViewById(R.id.buttonSave);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClicked();
            }
        });
        mButtonReset=(Button) root.findViewById(R.id.buttonReset);
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });
        getDataFromPrefs();
        return  root;
    }

    /**
     * save clicked
     */
    private void onSaveClicked() {
        if(TextUtils. isEmpty(mUserName.getText()) && TextUtils. isEmpty(mEmail.getText()) &&TextUtils. isEmpty( mBorrowerID.getText()))
        {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.please_enter_details), Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils. isEmpty(mUserName.getText()))
        {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.please_enter_user_name), Toast.LENGTH_SHORT).show();
        }
        else if(!isValidEmail(mEmail.getText()))
        {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.please_enter_valid_email_id), Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils. isEmpty(mBorrowerID.getText()))
        {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.please_enter_borrower_id), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this.getActivity(), getResources().getString(R.string.successfully_updated_details), Toast.LENGTH_SHORT).show();
            saveDataInPrefs();
        }
    }

    /**
     * is valid email
     * @param target
     * @return
     */
    public  boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * save data in prefs
     */
    void saveDataInPrefs() {
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(Constants.ApplicationSP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.UserName, mUserName.getText().toString());
        editor.putString(Constants.Emailid, mEmail.getText().toString());
        editor.putString(Constants.BorrowerId, mBorrowerID.getText().toString());
        editor.apply();
    }

    /**
     * get data from perf
     */
    void getDataFromPrefs(){
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(Constants.ApplicationSP, Context.MODE_PRIVATE);

        mUserName.setText(sharedPref.getString(Constants.UserName,""));
            mEmail.setText(sharedPref.getString(Constants.Emailid,""));
            mBorrowerID.setText(sharedPref.getString(Constants.BorrowerId,""));
    }

    /**
     * reset data
     */
    void resetData()
    {
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(Constants.ApplicationSP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Constants.UserName);
        editor.remove(Constants.Emailid);
        editor.remove(Constants.BorrowerId);
        mUserName.setText("");
        mEmail.setText("");
        mBorrowerID.setText("");
        editor.apply();
    }
}
