package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hanivisu.geniusbaby.Models.UpdateModel;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    CardView submit;
    EditText otpEditText;

    Integer otp_txt;
    String uId_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = findViewById(R.id.otpEditText);
        submit = findViewById(R.id.submit_btn);

        otp_txt =  SharedPrefManager.get_mInstance(OtpActivity.this).getOtp();
        uId_txt =  SharedPrefManager.get_mInstance(OtpActivity.this).getId();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Otp");
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(otpEditText.getText().toString().trim())){
                    otpEditText.setError("required");
                    otpEditText.requestFocus();
                    return;
                }

                if (otp_txt != 0){
                    if (otp_txt.toString().equals(otpEditText.getText().toString().trim())){

                        if (!uId_txt.equals("")){
                            activate_user(uId_txt);
                        }
                    }else {
                        toast_msg("Otp does not match");
                    }
                }
            }
        });


    }


    private void activate_user(String id){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait ...");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().activate_user(id);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();
                UpdateModel updateModel = response.body();

                if (updateModel != null){

                    if (updateModel.getStatus()){
                        toast_msg("Account Activated successfully, Please Login");
                        Intent intent = new Intent(OtpActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }else {
                    toast_msg("null response");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();

                toast_msg(t.getMessage());

            }
        });

    }

    private void toast_msg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }


}
