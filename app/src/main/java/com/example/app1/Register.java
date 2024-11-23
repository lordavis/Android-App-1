package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText editTextEmail , editTextPassword, getEditTextPassword2;
    Button buttonReg;
    Button buttonLogin;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.view_email);
        editTextPassword = findViewById(R.id.view_password);
        getEditTextPassword2 = findViewById(R.id.view_password2);
        buttonReg = findViewById(R.id.btn_register2);
        buttonLogin = findViewById(R.id.btn_login2);
        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(v -> {
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if(TextUtils.equals(editTextPassword.toString() , getEditTextPassword2.toString())){
                Toast.makeText(this , "Both passwords do not match.", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(email)){
                Toast.makeText(Register.this,"Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(Register.this,"Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.getTrimmedLength(password) < 6){
                Toast.makeText(Register.this,"Password too short",Toast.LENGTH_SHORT).show();
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}