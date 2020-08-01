package com.hanivisu.geniusbaby.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanivisu.geniusbaby.DailyActivity;
import com.hanivisu.geniusbaby.DayMusicActivity;
import com.hanivisu.geniusbaby.R;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import java.util.List;

public class WeekDayListAdapter extends RecyclerView.Adapter<WeekDayListAdapter.WeekDayViewHolder> {


    private Context context;
    private List<String> stringList;

    public WeekDayListAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public WeekDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_day_item,parent,false);
        return new WeekDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekDayViewHolder holder, int position) {

        String string = "Day "+(position+1);
        String nameStr = "0"+(position+1)+"/12/2019";

        holder.dayCount.setText(string);
        holder.dayName.setText(nameStr);

        final String weekDay = String.valueOf(position+1);



        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.get_mInstance(context).setDay(weekDay);
                Intent intent = new Intent(context, DailyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class WeekDayViewHolder extends RecyclerView.ViewHolder{

        CardView cardItem;
        TextView dayCount,dayName;

        public WeekDayViewHolder(@NonNull View itemView) {
            super(itemView);

            cardItem = itemView.findViewById(R.id.cardItem);
            dayCount = itemView.findViewById(R.id.dayCount);
            dayName = itemView.findViewById(R.id.dayName);
        }
    }
}
