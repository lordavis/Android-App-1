package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText editTextEmail , editTextPassword;
    Button buttonReg;
    Button buttonLogin;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),RvProducts.class);
            intent.putExtra("GmailId", currentUser.getEmail());
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        AppDatabase appDatabase = AppDatabase.getDB(this);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.view_email);
        editTextPassword = findViewById(R.id.view_password);
        buttonReg = findViewById(R.id.btn_register1);
        buttonLogin = findViewById(R.id.btn_login1);

        String spEmail = sp.getString("email","");
        String spPassword = sp.getString("password","");

        editTextEmail.setText(spEmail);
        editTextPassword.setText(spPassword);

        buttonReg.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        });

        buttonLogin.setOnClickListener(v -> {
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if(TextUtils.isEmpty(email)){
                Toast.makeText(Login.this,"Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(Login.this,"Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_SHORT).show();

                            appDatabase.userDao().insertAll(
                                    new UserInfo(email,password)
                            );

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(),RvProducts.class);
                            intent.putExtra("GmailId",email);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });


        });
        }
}
