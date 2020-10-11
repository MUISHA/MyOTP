package com.example.myotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendOPTActivity extends AppCompatActivity  {
    private EditText inputMobile;
    private Button buttonGetOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_o_p_t);
        inputMobile = findViewById(R.id.inputMobile);
        buttonGetOTP = findViewById(R.id.buttonGetOTP);
        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMobile.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(SendOPTActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SendOPTActivity.this, VerifyActivity.class);
                intent.putExtra("mobile", inputMobile.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

}