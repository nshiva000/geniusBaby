package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanivisu.geniusbaby.Models.MusicListModel.Datum;

import java.util.ArrayList;

public class MediaPlayerActivity extends AppCompatActivity {

    TextView songName,songDes;
    Button playBtn,previousBtn,nextBtn;

    SeekBar positionBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime,songPosition;
    Thread thread;

    ArrayList<Datum> songsList;

    private int seekForwardTime = 5 * 1000; // default 5 second
    private int seekBackwardTime = 5 * 1000; // default 5 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Music Player");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

      //  mp = new MediaPlayer();
        songName = findViewById(R.id.songNameBig);
        songDes = findViewById(R.id.songDesBig);
        playBtn = findViewById(R.id.playBtnBigScreen);

        previousBtn = findViewById(R.id.previous);
        nextBtn = findViewById(R.id.next);

        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);

        if (getIntent().getStringExtra("name") != null){
            songName.setText(getIntent().getStringExtra("name"));
        }

        if (getIntent().getStringExtra("des") != null){
            songDes.setText(getIntent().getStringExtra("des"));
        }

        songPosition = getIntent().getIntExtra("position",0);


        songsList = (ArrayList<Datum>) getIntent().getSerializableExtra("key");
        Log.e("shiva",songsList.toString()+"--size --"+ songsList.size());

        playSongWithPosition(songPosition);

    }


    private void playSongWithPosition(int songPosition){

        Uri myUri = Uri.parse("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3");

        if (songsList.get(songPosition).getCfile() != null){
            myUri = Uri.parse(songsList.get(songPosition).getCfile());
        }


        playSong(myUri);

    }





    private void playSong(Uri myUri){
        Log.e("shiva play",songPosition+"--"+songsList.size());

        mp = MediaPlayer.create(this,myUri);
        mp.setLooping(false);
        mp.seekTo(0);
        mp.setVolume(1f, 1f);
        totalTime = mp.getDuration();

        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


        mp.start();
        playBtn.setBackgroundResource(R.drawable.stop);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mp.isPlaying()) {
                    // Stopping
                    mp.start();
                    playBtn.setBackgroundResource(R.drawable.stop);

                } else {
                    // Playing
                    mp.pause();
                    playBtn.setBackgroundResource(R.drawable.play);
                }

            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewindSong();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardSong();
            }
        });





        // Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

    }



    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }


    public void playNxtSong(){
        if (mp != null) {

            if (songPosition < (songsList.size()-1)){
                Log.e("shiva",songPosition+"--"+songsList.size());
                songPosition++;
            }

            mp.stop();
            mp.release();
            mp = null;
           playSongWithPosition(songPosition);
        } else {

        }
    }

    private void playPreviousSong(){
        if (mp != null) {
            if (songPosition != 0){
                songPosition--;
            }
            mp.stop();
            mp.release();
            mp = null;
            playSongWithPosition(songPosition);

        } else {

        }
    }


    public void forwardSong() {

        playNxtSong();
//        if (mp != null) {
//            int currentPosition = mp.getCurrentPosition();
//            if (currentPosition + seekForwardTime <= mp.getDuration()) {
//                mp.seekTo(currentPosition + seekForwardTime);
//            } else {
//                mp.seekTo(mp.getDuration());
//            }
//        }
    }

    public void rewindSong() {

        playPreviousSong();
//        if (mp != null) {
//            int currentPosition = mp.getCurrentPosition();
//            if (currentPosition - seekBackwardTime >= 0) {
//                mp.seekTo(currentPosition - seekBackwardTime);
//            } else {
//                mp.seekTo(0);
//            }
//        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();
        mp.release();
        mp = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void toast_msg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
