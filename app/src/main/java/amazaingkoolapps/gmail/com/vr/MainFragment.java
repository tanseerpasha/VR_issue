package amazaingkoolapps.gmail.com.vr;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Dell on 2/19/2018.
 */

public class MainFragment extends Fragment implements View.OnClickListener{


    private int RECORD_AUDIO_REQUEST_CODE = 1 ;

    private Toolbar toolbar;
    private Chronometer chronometer;
    private ImageView voiceRecord, voicePause, voiceStop, voiceSave, voicePlay, voiceCancel;
    private TextView recordHint, stopHint, pauseHint, saveHint, playHint, cancelHint;
    private SeekBar seekBar;
    private LinearLayout layoutHint, layoutIcons;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String fileName = null;
    private String mFileName = null;
    private int lastProgress = 0;
    private Handler mHandler = new Handler();
    private boolean isPlaying = false;


    private void resetRecorder() {


        TransitionManager.beginDelayedTransition(layoutHint);
        voiceRecord.setVisibility(View.VISIBLE);
        recordHint.setVisibility(View.VISIBLE);

        voicePlay.setVisibility(View.GONE);
        playHint.setVisibility(View.GONE);

        voiceStop.setVisibility(View.VISIBLE);
        stopHint.setVisibility(View.VISIBLE);

        voicePause.setVisibility(View.GONE);
        pauseHint.setVisibility(View.GONE);

        voiceCancel.setVisibility(View.GONE);
        cancelHint.setVisibility(View.GONE);


        voiceSave.setVisibility(View.GONE);
        saveHint.setVisibility(View.GONE);

        seekBar.setVisibility(View.GONE);
    }




    private void saveVoiceClip(String userProvidedFileName){

        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
        if(dir.exists()){
            File from = new File(dir,mFileName);
            File to = new File(dir,userProvidedFileName + ".mp3");
            if(from.exists())
                if( from.renameTo(to)){
                    Toast.makeText(getActivity(), "Recording saved successfully as " + userProvidedFileName + ".mp3", Toast.LENGTH_SHORT).show();
                }
        }
        Toast.makeText(getActivity(), "Recording saved successfully.", Toast.LENGTH_SHORT).show();
        resetRecorder();
    }

    private void getVoiceRecordFileName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_editdialog, null);
        builder.setView(dialogView);
        final EditText voiceRecordFileName = (EditText) dialogView.findViewById(R.id.voiceRecordFileName);

        builder.setCancelable(true);
        builder.setTitle("Voice File Name");

        builder.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        saveVoiceClip(voiceRecordFileName.getText().toString());

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),
                        "Cancel",
                        Toast.LENGTH_SHORT).show();


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    private void deleteTheVoiceClip(){

        if(fileName != null){

            File root = android.os.Environment.getExternalStorageDirectory();
            File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/"  + mFileName);
            if (file.exists()) {
                if(file.delete()){
                    Toast.makeText(getActivity(), "Recording cancelled", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Recording couldn't be removed", Toast.LENGTH_SHORT).show();
                }

            }

        }

        resetRecorder();


    }

    private void pausePlaying() {
        chronometer.stop();
        mPlayer.pause();
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
//fileName is global string. it contains the Uri to the recently recorded audio.
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
        //making the imageview pause button
        voicePause.setImageResource(R.drawable.ic_pause_black_36dp);

        seekBar.setProgress(lastProgress);
        mPlayer.seekTo(lastProgress);
        seekBar.setMax(mPlayer.getDuration());
        seekUpdation();
        chronometer.start();

        /** once the audio is complete, timer is stopped here**/
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                lastProgress = 0;
                seekBar.setProgress(lastProgress);
                mPlayer.seekTo(lastProgress);
            }
        });

        /** moving the track as per the seekBar's position**/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if( mPlayer!=null && fromUser ){
                    //here the track's progress is being changed as per the progress bar
                    mPlayer.seekTo(progress);
                    //timer is being updated as per the progress of the seekbar
                    chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
                    lastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    private void seekUpdation() {
        if(mPlayer != null){
            int mCurrentPosition = mPlayer.getCurrentPosition() ;
            seekBar.setProgress(mCurrentPosition);
            lastProgress = mCurrentPosition;
        }
        mHandler.postDelayed(runnable, 100);
    }



    private void stopRecording() {

        try{
            mRecorder.stop();
            mRecorder.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mRecorder = null;
        //starting the chronometer
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        //showing the play button

    }

    private void prepareforStop() {
        TransitionManager.beginDelayedTransition(layoutHint);
        voiceRecord.setVisibility(View.GONE);
        recordHint.setVisibility(View.GONE);

        voicePlay.setVisibility(View.VISIBLE);
        playHint.setVisibility(View.VISIBLE);

        voiceStop.setVisibility(View.GONE);
        stopHint.setVisibility(View.GONE);

        voicePause.setVisibility(View.VISIBLE);
        pauseHint.setVisibility(View.VISIBLE);

        voiceCancel.setVisibility(View.VISIBLE);
        cancelHint.setVisibility(View.VISIBLE);


        voiceSave.setVisibility(View.VISIBLE);
        saveHint.setVisibility(View.VISIBLE);

        seekBar.setVisibility(View.VISIBLE);
    }

    private void stopPlaying() {
        try{
            mPlayer.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mPlayer = null;
        isPlaying = false;
        //showing the play button
        //voicePause.setImageResource(R.drawable.ic_pause_black_36dp);
        chronometer.stop();
    }




    private void startRecording() {
        //we use the MediaRecorder class to record
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
         * and the audios are being stored in the Audios folder **/
        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
        if (!file.exists()) {
            file.mkdirs();
        }
        mFileName = String.valueOf(System.currentTimeMillis() + ".mp3");
        fileName =  root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" +
                mFileName;
        Log.d("filename",fileName);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastProgress = 0;
        seekBar.setProgress(0);
        stopPlaying();
        //starting the chronometer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }




    private void prepareforRecording() {
        TransitionManager.beginDelayedTransition(layoutIcons);
        voiceRecord.setVisibility(View.GONE);
        recordHint.setVisibility(View.GONE);

        voicePlay.setVisibility(View.GONE);
        playHint.setVisibility(View.GONE);

        voicePause.setVisibility(View.GONE);
        pauseHint.setVisibility(View.GONE);

        voiceStop.setVisibility(View.VISIBLE);
        stopHint.setVisibility(View.VISIBLE);

        voiceSave.setVisibility(View.GONE);
        saveHint.setVisibility(View.GONE);
    }

    private void showToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if( v == voiceRecord){
            prepareforRecording();
            startRecording();
        }else if( v == voiceStop){
            prepareforStop();
            stopRecording();

        }else if( v == voicePlay){
            showToast("voicePlay");
            if( !isPlaying && fileName != null ){
                showToast("if");
                isPlaying = true;
                startPlaying();
            }else{
                showToast("else");
            }

        }else if(v == voicePause){
            if( isPlaying && fileName != null ) {
                isPlaying = false;
                pausePlaying();
            }


        }else if(v == voiceCancel){
            showToast("cancel");
            stopPlaying();
            deleteTheVoiceClip();
        }else if(v == voiceSave){
            getVoiceRecordFileName();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getPermissionToRecordAudio();
        }
        initViews(view);
    }

    private void initViews(View view) {

        /** setting up the toolbar  **/
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Voice Recorder");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        chronometer = (Chronometer) view.findViewById(R.id.chronometerTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());

        layoutHint = (LinearLayout) view.findViewById(R.id.layoutHint);
        layoutIcons = (LinearLayout) view.findViewById(R.id.layoutIcons);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        voiceRecord = (ImageView) view.findViewById(R.id.voiceRecord);
        voiceStop = (ImageView) view.findViewById(R.id.voiceStop);
        voicePause = (ImageView) view.findViewById(R.id.voicePause);
        voiceSave = (ImageView) view.findViewById(R.id.voiceSave);
        voicePlay = (ImageView) view.findViewById(R.id.voicePlay);
        voiceCancel = (ImageView) view.findViewById(R.id.voiceCancel);


        recordHint = (TextView) view.findViewById(R.id.recordHint);
        stopHint = (TextView) view.findViewById(R.id.stopHint);
        pauseHint = (TextView) view.findViewById(R.id.pauseHint);
        saveHint = (TextView) view.findViewById(R.id.saveHint);
        playHint = (TextView) view.findViewById(R.id.playHint);
        cancelHint = (TextView) view.findViewById(R.id.cancelHint);

        voiceRecord.setOnClickListener(this);
        voiceStop.setOnClickListener(this);
        voicePause.setOnClickListener(this);
        voiceSave.setOnClickListener(this);
        voicePlay.setOnClickListener(this);
        voiceCancel.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }

    // Callback with the request from calling requestPermissions(...)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){

                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
                getActivity().finishAffinity();
            }
        }

    }



    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
