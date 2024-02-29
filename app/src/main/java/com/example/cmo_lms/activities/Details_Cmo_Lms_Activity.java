package com.example.cmo_lms.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.Utils.JsonUtils;
import com.example.cmo_lms.Utils.LanguageUtil;
import com.example.cmo_lms.adapters.RecycleAdapter;
import com.example.cmo_lms.adapters.Search_name_data_Adapter;
import com.example.cmo_lms.model.Cmo_Lms_Summary_ResponseModel;
import com.example.cmo_lms.model.SearchResponseModel;
import com.example.cmo_lms.services.ApiService;
import com.example.cmo_lms.services.RetrofitClient;
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
    TextView cm_name_tv, cm_state_tv, txt_tr, txt_td, txt_tp, txt_tr_no, txt_td_no, txt_tp_no, txt_summary_tv, pdf_txt_btn, head_govr_name;
    ImageView background_img, background_img_clr, logo_img, cm_img, search_img, pdf_img;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    EditText search_edt_txt;
    RecycleAdapter adapter;
    CardView cardViewTr, cardViewTd, cardViewTp, search_cv;
    NestedScrollView nestedScrollView;
    Context context = this;
    Map<String, Map<String, Integer>> innerDataMap;
    String[] nameList;
    Dialog loadingDialog;
    Button Log_out_Btn, lang_change_btn, lang_change_btn_kn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cmo_lms);

        background_img = findViewById(R.id.imageView1);
        background_img_clr = findViewById(R.id.imageView2);
        head_govr_name = findViewById(R.id.government_txt);
        cm_name_tv = findViewById(R.id.cm_name);
        cm_state_tv = findViewById(R.id.cm_state);
        cardViewTr = findViewById(R.id.cv_tr);
        logo_img = findViewById(R.id.imageView3);
        search_edt_txt = findViewById(R.id.search_txt);
        txt_tr = findViewById(R.id.txt_tr);
        cardViewTd = findViewById(R.id.cv_td);
        cm_img = findViewById(R.id.cm_img);
        txt_td = findViewById(R.id.txt_td);
        txt_tp = findViewById(R.id.txt_tp);
        cardViewTp = findViewById(R.id.cv_tp);
        search_img = findViewById(R.id.search_engin);
        txt_tr_no = findViewById(R.id.tr_number);
        txt_td_no = findViewById(R.id.td_number);
        txt_tp_no = findViewById(R.id.tp_number);
        linearLayout1 = findViewById(R.id.lin_lay_4);
        txt_summary_tv = findViewById(R.id.cmo_lms_summary);
        linearLayout2 = findViewById(R.id.lin_lay_5);
        pdf_img = findViewById(R.id.file_img_pdf);
        pdf_txt_btn = findViewById(R.id.pdf_txt_btn);
        recyclerView = findViewById(R.id.recyclerView);
        nestedScrollView = findViewById(R.id.nest_scroll_view);
        linearLayout3 = findViewById(R.id.btn_lin_lay_1);
        linearLayout4 = findViewById(R.id.btn_lin_lay_2);
        search_cv = findViewById(R.id.search_cv);
        Log_out_Btn = findViewById(R.id.id_log_out);
        lang_change_btn = findViewById(R.id.id_lang_change_en);
        lang_change_btn_kn = findViewById(R.id.id_lang_change_kn);

        startSequentialAnimationForCardview();

        ObjectAnimator bounceAnimator = ObjectAnimator.ofFloat(search_img, "translationY", 0f, -50f, 0f);
        bounceAnimator.setDuration(2000);
        bounceAnimator.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(bounceAnimator);
        animatorSet.start();

        // Call AsyncTask to initiate network call
        new SummaryReportAsyncTask().execute();

        search_img.setOnClickListener(v -> search_edt_txt.requestFocus());

        search_edt_txt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                search_edt_txt.setEnabled(false);
                showCustomLoadingDialog();
                performSearch(search_edt_txt.getText().toString().trim());
                new Handler().postDelayed(this::dismissCustomLoadingDialog, 2000);
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


        String Selected_lang = LanguageUtil.getCurrentLanguage();

        if (Selected_lang.equals("en")) {
            lang_change_btn.setVisibility(View.GONE);
            lang_change_btn_kn.setVisibility(View.VISIBLE);
        } else {
            lang_change_btn_kn.setVisibility(View.GONE);
            lang_change_btn.setVisibility(View.VISIBLE);
        }

        lang_change_btn.setOnClickListener(v -> {
            setLanguage("en");
        });

        lang_change_btn_kn.setOnClickListener(v -> {
            setLanguage("kn");
        });

    }

    public  void setLanguage(String languageCode) {
        if (context != null) {
            LanguageUtil.setAppLocale(context, languageCode);
            recreate();

            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            android.content.res.Configuration configuration = resources.getConfiguration();

            resources.updateConfiguration(configuration, displayMetrics);
            context.createConfigurationContext(configuration);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showCustomLoadingDialog() {

        loadingDialog = new Dialog(this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.custom_loading_dailogue);
        loadingDialog.setCancelable(false);

        TextView loadingText = loadingDialog.findViewById(R.id.loadingText);
        loadingText.setText(getResources().getText(R.string.loading));

        loadingDialog.show();
    }

    private void dismissCustomLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
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

        } else if (query.matches("^[A-Za-z]{3}/[A-Za-z]{2}/\\d{7}/\\d{4}$")) {
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

        } else {
            Toast.makeText(context, "Invalid Search Input", Toast.LENGTH_SHORT).show();
        }
        search_edt_txt.setEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void showCustomDialogOfSearchByName(List<SearchResponseModel> listOfSearchNameData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.search_name_custom_popup, null);

        RecyclerView search_name_data_recycler = dialogView.findViewById(R.id.search_name_custom_popup_recycler_view);
        TextView tv_total_entries = dialogView.findViewById(R.id.tv_total_entries);

        tv_total_entries.setText(getResources().getText(R.string.total_entries) + " " + listOfSearchNameData.size());
        Search_name_data_Adapter searchNameDataAdapter = new Search_name_data_Adapter(context, listOfSearchNameData);
        Log.d("search by name data", listOfSearchNameData.size() + listOfSearchNameData.toString());
        search_name_data_recycler.setAdapter(searchNameDataAdapter);
        search_name_data_recycler.setLayoutManager(new LinearLayoutManager(this));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void startSequentialAnimationForCardview() {
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
                    txt_td.setVisibility(View.VISIBLE);
                    startFallingAnimation(cardViewTd);
                } else if (cardView == cardViewTd) {
                    cardViewTp.setVisibility(View.VISIBLE);
                    txt_tp.setVisibility(View.VISIBLE);
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
                    if (LanguageUtil.getCurrentLanguage().equals("en")) {
                        if (countsMap != null) {
                            totalPendingCount += countsMap.getOrDefault("Pending Count", 0);
                            totalClosedCount += countsMap.getOrDefault("Closed Count", 0);
                            totalCount += countsMap.getOrDefault("Count", 0);
                        }
                    } else {
                        if (countsMap != null) {
                            totalPendingCount += countsMap.getOrDefault("ಬಾಕಿಯಿರುವ ಎಣಿಕೆ", 0);
                            totalClosedCount += countsMap.getOrDefault("ಮುಚ್ಚಿದ ಎಣಿಕೆ", 0);
                            totalCount += countsMap.getOrDefault("ಎಣಿಕೆ", 0);
                        }
                    }
                }

                txt_tr_no.setText(String.valueOf(totalCount));
                txt_td_no.setText(String.valueOf(totalClosedCount));
                txt_tp_no.setText(String.valueOf(totalPendingCount));

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
}