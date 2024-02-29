package com.example.cmo_lms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.adapters.Search_name_data_Adapter;
import com.example.cmo_lms.model.Cmo_Lms_Summary_ResponseModel;
import com.example.cmo_lms.model.SearchResponseModel;
import com.example.cmo_lms.services.ApiService;
import com.example.cmo_lms.R;
import com.example.cmo_lms.services.RetrofitClient;
import com.example.cmo_lms.Utils.JsonUtils;
import com.example.cmo_lms.adapters.RecycleAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Details_Cmo_Lms_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView, textView1, txt_view1, textView2, textView3, txt_view4, txt_view5, txt_view6, txt_view7, pdf_txt_btn;
    ImageView imageView, imageView1, imageView2, imageView3, search_img, pdf_img;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    EditText edt_txt;
    RecycleAdapter adapter;
    CardView cardViewTr, cardViewTd, cardViewTp, search_cv;
    NestedScrollView nestedScrollView;
    Context context = this;
    Map<String, Map<String, Integer>> innerDataMap;
    String[] nameList;
    Button department_btn, subject_btn, constituency_btn, district_btn, Log_out_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cmo_lms);

        imageView = findViewById(R.id.imageView1);
        imageView1 = findViewById(R.id.imageView2);
        textView = findViewById(R.id.cm_name);
        textView1 = findViewById(R.id.cm_state);
        cardViewTr = findViewById(R.id.cv_tr);
        imageView2 = findViewById(R.id.imageView3);
        edt_txt = findViewById(R.id.search_txt);
        txt_view1 = findViewById(R.id.txt_tr);
        cardViewTd = findViewById(R.id.cv_td);
        imageView3 = findViewById(R.id.cm_img);
        textView2 = findViewById(R.id.txt_td);
        textView3 = findViewById(R.id.txt_tp);
        cardViewTp = findViewById(R.id.cv_tp);
        search_img = findViewById(R.id.search_engin);
        txt_view4 = findViewById(R.id.tr_number);
        txt_view5 = findViewById(R.id.td_number);
        txt_view6 = findViewById(R.id.tp_number);
        linearLayout1 = findViewById(R.id.lin_lay_4);
        txt_view7 = findViewById(R.id.cmo_lms_summary);
        linearLayout2 = findViewById(R.id.lin_lay_5);
        pdf_img = findViewById(R.id.file_img_pdf);
        pdf_txt_btn = findViewById(R.id.pdf_txt_btn);
        recyclerView = findViewById(R.id.recyclerView);
        nestedScrollView = findViewById(R.id.nest_scroll_view);
        linearLayout3 = findViewById(R.id.btn_lin_lay_1);
        linearLayout4 = findViewById(R.id.btn_lin_lay_2);
        search_cv = findViewById(R.id.search_cv);
        subject_btn = findViewById(R.id.subject_btn);
        department_btn = findViewById(R.id.department_btn);
        district_btn = findViewById(R.id.district_btn);
        constituency_btn = findViewById(R.id.constituency_btn);
        Log_out_Btn = findViewById(R.id.id_log_out);

        startSequentialAnimationForCardView();

        ObjectAnimator bounceAnimator = ObjectAnimator.ofFloat(search_img, "translationY", 0f, -50f, 0f);
        bounceAnimator.setDuration(2000);
        bounceAnimator.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(bounceAnimator);
        animatorSet.start();

        // Call AsyncTask to initiate network call
        new SummaryReportAsyncTask().execute();

        search_img.setOnClickListener(v -> edt_txt.requestFocus());

        edt_txt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                edt_txt.setEnabled(false);
                performSearch(edt_txt.getText().toString().trim());
                return true;
            }
            return false;
        });

        Log_out_Btn.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged_in", false);
            editor.apply();

            Intent intent = new Intent(context, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        pdf_img.setOnClickListener(v -> JsonUtils.generatePdf_summary(innerDataMap, context));

        pdf_txt_btn.setOnClickListener(v -> JsonUtils.generatePdf_summary(innerDataMap, context));

        subject_btn.setOnClickListener(v -> moveToSubjectWiseActivity());

        district_btn.setOnClickListener(V -> moveToDistrictWiseActivity());

        constituency_btn.setOnClickListener(v -> moveToConstituencyWiseActivity());

        department_btn.setOnClickListener(v -> moveToDepartmentWiseActivity());

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

    private void moveToDepartmentWiseActivity() {
        startActivity(new Intent(this, DepartmentWiseActivity.class));
    }

    private void moveToConstituencyWiseActivity() {
        startActivity(new Intent(this, ConstituencyWiseActivity.class));
    }

    private void moveToDistrictWiseActivity() {
        startActivity(new Intent(this, DistrictWiseActivity.class));
    }

    private void moveToSubjectWiseActivity() {
        startActivity(new Intent(this, SubjectWiseActivity.class));
    }

    private void performSearch(String query) {

        if (query.matches("[a-zA-Z]+")) {
            Toast.makeText(context, "search clicked for name : " + query, Toast.LENGTH_SHORT).show();

            Retrofit retrofit_name = RetrofitClient.getClient();
            ApiService apiService = retrofit_name.create(ApiService.class);

            Call<List<SearchResponseModel>> call_by_name = apiService.searchByName(query);
            Log.d("Request name", "URL: " + call_by_name.request().url());
            Log.d("Request name", "Method: " + call_by_name.request().method());
            Log.d("Request name", "Full request " + call_by_name.request());

            call_by_name.enqueue(new Callback<List<SearchResponseModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<SearchResponseModel>> call, @NonNull Response<List<SearchResponseModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(response.body());
                        Log.d("JSON String of name search data:", jsonString);
                        if (jsonString.equals("[]")) {
                            Toast.makeText(context, "Don't have details of the Given Name " + query, Toast.LENGTH_SHORT).show();
                        } else {
                            List<SearchResponseModel> dataList_of_searchName = new Gson().fromJson(jsonString, new TypeToken<List<SearchResponseModel>>() {
                            }.getType());
                            showCustomDialogOfSearchByName(dataList_of_searchName);
                        }
                    } else {
                        Toast.makeText(context, "Search name response failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<SearchResponseModel>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Invalid Search Input", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (query.matches("^[A-Za-z]{3}/[A-Za-z]{3}/\\d{7}/\\d{4}$")) {
            Toast.makeText(context, "search clicked for ref no : " + query, Toast.LENGTH_SHORT).show();

            Retrofit retrofit_name = RetrofitClient.getClient();
            ApiService apiService = retrofit_name.create(ApiService.class);

            Call<List<SearchResponseModel>> call_by_name = apiService.searchByName(query);
            Log.d("Request refno", "URL: " + call_by_name.request().url());
            Log.d("Request refno", "Method: " + call_by_name.request().method());
            Log.d("Request refno", "Full request " + call_by_name.request());

            call_by_name.enqueue(new Callback<List<SearchResponseModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<SearchResponseModel>> call, @NonNull Response<List<SearchResponseModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(response.body());
                        Log.d("JSON String of ref no search data:", jsonString);
                        if (jsonString.equals("[]")) {
                            Toast.makeText(context, "Don't have details of the Given ref no " + query, Toast.LENGTH_SHORT).show();
                        } else {
                            List<SearchResponseModel> dataList_of_searchName = new Gson().fromJson(jsonString, new TypeToken<List<SearchResponseModel>>() {
                            }.getType());
                            showCustomDialogOfSearchByName(dataList_of_searchName);
                        }
                    } else {
                        Toast.makeText(context, "Search ref no response failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<SearchResponseModel>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Invalid Search Input", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (query.matches("^[A-Za-z]{3}/[A-Za-z]{2}-[A-Za-z]{3}/\\d{7}/\\d{4}$")) {
            Toast.makeText(context, "search clicked for ref no : " + query, Toast.LENGTH_SHORT).show();

            Retrofit retrofit_name = RetrofitClient.getClient();
            ApiService apiService = retrofit_name.create(ApiService.class);

            Call<List<SearchResponseModel>> call_by_name = apiService.searchByName(query);
            Log.d("Request refno", "URL: " + call_by_name.request().url());
            Log.d("Request refno", "Method: " + call_by_name.request().method());
            Log.d("Request refno", "Full request " + call_by_name.request());

            call_by_name.enqueue(new Callback<List<SearchResponseModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<SearchResponseModel>> call, @NonNull Response<List<SearchResponseModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(response.body());
                        Log.d("JSON String of ref no search data:", jsonString);
                        if (jsonString.equals("[]")) {
                            Toast.makeText(context, "Don't have details of the Given ref no " + query, Toast.LENGTH_SHORT).show();
                        } else {
                            List<SearchResponseModel> dataList_of_searchName = new Gson().fromJson(jsonString, new TypeToken<List<SearchResponseModel>>() {
                            }.getType());
                            showCustomDialogOfSearchByName(dataList_of_searchName);
                        }
                    } else {
                        Toast.makeText(context, "Search ref no response failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<SearchResponseModel>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Invalid Search Input", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (query.matches("^[A-Za-z]{3}/[A-Za-z]{2}/\\d{7}/\\d{4}$")){
            Toast.makeText(context, "search clicked for ref no : " + query, Toast.LENGTH_SHORT).show();

            Retrofit retrofit_name = RetrofitClient.getClient();
            ApiService apiService = retrofit_name.create(ApiService.class);

            Call<List<SearchResponseModel>> call_by_name = apiService.searchByName(query);
            Log.d("Request refno", "URL: " + call_by_name.request().url());
            Log.d("Request refno", "Method: " + call_by_name.request().method());
            Log.d("Request refno", "Full request " + call_by_name.request());

            call_by_name.enqueue(new Callback<List<SearchResponseModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<SearchResponseModel>> call, @NonNull Response<List<SearchResponseModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(response.body());
                        Log.d("JSON String of ref no search data:", jsonString);
                        if (jsonString.equals("[]")) {
                            Toast.makeText(context, "Don't have details of the Given ref no " + query, Toast.LENGTH_SHORT).show();
                        } else {
                            List<SearchResponseModel> dataList_of_searchName = new Gson().fromJson(jsonString, new TypeToken<List<SearchResponseModel>>() {
                            }.getType());
                            showCustomDialogOfSearchByName(dataList_of_searchName);
                        }
                    } else {
                        Toast.makeText(context, "Search ref no response failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<SearchResponseModel>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Invalid Search Input", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(context, "Invalid Search Input", Toast.LENGTH_SHORT).show();
        }
        edt_txt.setEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void showCustomDialogOfSearchByName(List<SearchResponseModel> listOfSearchNameData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.search_name_custom_popup, null);

        RecyclerView search_name_data_recycler = dialogView.findViewById(R.id.search_name_custom_popup_recycler_view);
        TextView tv_total_entries = dialogView.findViewById(R.id.tv_total_entries);

        tv_total_entries.setText(getResources().getText(R.string.total_entries)+" "+listOfSearchNameData.size());
        Search_name_data_Adapter searchNameDataAdapter = new Search_name_data_Adapter(context, listOfSearchNameData);
        Log.d("search by name data", listOfSearchNameData.toString());
        search_name_data_recycler.setAdapter(searchNameDataAdapter);
        search_name_data_recycler.setLayoutManager(new LinearLayoutManager(this));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void startSequentialAnimationForCardView() {
        startFallingAnimation(cardViewTr);
    }

    private void startFallingAnimation(final CardView cardView) {
        Animation fallingAnimation = AnimationUtils.loadAnimation(context, R.anim.falling_down_anim);

        fallingAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setTranslationY(0);

                if (cardView == cardViewTr) {
                    cardViewTd.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    startFallingAnimation(cardViewTd);
                } else if (cardView == cardViewTd) {
                    cardViewTp.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    startFallingAnimation(cardViewTp);
                } else if (cardView == cardViewTp) {

                    resetCardPositions();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the animation on the cardView
        cardView.startAnimation(fallingAnimation);
    }

    private void resetCardPositions() {
        // Reset the Y positions of all card views
        cardViewTr.setTranslationY(0);
        cardViewTd.setTranslationY(0);
        cardViewTp.setTranslationY(0);
    }

    private class SummaryReportAsyncTask extends AsyncTask<Void, Void, Pair<String[], Map<String, Map<String, Integer>>>> {
        @Override
        protected Pair<String[], Map<String, Map<String, Integer>>> doInBackground(Void... voids) {
            return performSummaryReportApiCall();
        }

        @Override
        protected void onPostExecute(Pair<String[], Map<String, Map<String, Integer>>> alldata) {
            // Handle the result
            if (alldata != null) {
                handleSummaryReportResult(alldata);
            } else {
                // Handle failure
                Toast.makeText(Details_Cmo_Lms_Activity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleSummaryReportResult(Pair<String[], Map<String, Map<String, Integer>>> alldata) {
        // Handle data
        if (alldata != null) {
            nameList = alldata.first;
            innerDataMap = alldata.second;

            if (innerDataMap != null) {
                int totalPendingCount = 0;
                int totalClosedCount = 0;
                int totalCount = 0;

                for (Map<String, Integer> countsMap : innerDataMap.values()) {
                    if (countsMap != null) {
                        totalPendingCount += countsMap.getOrDefault("Pending Count", 0);
                        totalClosedCount += countsMap.getOrDefault("Closed Count", 0);
                        totalCount += countsMap.getOrDefault("Count", 0);
                    }
                }

                txt_view4.setText(String.valueOf(totalCount));
                txt_view5.setText(String.valueOf(totalClosedCount));
                txt_view6.setText(String.valueOf(totalPendingCount));

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecycleAdapter(nameList, innerDataMap, context);
                recyclerView.setAdapter(adapter);
            } else {
                // Handle the case where innerDataMap is null
                Toast.makeText(this, "Data is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where alldata is null
            Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
        }
    }


    private Pair<String[], Map<String, Map<String, Integer>>> performSummaryReportApiCall() {
        Retrofit retrofit_cmolms = RetrofitClient.getClient();
        ApiService apiService = retrofit_cmolms.create(ApiService.class);

        Call<List<Cmo_Lms_Summary_ResponseModel>> call_cmolms = apiService.getData();

        try {
            Response<List<Cmo_Lms_Summary_ResponseModel>> response = call_cmolms.execute();

            if (response.isSuccessful() && response.body() != null) {
                String jsonString = new Gson().toJson(response.body());
                Log.e("Api call map data", jsonString);
                Pair<String[], Map<String, Map<String, Integer>>> alldata = JsonUtils.convertJsonToMap(jsonString);
                Log.e("Api call all data", alldata.toString());

                return alldata;
            } else {
                Log.e("Unsuccessful Response", response.errorBody().toString());
            }
        } catch (IOException e) {
            Log.e("API Call", "Error: " + e.getMessage());
        }

        return null;
    }
}