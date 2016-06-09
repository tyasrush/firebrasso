package com.liez.tyasrus.firebrasso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    private final String FIREBASE_URL = "https://firebrasso.firebaseio.com/";
    private final String MESSAGE_ID = "message_id";

    private EditText messageEditText;
    private TextView messageTextView;
    private Button messageButton;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        messageEditText = (EditText) findViewById(R.id.et_message);
        messageTextView = (TextView) findViewById(R.id.tv_message);
        messageButton = (Button) findViewById(R.id.btn_send_message);
        if (messageButton != null)
            messageButton.setOnClickListener(this);

        firebase = new Firebase(FIREBASE_URL);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == messageButton.getId()) {
            firebase.child(MESSAGE_ID).setValue(messageEditText.getText() != null ? messageEditText.getText().toString() : "edittext is null");
            firebase.child(MESSAGE_ID).addValueEventListener(this);
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        System.out.println(dataSnapshot.getValue());
        messageTextView.setText(dataSnapshot.getValue() != null ? "message value : \n" + dataSnapshot.getValue() : "message value : \n" + "null");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
