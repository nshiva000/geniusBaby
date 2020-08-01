package com.hanivisu.geniusbaby.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanivisu.geniusbaby.MediaPlayerActivity;
import com.hanivisu.geniusbaby.Models.MusicListModel.Datum;
import com.hanivisu.geniusbaby.R;

import java.util.ArrayList;
import java.util.List;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.DayListViewHolder> {

    private Context context;
    private ArrayList<Datum> datumList;

    public DayListAdapter(Context context, ArrayList<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public DayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_list_item,parent,false);
        return new DayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayListViewHolder holder, final int position) {

        final Datum datum = datumList.get(position);
        holder.songDuration.setText(datum.getCduration());
        holder.songName.setText(datum.getCname());
        holder.songDes.setText(datum.getCdescription());
        final String count = "0"+(position+1);
        holder.songCount.setText(count);
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, MediaPlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",datum.getCname());
                intent.putExtra("file",datum.getCfile());
                intent.putExtra("des",datum.getCdescription());
                intent.putExtra("position",position);
                intent.putExtra("key",datumList);


                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class DayListViewHolder extends RecyclerView.ViewHolder{

        LinearLayout cardItem;
        TextView songCount,songName,songDes,songDuration;

        public DayListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardItem = itemView.findViewById(R.id.cardItem);
            songCount = itemView.findViewById(R.id.songCount);
            songName = itemView.findViewById(R.id.songName);
            songDes = itemView.findViewById(R.id.songDes);
            songDuration = itemView.findViewById(R.id.songDuration);
        }
    }
}
