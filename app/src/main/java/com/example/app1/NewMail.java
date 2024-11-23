
package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewMail extends AppCompatActivity {

    EditText view_emailId, view_txtMsg;
    Button btn_sendMsg;
    Boolean serviceRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);

        Button contactsPage = findViewById(R.id.btn_contacts_page);

        Button start = findViewById(R.id.startButton);
        Button stop = findViewById(R.id.stopButton);

        contactsPage.setOnClickListener(v -> startActivity(new Intent(this, Contacts.class)));

        //Media player service
        start.setOnClickListener(v -> {
            if (!serviceRunning){
                serviceRunning = true;
                startService(new Intent(NewMail.this, NewService.class));
            }
        });

        stop.setOnClickListener(v -> {
            serviceRunning = false;
            stopService(new Intent(NewMail.this, NewService.class));
        });

        view_emailId = findViewById(R.id.view_emailId);
        view_txtMsg = findViewById(R.id.view_txtMsg);
        btn_sendMsg = findViewById(R.id.btn_sendMsg);

        btn_sendMsg.setOnClickListener(v -> sendMessage());
    }

    //File provider

    public void sendMessage() {

        if(TextUtils.isEmpty(view_emailId.getText().toString())){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,view_txtMsg.getText().toString() );
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage("com.google.android.gm");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{view_emailId.getText().toString()});
            intent.putExtra(Intent.EXTRA_TEXT, view_txtMsg.getText().toString());
            startActivity(intent);
        }

    }

}