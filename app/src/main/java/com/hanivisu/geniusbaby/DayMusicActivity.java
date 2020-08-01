package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.hanivisu.geniusbaby.Adapter.DayListAdapter;
import com.hanivisu.geniusbaby.Models.MusicListModel.Datum;
import com.hanivisu.geniusbaby.Models.MusicListModel.MusicListModel;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayMusicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DayListAdapter adapter;
    ArrayList<Datum> datumArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_music);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Music - List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        getSongsList();

        adapter = new DayListAdapter(DayMusicActivity.this,datumArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }


    private void getSongsList(){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        String day = SharedPrefManager.get_mInstance(getApplicationContext()).getDay();
        String week = SharedPrefManager.get_mInstance(getApplicationContext()).getWeek();
        String session = SharedPrefManager.get_mInstance(getApplicationContext()).getSession();

        Log.e("shiva",week+"---"+day+"---"+session);



        Call<MusicListModel> call = RetrofitClient.getmInstance().getApi().getSongs(day,week,session);
        call.enqueue(new Callback<MusicListModel>() {
            @Override
            public void onResponse(Call<MusicListModel> call, Response<MusicListModel> response) {
                progressDialog.dismiss();
                MusicListModel musicListModel = response.body();

                if (musicListModel != null){
                    if (musicListModel.getStatus()){

                        datumArrayList.addAll(musicListModel.getData());
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<MusicListModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


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
