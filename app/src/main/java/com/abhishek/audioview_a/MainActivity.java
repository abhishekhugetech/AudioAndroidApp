package com.abhishek.audioview_a;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager aManager;

    public void play(View view){
        mPlayer.start();
    }
    public void pause(View view){
        mPlayer.pause();
    }
    public void stop(View view){
        mPlayer.stop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.sound);

        aManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = aManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = aManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Creating a New Seekbar Object
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        //Setting Up the On Seekbar Change Listener

        // Inside this -->Creating On Progress Change Method that will do Something when the Seekbar is Bieng Changed
        volumeControl.setOnSeekBarChangeListener( new OnSeekBarChangeListener(){

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
                Log.i("Change of SeekBar",progress+"");
                aManager.setStreamVolume(AudioManager.STREAM_MUSIC ,progress,0);
            }

        });

        final SeekBar seekMusic = (SeekBar)findViewById(R.id.musicSeec);
        seekMusic.setMax(mPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekMusic.setProgress(mPlayer.getCurrentPosition());
            }
        },0,100);
        seekMusic.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            mPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.start();
            }
        });



















    }

}
