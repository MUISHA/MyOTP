package com.example.myotp.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myotp.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myotp.backend.AudioPlayer;
import com.example.myotp.backend.BaseActivity;
import com.example.myotp.backend.SinchService;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.video.VideoCallListener;

import java.util.List;

public class Incoming_audio_call extends BaseActivity {


    static final String TAG = Incoming_audio_call.class.getSimpleName();
    private String mCallId;
    private AudioPlayer mAudioPlayer;

    ImageView callattend,callreject, callmsg ;

    TextView ur_number ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_audio_call);

        callattend = (ImageView) findViewById(R.id.attend_call);
        callreject = (ImageView) findViewById(R.id.reject_call);
        callmsg = (ImageView) findViewById(R.id.call_msg);
        ur_number = ( TextView ) findViewById( R.id.otheruser);


        callattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answerClicked();

            }
        });


        callreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                declineClicked();

            }
        });

        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);


    }

    @Override
    protected void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            ur_number.setText( getSinchServiceInterface().getCall(mCallId).getRemoteUserId() );


        } else {
            Log.e(TAG, "Started with invalid callId, aborting");
            finish();
        }
    }



    private void answerClicked() {

        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.answer();
            finish();
            Intent intent = new Intent(this, AudioCallScreen.class);
            intent.putExtra(SinchService.CALL_ID, mCallId);
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void declineClicked() {


        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended, cause: " + cause.toString());
            mAudioPlayer.stopRingtone();
            finish();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");

            mAudioPlayer.playRingtone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }



    }



}
