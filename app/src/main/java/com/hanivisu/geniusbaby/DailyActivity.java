package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hanivisu.geniusbaby.storage.SharedPrefManager;

public class DailyActivity extends AppCompatActivity {

    CardView morningCard,eveningCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Music");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        morningCard = findViewById(R.id.morningCard);
        eveningCard = findViewById(R.id.eveningCard);


        morningCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.get_mInstance(getApplicationContext()).setSession("m");
               moveToNxt();
            }
        });


        eveningCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.get_mInstance(getApplicationContext()).setSession("e");
              moveToNxt();
            }
        });
    }


    private void moveToNxt(){
        Intent intent = new Intent(DailyActivity.this,DayMusicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
