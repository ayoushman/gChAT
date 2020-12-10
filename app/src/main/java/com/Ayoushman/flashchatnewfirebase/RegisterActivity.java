package com.Ayoushman.flashchatnewfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    private EditText mUsernameView ;
    private EditText mEmailView ;
    private EditText mPasswordView ;
    private EditText mConfirmPasswordView ;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (EditText) findViewById(R.id.register_Email);
        mPasswordView = (EditText) findViewById(R.id.register_Password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_ConfirmPassword);
        mUsernameView = (EditText) findViewById(R.id.register_Username);

        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            CreateNewFirebaseUser();

        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassword = mConfirmPasswordView.getText().toString() ;
        return confirmPassword.equals(password) && password.length() > 8 ;
    }


    private void CreateNewFirebaseUser(){
String email = mEmailView.getText().toString() ;
String password = mPasswordView.getText().toString();
mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Log.d("GcHAT","CreateUser On Complete "+ task.isSuccessful());
        if(!task.isSuccessful()){
            Log.d("GcHAT","Create User Failed!!");
            showErrorDialog("Registration Attempt Failed");
        }
        else
        {
            saveDisplayName();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            finish();
            startActivity(intent);
        }

    }
});
    }


    // TODO: Save the display name to Shared Preferences

    private  void saveDisplayName(){
        String displayName = mUsernameView.getText().toString();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY,displayName).apply();
    }


    // TODO: Create an alert dialog to show in case registration failed

private void showErrorDialog(String message){

        new AlertDialog.Builder(this).setTitle("oops").setMessage(message).setPositiveButton(android.R.string.ok,null).setIcon(android.R.drawable.ic_dialog_alert).show();


}


}

