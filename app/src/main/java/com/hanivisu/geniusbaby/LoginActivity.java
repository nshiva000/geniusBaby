package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanivisu.geniusbaby.Models.LoginModel.LoginModel;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView signUpText;
    EditText phone,password;
    ImageView submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        signUpText = findViewById(R.id.signUpText);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(phone.getText().toString().trim())){
                    phone.setError("required");
                    phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString().trim())){
                    password.setError("required");
                    password.requestFocus();
                    return;
                }


                UserLogin(phone.getText().toString().trim(),password.getText().toString().trim());
            }
        });



        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private void UserLogin(String phone,String password){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<LoginModel> call = RetrofitClient.getmInstance().getApi().user_login(phone,password);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();

                LoginModel loginModel = response.body();

                if (loginModel != null){
                    if (loginModel.getStatus()){
                        toast_msg("Login Successfull");
                        SharedPrefManager.get_mInstance(LoginActivity.this).saveUser(loginModel);
                        Intent intent = new Intent(LoginActivity.this,DateOfDelivaryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        toast_msg("please check phone number or email");
                    }
                }else {
                    toast_msg("server returned null response");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void toast_msg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
