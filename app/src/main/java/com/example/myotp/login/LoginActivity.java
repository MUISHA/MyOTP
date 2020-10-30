package com.example.myotp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myotp.R;
import com.example.myotp.menu.Drawables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login,btn_signIn;
    EditText mEmails,passwords;
    ProgressBar progressBar_login;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmails = findViewById(R.id.mEmails);
        passwords = findViewById(R.id.passwords);
        progressBar_login = findViewById(R.id.progressBar_login);

        progressBar_login.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        btn_login = findViewById(R.id.btn_login);
        btn_signIn = findViewById(R.id.btn_signIn);
        /**
         * Login in activity
         */
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmails.getText().toString().trim();
                String password = passwords.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmails.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwords.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    passwords.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar_login.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Bien connect", Toast.LENGTH_SHORT).show();
                                    mEmails.setText("");
                                    passwords.setText("");
                                    progressBar_login.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(getApplicationContext(), Drawables.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Erreur de connect"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, Drawables.class);
        startActivity(intent);
        finish();
    }
}