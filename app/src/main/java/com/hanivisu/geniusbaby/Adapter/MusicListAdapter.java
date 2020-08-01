package com.hanivisu.geniusbaby.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanivisu.geniusbaby.HomeActivity;
import com.hanivisu.geniusbaby.Models.MusicListModel.Datum;
import com.hanivisu.geniusbaby.R;

import java.io.IOException;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewholder> {


    private Context context;
    private List<Datum> datumList;

    public MusicListAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public MusicListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item,parent,false);
        return new MusicListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewholder holder, final int position) {

        Datum datum = datumList.get(position);

        holder.songName.setText(datum.getCname());



        holder.songItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((HomeActivity)context).playSong(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class MusicListViewholder extends RecyclerView.ViewHolder{

        CardView songItem;
        TextView songName,songDuration;

        public MusicListViewholder(@NonNull View itemView) {
            super(itemView);

            songItem = itemView.findViewById(R.id.song_item);
            songName = itemView.findViewById(R.id.song_name);
            songDuration = itemView.findViewById(R.id.songDuration);
        }
    }
}
