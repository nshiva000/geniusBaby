package com.hanivisu.geniusbaby;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;




public class MediaPlayerService extends Service {

    MediaPlayer mp;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       super.onStartCommand(intent, flags, startId);


        mp = MediaPlayer.create(this, Uri.parse("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3"));
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        mp.start();



        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
