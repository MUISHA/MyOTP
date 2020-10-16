package com.example.myotp.frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myotp.R;
import com.example.myotp.backend.BaseActivity;
import com.example.myotp.backend.SinchService;
import com.sinch.android.rtc.SinchError;


public class Enternumber extends BaseActivity implements SinchService.StartFailedListener {

    EditText phonetxt;
    Button loginbtn;
    String number ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        phonetxt = ( EditText ) findViewById ( R.id.phonetxt );
        loginbtn = ( Button )   findViewById ( R.id.loginbtn );

        if (ContextCompat.checkSelfPermission(Enternumber.this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Enternumber.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Enternumber.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  || ContextCompat.checkSelfPermission(Enternumber.this, android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
            }

        }


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( phonetxt.getText().toString().isEmpty() ) {

                    Toast.makeText(  Enternumber.this  , "Empty Phone Number not allowed",
                            Toast.LENGTH_LONG).show();

                }
                else{

                    number = phonetxt.getText().toString().trim();
                    if ( ! getSinchServiceInterface().isStarted() ) {

                        getSinchServiceInterface().startClient( number );
                        Toast.makeText(Enternumber.this,"Server Start Working ..... ",Toast.LENGTH_SHORT).show();

                        Intent i = new Intent( Enternumber.this , AudioCall.class );
                        i.putExtra("number", number);
                        startActivity( i );

                    }

                }

            }
        });

    }





    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }
}
