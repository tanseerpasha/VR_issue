package amazaingkoolapps.gmail.com.vr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MF_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            getPermissionToRecordAudio();
//        }
//        initViews();
//
        if(savedInstanceState== null){
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recording_details,mainFragment , TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_list:
                Intent intent = new Intent(this, RecordingListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


}
//*
//
//    private int RECORD_AUDIO_REQUEST_CODE =123 ;
//
//    private Toolbar toolbar;
//    private Chronometer chronometer;
//    private ImageView voiceRecord, voicePause, voiceStop, voiceSave, voicePlay, voiceCancel;
//    private TextView recordHint, stopHint, pauseHint, saveHint, playHint, cancelHint;
//    private SeekBar seekBar;
//    private LinearLayout layoutHint, layoutIcons;
//
//    private MediaRecorder mRecorder;
//    private MediaPlayer mPlayer;
//    private String fileName = null;
//    private String mFileName = null;
//    private int lastProgress = 0;
//    private Handler mHandler = new Handler();
//    private boolean isPlaying = false;
//
//
//    private void resetRecorder() {
//
//
//        TransitionManager.beginDelayedTransition(layoutHint);
//        voiceRecord.setVisibility(View.VISIBLE);
//        recordHint.setVisibility(View.VISIBLE);
//
//        voicePlay.setVisibility(View.GONE);
//        playHint.setVisibility(View.GONE);
//
//        voiceStop.setVisibility(View.VISIBLE);
//        stopHint.setVisibility(View.VISIBLE);
//
//        voicePause.setVisibility(View.GONE);
//        pauseHint.setVisibility(View.GONE);
//
//        voiceCancel.setVisibility(View.GONE);
//        cancelHint.setVisibility(View.GONE);
//
//
//        voiceSave.setVisibility(View.GONE);
//        saveHint.setVisibility(View.GONE);
//
//        seekBar.setVisibility(View.GONE);
//    }
//
//
//    private void saveVoiceClip(){
//
//        File root = Environment.getExternalStorageDirectory();
//        File dir = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
//        if(dir.exists()){
//            File from = new File(dir,mFileName);
//            File to = new File(dir,"tanseer" + ".mp3");
//            if(from.exists())
//                if( from.renameTo(to)){
//                    Toast.makeText(this, "Recording saved successfully as tanseer.mp3", Toast.LENGTH_SHORT).show();
//                }
//        }
//        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
//        resetRecorder();
//    }
//
//
//    private void deleteTheVoiceClip(){
//
//        if(fileName != null){
//
//            File root = android.os.Environment.getExternalStorageDirectory();
//            File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/"  + mFileName);
//            if (file.exists()) {
//                if(file.delete()){
//                    Toast.makeText(this, "Recording cancelled", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this, "Recording couldn't be removed", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//        }
//
//        resetRecorder();
//
//
//    }
//
//    private void pausePlaying() {
//        chronometer.stop();
//        mPlayer.pause();
//    }
//
//    private void startPlaying() {
//        mPlayer = new MediaPlayer();
//        try {
////fileName is global string. it contains the Uri to the recently recorded audio.
//            mPlayer.setDataSource(fileName);
//            mPlayer.prepare();
//            mPlayer.start();
//        } catch (IOException e) {
//            Log.e("LOG_TAG", "prepare() failed");
//        }
//        //making the imageview pause button
//        voicePause.setImageResource(R.drawable.ic_pause_black_36dp);
//
//        seekBar.setProgress(lastProgress);
//        mPlayer.seekTo(lastProgress);
//        seekBar.setMax(mPlayer.getDuration());
//        seekUpdation();
//        chronometer.start();
//
//        /** once the audio is complete, timer is stopped here**/
//        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                isPlaying = false;
//                chronometer.stop();
//                chronometer.setBase(SystemClock.elapsedRealtime());
//                lastProgress = 0;
//                seekBar.setProgress(lastProgress);
//                mPlayer.seekTo(lastProgress);
//            }
//        });
//
//        /** moving the track as per the seekBar's position**/
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if( mPlayer!=null && fromUser ){
//                    //here the track's progress is being changed as per the progress bar
//                    mPlayer.seekTo(progress);
//                    //timer is being updated as per the progress of the seekbar
//                    chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
//                    lastProgress = progress;
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//    }
//
//
//
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            seekUpdation();
//        }
//    };
//
//    private void seekUpdation() {
//        if(mPlayer != null){
//            int mCurrentPosition = mPlayer.getCurrentPosition() ;
//            seekBar.setProgress(mCurrentPosition);
//            lastProgress = mCurrentPosition;
//        }
//        mHandler.postDelayed(runnable, 100);
//    }
//
//
//
//    private void stopRecording() {
//
//        try{
//            mRecorder.stop();
//            mRecorder.release();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        mRecorder = null;
//        //starting the chronometer
//        chronometer.stop();
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        //showing the play button
//
//    }
//
//    private void prepareforStop() {
//        TransitionManager.beginDelayedTransition(layoutHint);
//        voiceRecord.setVisibility(View.GONE);
//        recordHint.setVisibility(View.GONE);
//
//        voicePlay.setVisibility(View.VISIBLE);
//        playHint.setVisibility(View.VISIBLE);
//
//        voiceStop.setVisibility(View.GONE);
//        stopHint.setVisibility(View.GONE);
//
//        voicePause.setVisibility(View.VISIBLE);
//        pauseHint.setVisibility(View.VISIBLE);
//
//        voiceCancel.setVisibility(View.VISIBLE);
//        cancelHint.setVisibility(View.VISIBLE);
//
//
//        voiceSave.setVisibility(View.VISIBLE);
//        saveHint.setVisibility(View.VISIBLE);
//
//        seekBar.setVisibility(View.VISIBLE);
//    }
//
//    private void stopPlaying() {
//        try{
//            mPlayer.release();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        mPlayer = null;
//        isPlaying = false;
//        //showing the play button
//        //voicePause.setImageResource(R.drawable.ic_pause_black_36dp);
//        chronometer.stop();
//    }
//
//
//
//
//    private void startRecording() {
//        //we use the MediaRecorder class to record
//        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
//         * and the audios are being stored in the Audios folder **/
//        File root = android.os.Environment.getExternalStorageDirectory();
//        File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        mFileName = String.valueOf(System.currentTimeMillis() + ".mp3");
//        fileName =  root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" +
//                mFileName;
//        Log.d("filename",fileName);
//        mRecorder.setOutputFile(fileName);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//
//        try {
//            mRecorder.prepare();
//            mRecorder.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        lastProgress = 0;
//        seekBar.setProgress(0);
//        stopPlaying();
//        //starting the chronometer
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.start();
//    }
//
//
//
//
//    private void prepareforRecording() {
//        TransitionManager.beginDelayedTransition(layoutIcons);
//        voiceRecord.setVisibility(View.GONE);
//        recordHint.setVisibility(View.GONE);
//
//        voicePlay.setVisibility(View.GONE);
//        playHint.setVisibility(View.GONE);
//
//        voicePause.setVisibility(View.GONE);
//        pauseHint.setVisibility(View.GONE);
//
//        voiceStop.setVisibility(View.VISIBLE);
//        stopHint.setVisibility(View.VISIBLE);
//
//        voiceSave.setVisibility(View.GONE);
//        saveHint.setVisibility(View.GONE);
//    }
//
//    private void showToast(String msg){
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if( v == voiceRecord){
//            prepareforRecording();
//            startRecording();
//        }else if( v == voiceStop){
//            prepareforStop();
//            stopRecording();
//
//        }else if( v == voicePlay){
//            showToast("voicePlay");
//            if( !isPlaying && fileName != null ){
//                showToast("if");
//                isPlaying = true;
//                startPlaying();
//            }else{
//                showToast("else");
//            }
//
//        }else if(v == voicePause){
//            if( isPlaying && fileName != null ) {
//                isPlaying = false;
//                pausePlaying();
//            }
//
//
//        }else if(v == voiceCancel){
//            showToast("cancel");
//            stopPlaying();
//            deleteTheVoiceClip();
//        }else if(v == voiceSave){
//
//            saveVoiceClip();
//
//        }
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            getPermissionToRecordAudio();
//        }
//        initViews();
//    }
//
//    private void initViews() {
//
//        /** setting up the toolbar  **/
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Voice Recorder");
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
//        setSupportActionBar(toolbar);
//
//        chronometer = (Chronometer) findViewById(R.id.chronometerTimer);
//        chronometer.setBase(SystemClock.elapsedRealtime());
//
//        layoutHint = (LinearLayout) findViewById(R.id.layoutHint);
//        layoutIcons = (LinearLayout) findViewById(R.id.layoutIcons);
//        seekBar = (SeekBar) findViewById(R.id.seekBar);
//
//        voiceRecord = (ImageView) findViewById(R.id.voiceRecord);
//        voiceStop = (ImageView) findViewById(R.id.voiceStop);
//        voicePause = (ImageView) findViewById(R.id.voicePause);
//        voiceSave = (ImageView) findViewById(R.id.voiceSave);
//        voicePlay = (ImageView) findViewById(R.id.voicePlay);
//        voiceCancel = (ImageView) findViewById(R.id.voiceCancel);
//
//
//        recordHint = (TextView) findViewById(R.id.recordHint);
//        stopHint = (TextView) findViewById(R.id.stopHint);
//        pauseHint = (TextView) findViewById(R.id.pauseHint);
//        saveHint = (TextView) findViewById(R.id.saveHint);
//        playHint = (TextView) findViewById(R.id.playHint);
//        cancelHint = (TextView) findViewById(R.id.cancelHint);
//
//        voiceRecord.setOnClickListener(this);
//        voiceStop.setOnClickListener(this);
//        voicePause.setOnClickListener(this);
//        voiceSave.setOnClickListener(this);
//        voicePlay.setOnClickListener(this);
//        voiceCancel.setOnClickListener(this);
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void getPermissionToRecordAudio() {
//        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
//        // checking the build version since Context.checkSelfPermission(...) is only available
//        // in Marshmallow
//        // 2) Always check for permission (even if permission has already been granted)
//        // since the user can revoke permissions at any time through Settings
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//
//            // The permission is NOT already granted.
//            // Check if the user has been asked about this permission already and denied
//            // it. If so, we want to give more explanation about why the permission is needed.
//            // Fire off an async request to actually get the permission
//            // This will show the standard permission request dialog UI
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    RECORD_AUDIO_REQUEST_CODE);
//
//        }
//    }
//
//    // Callback with the request from calling requestPermissions(...)
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        // Make sure it's our original READ_CONTACTS request
//        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
//            if (grantResults.length == 3 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){
//
//                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();
//
//            } else {
//                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
//                finishAffinity();
//            }
//        }
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.list_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.item_list:
//                Intent intent = new Intent(this, RecordingListActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mRecorder != null) {
//            mRecorder.release();
//            mRecorder = null;
//        }
//
//        if (mPlayer != null) {
//            mPlayer.release();
//            mPlayer = null;
//        }
//    }
//}
