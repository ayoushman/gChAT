package com.Ayoushman.flashchatnewfirebase;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDataBaseReference ;
    private ChatListAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chat_activity);

        mInputText = (EditText) findViewById(R.id.input_message);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();


        // Link the Views in the layout to the Java code


        // TODO: Send the message when the "enter" button is pressed
mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        sendMessage();
        return true;
    }
});

        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
sendMessage();
            }
        });

    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName(){
        SharedPreferences prefs =  getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
        if(mDisplayName==null){
            mDisplayName = "Anonymous";
        }
    }


    private void sendMessage() {


        Log.d("GcHat","I SEND SOMETHING...........");

        String input = mInputText.getText().toString();
        if(!input.equals("")){
            InstantMessage chat = new InstantMessage(input,mDisplayName);
            mDataBaseReference.child("messages").push().setValue(chat);
        mInputText.setText("");


        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
@Override
public void onStart(){
        super.onStart();
        mAdapter = new ChatListAdapter(this,mDataBaseReference,mDisplayName);
        mChatListView.setAdapter(mAdapter);
}

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        mAdapter.cleanup();

    }

}
