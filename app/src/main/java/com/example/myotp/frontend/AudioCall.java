package com.example.myotp.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myotp.R;
import com.example.myotp.backend.BaseActivity;
import com.example.myotp.backend.SinchService;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;

public class AudioCall extends BaseActivity implements SinchService.StartFailedListener {

    EditText call_no;
    Button call_btn;
    String number;
    String self;
    ProgressDialog progress;

    TextView ur_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_call);

        progress = new ProgressDialog(this);

        call_no = ( EditText ) findViewById ( R.id.call_number );
        call_btn = ( Button )   findViewById ( R.id.callbtn );
        ur_number = ( TextView ) findViewById ( R.id.ur_num );


        progress.setMessage("Please wait .....");
        progress.setCancelable(false);
        progress.show();


        if ( getIntent().hasExtra("number") ){

            self = getIntent().getStringExtra("number");
            ur_number.setText( "Login as : "+ self );

        }
        else{
            self = "0";
        }

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (call_no.getText().toString().isEmpty()) {

                    Toast.makeText(AudioCall.this, "Empty Phone Number not allowed",
                            Toast.LENGTH_LONG).show();

                }

                else if( self.equalsIgnoreCase( call_no.getText().toString().trim() )  ){

                    Toast.makeText(AudioCall.this, "You can't call yourself",
                            Toast.LENGTH_LONG).show();

                }

                else {

                    number = call_no.getText().toString().trim();
                    Call call = getSinchServiceInterface().callUseraudio(number);
                    final String callId = call.getCallId();

                    Intent callScreen = new Intent(AudioCall.this, AudioCallScreen.class);
                    callScreen.putExtra(SinchService.CALL_ID, callId);
                    callScreen.putExtra("self", self);
                    callScreen.putExtra("number", number);


                    startActivity(callScreen);

                }
            }
        });




    }

    @Override
    protected void onServiceConnected() {
        // mLoginButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);

        Toast.makeText(AudioCall.this, "Server connected",
                Toast.LENGTH_LONG).show();

        progress.dismiss();

    }


    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {


    }
}
