package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hanivisu.geniusbaby.Adapter.WeekListAdapter;

import java.util.ArrayList;

public class WeeksActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    WeekListAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    TextView weekName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weekName = findViewById(R.id.weekName);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Weeks");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int week = getIntent().getIntExtra("week",17);

        for (int i = 0; i <= 3; i++) {
            arrayList.add(String.valueOf(week));
            week++;
        }

        weekName.setText("WEEK ("+getIntent().getIntExtra("week",17)+"-"+--week+") ");


        recyclerView = findViewById(R.id.recyclerView);

        adapter = new WeekListAdapter(WeeksActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
//        recyclerView.setLayoutManager(layoutManager);



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
