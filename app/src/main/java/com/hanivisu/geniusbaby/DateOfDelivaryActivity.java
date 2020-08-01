package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.hanivisu.geniusbaby.Models.DODModel.DODModel;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateOfDelivaryActivity extends AppCompatActivity {

    CardView toDateCardView;
    TextView dateTextView;
    String date_txt;
    Button submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_delivary);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Date Of Delivery");
        }


        toDateCardView = findViewById(R.id.todateCardView);
        dateTextView = findViewById(R.id.dateTextView);
        submit = findViewById(R.id.submit);

        toDateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker;
                picker = new DatePickerDialog(DateOfDelivaryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                date_txt = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                dateTextView.setText(date_txt);
                            }
                        }, year, month, day);
                picker.show();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = SharedPrefManager.get_mInstance(DateOfDelivaryActivity.this).getId();
                Log.e("shiva",date_txt+"---"+userId);

                if (date_txt == null){
                    toast_msg("Please select Date");
                    return;
                }

                sendData(date_txt,userId);


            }
        });
    }



    private void sendData(String dod,String userId){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait..");
        progressDialog.show();

        Call<DODModel> call = RetrofitClient.getmInstance().getApi().sendDOD(dod,userId);
        call.enqueue(new Callback<DODModel>() {
            @Override
            public void onResponse(Call<DODModel> call, Response<DODModel> response) {
                progressDialog.dismiss();
                DODModel dodModel = response.body();

                if (dodModel != null){
                    if (dodModel.getStatus()){


                        Intent intent = new Intent(DateOfDelivaryActivity.this,WeeksActivity.class);
                        intent.putExtra("week",dodModel.getWeek());
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<DODModel> call, Throwable t) {

            }
        });
    }

    private void toast_msg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
