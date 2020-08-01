package com.hanivisu.geniusbaby;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanivisu.geniusbaby.Adapter.MusicListAdapter;
import com.hanivisu.geniusbaby.Fragments.DailyActivitiesFragment;
import com.hanivisu.geniusbaby.Fragments.MediaFragment;
import com.hanivisu.geniusbaby.Models.MusicListModel.Datum;
import com.hanivisu.geniusbaby.Models.MusicListModel.MusicListModel;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    LinearLayout mediaLayout;
    RelativeLayout.LayoutParams params;
    ImageView backBtn;
    Button playSmall,playBig,playNxt,playBack;
    CardView bottomCardView;
    MediaPlayer mp;
    int songItem = 0;


    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    int totalTime;

    ArrayList<Datum> datumArrayList = new ArrayList<>();
    MusicListAdapter adapter;
    TextView songNameBig,songNameSmall,songDesBig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSongsList();


        fragmentManager = getSupportFragmentManager();
        bottomCardView = findViewById(R.id.bottom_cardView);
        mediaLayout = findViewById(R.id.mediaLayout);
        backBtn = findViewById(R.id.backBtn);
        playSmall = findViewById(R.id.playBtnSmallScreen);
        playBig = findViewById(R.id.playBtnBigScreen);

        playBack = findViewById(R.id.previous);
        playNxt = findViewById(R.id.next);


        songNameBig = findViewById(R.id.songNameBig);
        songNameSmall = findViewById(R.id.songNameSmall);
        songDesBig = findViewById(R.id.songDesBig);


        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);



        mp = MediaPlayer.create(this, Uri.parse("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3"));
        mp.setLooping(false);
        mp.seekTo(0);
        mp.setVolume(1f, 1f);
        totalTime = mp.getDuration();

        // Position Bar
        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int seeked_progess;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            seeked_progess = progress;
                            seeked_progess = seeked_progess * 1000;
                            mp.seekTo(seeked_progess);
                            positionBar.setProgress(progress);

                            Log.e("progress shiva",progress+"-----");
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mp.seekTo(seeked_progess);
                    }
                }
        );



        MediaFragment mediaFragment = new MediaFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.topLayout,mediaFragment,mediaFragment.getTag())
                .commit();


        bottomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaLayout.setVisibility(View.VISIBLE);
                bottomCardView.setVisibility(View.GONE);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaLayout.setVisibility(View.GONE);
                bottomCardView.setVisibility(View.VISIBLE);
            }
        });


        playBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playPause();
            }
        });

        playSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();

            }
        });

        playNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        playBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playBack();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MusicListAdapter(HomeActivity.this,datumArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



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



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            Log.e("shiva hello",currentPosition+"--");

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime-currentPosition);
            //remainingTimeLabel.setText("- " + remainingTime);
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


    private void playPause(){
        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            playSmall.setBackgroundResource(R.drawable.stop);
            playBig.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playSmall.setBackgroundResource(R.drawable.play);
            playBig.setBackgroundResource(R.drawable.play);
        }
    }


    private void getSongsList(){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        Call<MusicListModel> call = RetrofitClient.getmInstance().getApi().getSongs("1","j","j");
        call.enqueue(new Callback<MusicListModel>() {
            @Override
            public void onResponse(Call<MusicListModel> call, Response<MusicListModel> response) {
                progressDialog.dismiss();
                MusicListModel musicListModel = response.body();

                if (musicListModel != null){
                    if (musicListModel.getStatus()){
                        datumArrayList.addAll(musicListModel.getData());
                        adapter.notifyDataSetChanged();

                        try {
                            playSong(songItem);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<MusicListModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


}




    private void playNext() throws IOException {
        songItem++;
        playSong(songItem);
    }

    private void playBack() throws IOException {
        if (songItem != 0){
            songItem--;
        }

        playSong(songItem);
    }

//    private int getDurationInMilliseconds(String path) {
//        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
//        mmr.setDataSource(path);
//        int duration = Integer.parseInt(mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION));
//        mmr.release();
//        return duration;
//    }

    public void playSong(final int i) throws IOException {
        if(i<datumArrayList.size())
        {
            if (String.valueOf(datumArrayList.get(i).getCfile()) != null){
                mp.reset();
                /* load the new source */
                mp.setDataSource(String.valueOf(datumArrayList.get(i).getCfile()));
                /* Prepare the mediaplayer */
                mp.prepare();

                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        int duration = mp.getDuration();
                        if (duration <= 0) {

//                            Uri uri = Uri.parse(datumArrayList.get(i).getCfile());
//                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//                            mmr.setDataSource(datumArrayList.get(i).getCfile());
//                            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//                            int millSecond = Integer.parseInt(durationStr);
//                            //duration = getDurationInMilliseconds(datumArrayList.get(i).getCfile());
//                            duration = millSecond;
                        }
                        String remainingTime = createTimeLabel(duration);
                        positionBar.setMax(duration);
                        remainingTimeLabel.setText(remainingTime);
                        Log.i("time", duration + " ms");
                    }
                });

                playSmall.setBackgroundResource(R.drawable.stop);
                playBig.setBackgroundResource(R.drawable.stop);
                setSongTitle(i);
                /* start */
                mp.start();




            }else {
                /* release mediaplayer */
                mp.release();
            }

        }
        else
        {
            /* release mediaplayer */
            mp.release();
        }
    }


    public void doStuff() {
        // handle button click event here


        DailyActivitiesFragment dailyActivitiesFragment = new DailyActivitiesFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.topLayout,dailyActivitiesFragment,dailyActivitiesFragment.getTag())
                .commit();
    }


    private void setSongTitle(int position){
        songNameBig.setText(datumArrayList.get(position).getCname());
        songNameSmall.setText(datumArrayList.get(position).getCname());
        songDesBig.setText(datumArrayList.get(position).getCdescription());
    }
}