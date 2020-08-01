package com.hanivisu.geniusbaby.Fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hanivisu.geniusbaby.HomeActivity;
import com.hanivisu.geniusbaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    Button payAndPauseBtn,backBtn;
    MediaPlayer mp;
    Boolean play_state = false;


    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

//        payAndPauseBtn = view.findViewById(R.id.playAndPause);
//        backBtn = view.findViewById(R.id.backButton);

        mp = MediaPlayer.create(getContext(), Uri.parse("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3"));
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        payAndPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mp.isPlaying()) {
                    // Stopping
                    mp.start();
                    payAndPauseBtn.setBackgroundResource(R.drawable.stop);

                } else {
                    // Playing
                    mp.pause();
                    payAndPauseBtn.setBackgroundResource(R.drawable.play);
                }


            }
        });

//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((HomeActivity)getActivity()).hideBottomView();
//            }
//        });





    }
}
