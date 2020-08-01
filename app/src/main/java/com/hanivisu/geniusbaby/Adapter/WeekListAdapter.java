package com.hanivisu.geniusbaby.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanivisu.geniusbaby.R;
import com.hanivisu.geniusbaby.WeekDaysActivity;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import java.util.List;

public class WeekListAdapter extends RecyclerView.Adapter<WeekListAdapter.WeekListViewHolder> {

    private Context context;
    private List<String> stringList;

    public WeekListAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public WeekListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_list_item,parent,false);
        return new WeekListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekListViewHolder holder, int position) {

        String string = "Week "+stringList.get(position);

        holder.weekName.setText(string);

        final String week = stringList.get(position);
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WeekDaysActivity.class);
                Log.e("shiva",week+"-----");
                SharedPrefManager.get_mInstance(context).setWeek(week);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class WeekListViewHolder extends RecyclerView.ViewHolder{

        CardView cardItem;
        TextView weekName;

        public WeekListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardItem = itemView.findViewById(R.id.cardItem);
            weekName = itemView.findViewById(R.id.weekName);
        }
    }
}
