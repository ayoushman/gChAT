package com.Ayoushman.flashchatnewfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

//    TODO: Add member variables here:
    private FirebaseAuth mAuth ;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmailView = (EditText)findViewById(R.id.EditTextEmail);
        mPasswordView = (EditText)findViewById(R.id.EditTextPassword);



        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mAuth = FirebaseAuth.getInstance();

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.Ayoushman.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {


       String email = mEmailView.getText().toString();
       String password = mPasswordView.getText().toString();
       if(email.equals("")||password.equals("")){
           return;
       }

        Toast.makeText(this,"Login In Progress....",Toast.LENGTH_SHORT).show();
       mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               Log.d("GcHat", "SignIn with email is complete: " + task.isSuccessful());
               if(!task.isSuccessful()){
                   Log.d("GcHat", "Problem Signing In ............:" + task.getException());
                   showAlertDialog("There was a problem signing in.........");

               }
               else {
                   Intent intent = new Intent(LoginActivity.this,MainChatActivity.class);
                   finish();
                   startActivity(intent);
               }
           }
       });



    }

    private void showAlertDialog(String message){

        new AlertDialog.Builder(this).setTitle("oops").setMessage(message).setPositiveButton(android.R.string.ok,null).setIcon(android.R.drawable.ic_dialog_alert).show();


    }




}