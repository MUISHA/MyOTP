package com.example.myotp.frontend;


import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myotp.R;
import com.example.myotp.backend.AudioPlayer;
import com.example.myotp.backend.BaseActivity;
import com.example.myotp.backend.SinchService;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.calling.CallState;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AudioCallScreen extends BaseActivity implements  SinchService.StartFailedListener{

    static final String TAG = AudioCallScreen.class.getSimpleName();
    static final String CALL_START_TIME = "callStartTime";
    static final String ADDED_LISTENER = "addedListener";

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;

    private String mCallId;
    private long mCallStart = 0;
    private boolean mAddedListener = false;
    private boolean mVideoViewsAdded = false;

    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;

    ImageView img, loudspeaker, camera , mic;
    static int loud_flag =0 , cam_flag =0 , mic_flag =0;



    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {


    }


    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            AudioCallScreen.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong(CALL_START_TIME, mCallStart);
        savedInstanceState.putBoolean(ADDED_LISTENER, mAddedListener);
        savedInstanceState.putInt("loud",loud_flag);
        savedInstanceState.putInt("cam",cam_flag);
        savedInstanceState.putInt("mic",mic_flag);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        mCallStart = savedInstanceState.getLong(CALL_START_TIME);
        mAddedListener = savedInstanceState.getBoolean(ADDED_LISTENER);

        loud_flag = savedInstanceState.getInt("loud");
        cam_flag = savedInstanceState.getInt("cam");
        mic_flag = savedInstanceState.getInt("mic");


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_call_screen);

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = (TextView) findViewById(R.id.callDuration);
        mCallerName = (TextView) findViewById(R.id.remoteUser);
        mCallState = (TextView) findViewById(R.id.callState);
        Button endCallButton = (Button) findViewById(R.id.hangup);

        endCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });

        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
        if (savedInstanceState == null) {
            mCallStart = System.currentTimeMillis();
        }

        mCallState.setText("Calling");

        loudspeaker = (ImageView) findViewById(R.id.loudspeaker);
        mic = (ImageView) findViewById(R.id.mic);

        if ( getIntent(). hasExtra ("number") ){

            mCallerName.setText( getIntent() . getStringExtra("number") );

        }



        loudspeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loud_flag == 0){

                    loud_flag = 1;
                    loudspeaker.setBackground(getResources().getDrawable(R.drawable.phonecircle));
                    getSinchServiceInterface().getAudioController().enableSpeaker();

                }
                else {

                    loudspeaker.setBackground(getResources().getDrawable(R.drawable.trans));
                    loud_flag = 0;
                    getSinchServiceInterface().getAudioController().disableSpeaker();

                }

            }

        });


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mic_flag == 0){


                    mic.setBackground(getResources().getDrawable(R.drawable.phonecircle));
                    getSinchServiceInterface().getAudioController().mute();
                    mic_flag = 1;
                }
                else {

                    mic.setBackground(getResources().getDrawable(R.drawable.trans));
                    getSinchServiceInterface().getAudioController().unmute();
                    mic_flag = 0;
                }


            }
        });


    }



    //method to update video feeds in the UI
    private void updateUI() {

        System.out.println("\n\n\n\n\n   qqqqq  =====  null "  );

        if (getSinchServiceInterface() == null) {
            return; // early
        }

        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {

            System.out.println("\n\n\n\n\n   qqqqq  ===== " + call.getState().toString() );
            mCallState.setText(call.getState().toString());

            // online data
            /*

           if(me == 1) {

                addlocalView();

            }

            if(other == 1){

                addRemoteView();

            }

            if(me == 0 && other == 0 ) {

                call_ref.child(mCallId).child(auth.getCurrentUser().getUid()).setValue(0);
                call_ref.child(mCallId).child(call.getRemoteUserId()).setValue(0);
                call_ref.child(mCallId).child("active").setValue(0);

            }

           */


            if (call.getState() == CallState.ESTABLISHED) {
                //when the call is established, addVideoViews configures the video to  be shown

            }
        }
    }

    //stop the timer when call is ended
    @Override
    public void onStop() {

        super.onStop();

    }

    //start the timer for the call duration here

    @Override
    public void onStart() {

        super.onStart();
        mTimer = new Timer();
        updateUI();

    }


    //method to end the call
    private void endCall() {

        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);

        if ( call != null ) {

            call.hangup();
            call = null;

        }
        finish();
    }

    private String formatTimespan( long timespan ) {

        long totalSeconds = timespan / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format( Locale.US, "%02d:%02d", minutes , seconds );

    }

    //method to update live duration of the call
    private void updateCallDuration() {
        if (mCallStart > 0) {

            mCallDuration.setText(formatTimespan( System.currentTimeMillis() - mCallStart ));

        }
    }


    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            if (!mAddedListener) {

                mCallerName.setText( call.getRemoteUserId() );


                call.addCallListener(new SinchCallListener());
                mAddedListener = true;



            }
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }

        updateUI();
    }


    private class SinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(Call call) {

            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
            mCallState.setText( "Ringing" );
            mCallState.setText(call.getState().toString());
            mCallerName.setText( call.getRemoteUserId() );

        }

        @Override
        public void onCallEstablished(Call call) {

            mCallerName.setText( call.getRemoteUserId() );

            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            // AudioController audioController = getSinchServiceInterface().getAudioController();
            // audioController.enableSpeaker();
            mCallStart = System.currentTimeMillis();
            Log.d(TAG, "Call offered video: " + call.getDetails().isVideoOffered());

            mDurationTask = new UpdateCallDurationTask();
            mTimer.schedule( mDurationTask , 0, 500);

        }

        @Override
        public void onCallEnded(Call call) {

            mCallerName.setText( call.getRemoteUserId() );

            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            String endMsg = "Call ended: " + call.getDetails().toString();
            Toast.makeText(AudioCallScreen.this, endMsg, Toast.LENGTH_LONG).show();

            endCall();

        }


        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }

    }

}
