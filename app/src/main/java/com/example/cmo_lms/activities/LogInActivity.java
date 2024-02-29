package com.example.cmo_lms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.model.LoginResponseModel;
import com.example.cmo_lms.services.ApiService;
import com.example.cmo_lms.services.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LogInActivity extends AppCompatActivity {
    TextView textView, txt_info, txt_Name, txt_pwd;
    TextInputLayout textInputLayout_user_name, textInputLayout_paswd_lay;
    TextInputEditText textInputEditText_user_name, textInputEditText_paswd;
    Button signIn_btn;
    View view_underline;
    ImageView img_logo;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        textView = findViewById(R.id.header);
        view_underline = findViewById(R.id.underline);
        img_logo = findViewById(R.id.logo_);
        txt_info = findViewById(R.id.info);
        txt_Name = findViewById(R.id.user_name);
        txt_pwd = findViewById(R.id.paswd);
        textInputLayout_user_name = findViewById(R.id.myTextInputLayout_user_name);
        textInputLayout_paswd_lay = findViewById(R.id.myTextInputLayout_paswd);
        textInputEditText_user_name = findViewById(R.id.my_editText_user_name);
        textInputEditText_paswd = findViewById(R.id.my_editText_password);
        signIn_btn = findViewById(R.id.sign_in_btn);

        String stringBuf = getString(R.string.c_m_o) + " " + getString(R.string.l_m_s);
        txt_info.setText(stringBuf);


        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (isLoggedIn) {
            moveToVerifyFragment();
            return; // Return after moving to Details_Cmo_Lms_Activity
        }

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputEditText_user_name.getText() == null || textInputEditText_user_name.getText().toString().isEmpty()) {
                    textInputLayout_user_name.setErrorIconDrawable(null);
                    textInputLayout_user_name.setError("Please Enter Username.");

                } else if (textInputEditText_paswd.getText() == null || textInputEditText_paswd.getText().toString().isEmpty()) {
                    textInputLayout_user_name.setError(null);
                    textInputLayout_paswd_lay.setErrorIconDrawable(null);
                    textInputLayout_paswd_lay.setError("Please Enter Password.");
                } else if (isValidPassword(String.valueOf(textInputEditText_paswd.getText()))) {
                    textInputLayout_paswd_lay.setErrorIconDrawable(null);
                    textInputLayout_paswd_lay.setError("Please Enter Valid Password.");
                } else {
                    username = textInputEditText_user_name.getText().toString().trim();
                    password = textInputEditText_paswd.getText().toString().trim();
                     apiCallForLogin();
                }

            }
        });

        textInputEditText_paswd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (textInputEditText_user_name.getText() == null || textInputEditText_user_name.getText().toString().isEmpty()) {
                        textInputLayout_user_name.setErrorIconDrawable(null);
                        textInputLayout_user_name.setError("Please Enter Username.");

                    } else if (textInputEditText_paswd.getText() == null || textInputEditText_paswd.getText().toString().isEmpty()) {
                        textInputLayout_user_name.setError(null);
                        textInputLayout_paswd_lay.setErrorIconDrawable(null);
                        textInputLayout_paswd_lay.setError("Please Enter Password.");
                    } else if (isValidPassword(String.valueOf(textInputEditText_paswd.getText()))) {
                        textInputLayout_paswd_lay.setErrorIconDrawable(null);
                        textInputLayout_paswd_lay.setError("Please Enter Valid Password.");
                    } else {
                        username = textInputEditText_user_name.getText().toString().trim();
                        password = textInputEditText_paswd.getText().toString().trim();
                        apiCallForLogin();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!InternetUtil.isNetworkAvailable(this)) {
            Intent dIntent = new Intent(this, NoInternetActivity.class);
            startActivity(dIntent);
            finish();
        }
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }

    private void apiCallForLogin() {
        Retrofit retrofit = RetrofitClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
//         Make API call
        Call<LoginResponseModel> call = apiService.loginUser(username, password);
        Log.e("user:", username);
        Log.e("pwd:", password);
        Log.d("Request", "URL: " + call.request().url());
        Log.d("Request", "Method: " + call.request().method());
        Log.d("Request", "Full request " + call.request());
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                if (response.isSuccessful()) {
                    LoginResponseModel responseBodyData = response.body();
                    if (responseBodyData != null) {
                        Log.e("Login data : ", responseBodyData.toString());
                        if (!(responseBodyData.getCallcenteruser_id() == 0)) {
                            Toast.makeText(LogInActivity.this, "Login Successful. ", Toast.LENGTH_SHORT).show();
                                // Store login state in SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("is_logged_in", true);
                                editor.apply();
                                moveToVerifyFragment();
                        } else {
                            Toast.makeText(LogInActivity.this, "Please enter valid User Name and Password. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LogInActivity.this, "Please enter valid User Name and Password. ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogInActivity.this, "Login failed Please Retry. ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                Log.e("API Error", "Failed to make API call", t);
                Toast.makeText(LogInActivity.this, "Something went Wrong please try again. ", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void moveToVerifyFragment() {
        Intent intent = new Intent(this, Details_Cmo_Lms_Activity.class);
        startActivity(intent);
        finish();
    }
}