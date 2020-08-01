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

import com.hanivisu.geniusbaby.Models.SignUpModel.SignUpModel;
import com.hanivisu.geniusbaby.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView signInText;
    EditText name,email,phone,password;
    ImageView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);



        signInText = findViewById(R.id.signInText);

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    name.setError("Please enter username");
                    name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone.getText().toString().trim())){
                    phone.setError("Please enter Phone Number");
                    phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString().trim())){
                    password.setError("Please enter Password");
                    password.requestFocus();
                    return;
                }

                UserSignUp(name.getText().toString().trim(),email.getText().toString().trim(),phone.getText().toString().trim(),password.getText().toString().trim());

            }
        });


    }


    private void UserSignUp(String name, String email, final String phone, final String password){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<SignUpModel> call = RetrofitClient.getmInstance().getApi().user_signUp(name, email, phone, password);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                progressDialog.dismiss();
                SignUpModel updateModel = response.body();

                if (updateModel != null){

                    if (updateModel.getStatus()){
                        toast_msg("Account Created SuccessFully");

                        SharedPrefManager.get_mInstance(getApplicationContext()).setId(updateModel.getUserid());
                        SharedPrefManager.get_mInstance(getApplicationContext()).setOtp(updateModel.getOtp());
                        Intent intent = new Intent(SignUpActivity.this,OtpActivity.class);
                        startActivity(intent);
                    }else {
                        toast_msg("Something is wrong");
                    }

                }else {
                    toast_msg("Server returned null data");
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                progressDialog.dismiss();
                toast_msg(t.getMessage()+"--");
            }
        });

    }


    private void toast_msg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
