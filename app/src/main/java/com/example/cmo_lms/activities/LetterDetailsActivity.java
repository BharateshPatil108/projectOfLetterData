package com.example.cmo_lms.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.net.ParseException;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.Utils.JsonUtils;
import com.example.cmo_lms.Utils.LanguageUtil;
import com.example.cmo_lms.adapters.LetterDetailsEofficeAdapter;
import com.example.cmo_lms.adapters.LetterDetails_Adapter;
import com.example.cmo_lms.model.RepDetailsResponse;
import com.example.cmo_lms.model.SearchResponseModel;
import com.example.cmo_lms.model.Searchref_noResponseModel;
import com.example.cmo_lms.services.ApiService;
import com.example.cmo_lms.services.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LetterDetailsActivity extends AppCompatActivity {
    static RecyclerView recyclerView_name;
    static Map<String, String> dataMap = new LinkedHashMap<>();
    static Map<String, Integer> apiCallMap_searchIds = new LinkedHashMap<>();
    static RepDetailsResponse repDetails;
    ConstraintLayout letter_const_lay;
    Map<String, String> kannadaDataMap = new LinkedHashMap<>();
    RecyclerView recycleView_letter_eOffice;
    LinearLayout linearLayout;
    TextView pdfBtnTxt;
    ImageView pdfImg;
    Button button;
    Context context = this;
    String viewPdf;
    String fileName;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_details);

        letter_const_lay = findViewById(R.id.letter_constraint_1);
        recyclerView_name = findViewById(R.id.recycleView_letter_name);
        recycleView_letter_eOffice = findViewById(R.id.eOffice_recycleView_letter_name);
        linearLayout = findViewById(R.id.lin_lay_letter_2);
        pdfImg = findViewById(R.id.file_img_pdf_letter);
        pdfBtnTxt = findViewById(R.id.pdf_txt_btn_letter);
        button = findViewById(R.id.pdf_viewer);

        letter_const_lay.setVisibility(View.GONE);
        recyclerView_name.setVisibility(View.GONE);
        recycleView_letter_eOffice.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        pdfImg.setVisibility(View.GONE);
        pdfBtnTxt.setVisibility(View.GONE);
        button.setVisibility(View.GONE);

        showCustomLoadingDialog();

        new Handler().postDelayed(() -> {
            dismissCustomLoadingDialog();
            showData();
        }, 1500);

        // search Letter data below
        Intent intent = getIntent();
        SearchResponseModel searchNameResponseModel = (SearchResponseModel) intent.getSerializableExtra("searchNameResponseModel");

        if (searchNameResponseModel != null) {
            searchByNameData(searchNameResponseModel.getRgRef());

        } else {
            Log.e("In searchNameResponseModel ref no getting null", "LetterDetails activity");
        }

        pdfBtnTxt.setOnClickListener(v -> {
            String data = dataMap.get("Attachment :");
            if (data == null || data.isEmpty()) {
                Toast.makeText(context, "Letter path is not present.", Toast.LENGTH_SHORT).show();
            } else {
                if (LanguageUtil.getCurrentLanguage().equals("en")) {
                    JsonUtils.generatePdf(dataMap, context);
                } else {
                    JsonUtils.generatePdf(kannadaDataMap, context);
                }
            }
        });

        pdfImg.setOnClickListener(v -> {
            String data = dataMap.get("Attachment :");
            if (data == null || data.isEmpty()) {
                Toast.makeText(context, "Letter path is not present.", Toast.LENGTH_SHORT).show();
            } else {
                if (LanguageUtil.getCurrentLanguage().equals("en")) {
                    JsonUtils.generatePdf(dataMap, context);
                } else {
                    JsonUtils.generatePdf(kannadaDataMap, context);
                }
            }
        });

        button.setOnClickListener(v -> DownloadViewPdf(viewPdf));
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

    private void showData() {
        letter_const_lay.setVisibility(View.VISIBLE);
        recyclerView_name.setVisibility(View.VISIBLE);
        recycleView_letter_eOffice.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        pdfImg.setVisibility(View.VISIBLE);
        pdfBtnTxt.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    }

    private void DownloadViewPdf(String viewPdf_data) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ipgrs.karnataka.gov.in").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

        String[] pathSegments = viewPdf.trim().split("/");
        fileName = pathSegments[pathSegments.length - 1];

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call_ids = apiService.downloadFile(viewPdf_data.trim());

        Log.d("Request ids", "URL: " + call_ids.request().url());
        Log.d("Request ids", "Method: " + call_ids.request().method());
        Log.d("Request ids", "Full request " + call_ids.request());

        call_ids.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Start a background thread to save the file
                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            return writeResponseBodyToDisk(response.body());
                        }

                        @Override
                        protected void onPostExecute(Boolean success) {
                            if (success) {
                                // File saved successfully
                                Log.d("File Download", "File downloaded successfully");
                                Toast.makeText(context, "File downloaded successfully..", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failed to save file
                                Log.e("File Download", "Failed to download file");
                                Toast.makeText(context, "Failed to download file..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                } else {
                    Log.e("fetchRepDetails", "Unsuccessful response: " + response.message());
                    Toast.makeText(context, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("fetchRepDetails", "API call failed: " + t.getMessage());
                // Handle failure message or UI update here
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // Change the file location and name as needed
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                }
                MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, null);
                outputStream.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    private void searchByNameData(String searchLetterData) {

        Retrofit retrofit_refno = RetrofitClient.getClient();
        ApiService apiService = retrofit_refno.create(ApiService.class);

        Call<Searchref_noResponseModel> call_ref_no = apiService.searchByRef_no(searchLetterData);
        Log.d("Request refno", "URL: " + call_ref_no.request().url());
        Log.d("Request refno", "Method: " + call_ref_no.request().method());
        Log.d("Request refno", "Full request " + call_ref_no.request());
        call_ref_no.enqueue(new Callback<Searchref_noResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<Searchref_noResponseModel> call, @NonNull Response<Searchref_noResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(response.body());
                    Log.d("JSON String:", jsonString);
                    dataMap = JsonUtils.convertJsonStringToMap(jsonString);
                    Log.d("JSON String as a map:", dataMap.toString());
                    if (jsonString.equals("[]")) {
                        Toast.makeText(context, "Don't have Letter details of the Given Ref No " + searchLetterData, Toast.LENGTH_SHORT).show();
                    } else {

                        List<String> keysForApiCalls = Arrays.asList("Representative Griv Category :", "Forwarded Dept Id :", "Forwarded LineDepartment ID :", "Forwarded District ID :", "MLA Constituency :", "MP-Lok Sabha Constituency :", "Post Name :", "MP-Rajya Sabha Constituency :", "MLC Constituency :", "Ex MLC Constituency :");

                        if (!(dataMap.get("Attachment :") == null) || Objects.requireNonNull(dataMap.get("Attachment :")).isEmpty()) {
                            button.setVisibility(View.VISIBLE);
                            viewPdf = dataMap.get("Attachment :");

                            assert viewPdf != null;
                            Log.e("viewPdf_data", viewPdf);
                        }

                        for (String key : keysForApiCalls) {
                            String value = dataMap.get(key);
                            int intValue = value != null && !value.equals("null") ? Integer.parseInt(value) : 0;
                            apiCallMap_searchIds.put(key, intValue);
                        }
                        Log.e("apiCallMap_searchIds", apiCallMap_searchIds.toString());
                        fetchRepDetails(apiCallMap_searchIds);
                    }
                } else {
                    Toast.makeText(context, "Search ref no Letter data response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Searchref_noResponseModel> call, @NonNull Throwable t) {
                Log.e("API Call", "Error: " + t.getMessage());
            }
        });
    }

    public void fetchRepDetails(Map<String, Integer> idsData) {
        JsonObject requestBody = new JsonObject();

        requestBody.addProperty("rg_griv_category_id", idsData.get("Representative Griv Category :"));
        requestBody.addProperty("rg_forwarded_dept_id", idsData.get("Forwarded Dept Id :"));
        requestBody.addProperty("rg_line_department_id", idsData.get("Forwarded LineDepartment ID :"));
        requestBody.addProperty("mla_constituency", idsData.get("MLA Constituency :"));
        requestBody.addProperty("mpl_constituency", idsData.get("MP-Lok Sabha Constituency :"));
        requestBody.addProperty("mpr_constituency", idsData.get("MP-Rajya Sabha Constituency :"));
        requestBody.addProperty("mlc_constituency", idsData.get("MLC Constituency :"));
        requestBody.addProperty("ex_mlc_constituency", idsData.get("Ex MLC Constituency :"));
        requestBody.addProperty("rg_post_id", idsData.get("Post Name :"));

        Retrofit retrofit_ids = RetrofitClient.getClient();
        ApiService apiService = retrofit_ids.create(ApiService.class);

        // API call to fetch representative details
        Call<RepDetailsResponse> call_ids = apiService.searchByIds(requestBody);

        Log.d("Request ids", "URL: " + call_ids.request().url());
        Log.d("Request ids", "Method: " + call_ids.request().method());
        Log.d("Request ids", "Full request " + call_ids.request());

        call_ids.enqueue(new Callback<RepDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RepDetailsResponse> call, @NonNull Response<RepDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    repDetails = response.body();

                    Log.e("repDetails_ofIds", repDetails.toString());
                    Map<String, String> kannada_map = new LinkedHashMap<>();
                    Map<String, String> english_map = new LinkedHashMap<>();

                    String repName = repDetails.getRepName();
                    if (repName != null) {
                        String[] repNameParts = repName.split("/");
                        if (repNameParts.length >= 2) {
                            kannada_map.put("ಪ್ರತಿನಿಧಿ ಹೆಸರು :", repNameParts[0].trim());
                            english_map.put("Representative Name :", repNameParts[1].trim());
                        }
                    } else {
                        kannada_map.put("ಪ್ರತಿನಿಧಿ ಹೆಸರು :", null);
                        english_map.put("Representative Name :", null);
                    }

                    String cat_Name = repDetails.getCategoryName();
                    if (cat_Name != null) {
                        String[] catNameParts = cat_Name.split("/");
                        if (catNameParts.length >= 2) {
                            kannada_map.put("ವರ್ಗದ ಹೆಸರು :", catNameParts[0].trim());
                            english_map.put("Representative Griv Category :", catNameParts[1].trim());
                        }
                    } else {
                        kannada_map.put("ವರ್ಗದ ಹೆಸರು :", null);
                        english_map.put("Representative Griv Category :", null);
                    }

                    String const_Name = repDetails.getConName();
                    if (const_Name != null) {
                        String[] constNameParts = const_Name.split("/");
                        if (constNameParts.length >= 2) {
                            kannada_map.put("ಕ್ಷೇತ್ರದ ಹೆಸರು :", constNameParts[0].trim());
                            english_map.put("Constituency :", constNameParts[1].trim());
                        }
                    } else {
                        kannada_map.put("ಕ್ಷೇತ್ರದ ಹೆಸರು :", null);
                        english_map.put("Constituency :", null);
                    }

                    String dept_Name = repDetails.getDeptName();
                    if (dept_Name != null) {
                        String[] deptNameParts = dept_Name.split("/");
                        if (deptNameParts.length >= 2) {
                            kannada_map.put("ಇಲಾಖೆಯ ಹೆಸರು :", deptNameParts[0].trim());
                            english_map.put("Forwarded Dept Id :", deptNameParts[1].trim());
                        }
                    } else {
                        kannada_map.put("ಇಲಾಖೆಯ ಹೆಸರು :", null);
                        english_map.put("Forwarded Dept Id :", null);
                    }

                    String line_dept_Name = repDetails.getLineDepName();
                    if (line_dept_Name != null) {
                        String[] lineDeptNameParts = line_dept_Name.split("/");
                        if (lineDeptNameParts.length >= 2) {
                            kannada_map.put("ಸಾಲಿನ ಇಲಾಖೆಯ ಹೆಸರು :", lineDeptNameParts[0].trim());
                            english_map.put("Forwarded LineDepartment ID :", lineDeptNameParts[1].trim());
                        }
                    } else {
                        kannada_map.put("ಸಾಲಿನ ಇಲಾಖೆಯ ಹೆಸರು :", null);
                        english_map.put("Forwarded LineDepartment ID :", null);
                    }

                    if (LanguageUtil.getCurrentLanguage().equals("kn")) {

                        dataMap.put("Representative Name :", kannada_map.get("ಪ್ರತಿನಿಧಿ ಹೆಸರು :"));
                        dataMap.put("Representative Griv Category :", kannada_map.get("ವರ್ಗದ ಹೆಸರು :"));
                        dataMap.put("Forwarded Dept Id :", kannada_map.get("ಇಲಾಖೆಯ ಹೆಸರು :"));
                        dataMap.put("Forwarded LineDepartment ID :", kannada_map.get("ಸಾಲಿನ ಇಲಾಖೆಯ ಹೆಸರು :"));
                        dataMap.put("Post Name :", repDetails.getPostName());

                        if (Objects.equals(dataMap.get("Representative Type :"), "ಶಾಸಕರು - ವಿಧಾನ ಸಭೆ")) {
                            dataMap.put("MLA Constituency :", kannada_map.get("ಕ್ಷೇತ್ರದ ಹೆಸರು :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "ಲೋಕಸಭಾ ಸದಸ್ಯರು")) {
                            dataMap.put("MP-Lok Sabha Constituency :", kannada_map.get("ಕ್ಷೇತ್ರದ ಹೆಸರು :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "ಶಾಸಕರು - ವಿಧಾನ ಪರಿಷತ್‌")) {
                            dataMap.put("MLC Constituency :", kannada_map.get("ಕ್ಷೇತ್ರದ ಹೆಸರು :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರು")) {
                            dataMap.put("MP-Rajya Sabha Constituency :", kannada_map.get("ಕ್ಷೇತ್ರದ ಹೆಸರು :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "ಮಾಜಿ ಶಾಸಕರು – ವಿಧಾನ ಪರಿಷತ್ತು")) {
                            dataMap.put("Ex MLC Constituency :", kannada_map.get("ಕ್ಷೇತ್ರದ ಹೆಸರು :"));
                        } else {
                            dataMap.put("MLA Constituency :", kannada_map.get("ಕ್ಷೇತ್ರದ ಹೆಸರು :"));
                        }

                        Log.e("kannada data", kannada_map.toString());

                        Map<String, String> kannadaKeys = new LinkedHashMap<>();

                        kannadaKeys.put("Rg ID :", "ಆರ್ ಜಿ ಐಡಿ :");
                        kannadaKeys.put("Representative No :", "ಪ್ರತಿನಿಧಿ ಸಂಖ್ಯೆ :");
                        kannadaKeys.put("Date :", "ದಿನಾಂಕ :");
                        kannadaKeys.put("Letter No :", "ಪತ್ರದ ಸಂಖ್ಯೆ :");
                        kannadaKeys.put("Representative Name :", "ಪ್ರತಿನಿಧಿ ಹೆಸರು :");
                        kannadaKeys.put("Representative Mob :", "ಪ್ರತಿನಿಧಿ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ. :");
                        kannadaKeys.put("Representative Address :", "ಪತ್ರದಲ್ಲಿರುವಂತೆ ವಿಳಾಸ :");
                        kannadaKeys.put("Representative Griv Category :", "ಪ್ರತಿನಿಧಿ ಕುಂದುಕೊರತೆ ವರ್ಗ :");
                        kannadaKeys.put("Attachment :", "ಲಗತ್ತು :");
                        kannadaKeys.put("CM Note Path :", "ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳ ಟಿಪ್ಪಣಿ :");
                        kannadaKeys.put("Letter Description :", "ಪತ್ರದ/ಟಿಪ್ಪಣಿಯ ಸಂಕ್ಷಿಪ್ತ ವಿವರ :");
                        kannadaKeys.put("Forwarded Dept Id :", "ಇಲಾಖೆ : ");
                        kannadaKeys.put("Forwarded LineDepartment ID :", "ಸಾಲಿನ ಇಲಾಖೆ :");
                        kannadaKeys.put("Forwarded District ID :", "ಜಿಲ್ಲೆ :");
                        kannadaKeys.put("Status :", "ಸ್ಥಿತಿ :");
                        kannadaKeys.put("Post Name :", "ಪೋಸ್ಟ್ ಹೆಸರು : ");
                        kannadaKeys.put("Closure Date :", "ಮುಚ್ಚುವ ದಿನಾಂಕ :");
                        kannadaKeys.put("Closure Description :", "ಮುಚ್ಚುವಿಕೆಯ ವಿವರಣೆ :");
                        kannadaKeys.put("Rg is Atr Filled :", "ಆರ್ ಜಿ ಎಟಿಆರ್ ತುಂಬಿದೆ :");
                        kannadaKeys.put("Rg is Active :", "ಆರ್ ಜಿ ಸಕ್ರಿಯವಾಗಿದೆ :");
                        kannadaKeys.put("Rg Created On :", "ಆರ್ ಜಿ ರಚಿಸಲಾಗಿದೆ :");
                        kannadaKeys.put("Rg Created By :", "ಆರ್ ಜಿ ರಚಿಸಿದವರು :");
                        kannadaKeys.put("Rg Updated On :", "ಆರ್ ಜಿ ನವೀಕರಿಸಲಾಗಿದೆ :");
                        kannadaKeys.put("Rg Updated By :", "ಆರ್ ಜಿ ಇವರಿಂದ ನವೀಕರಿಸಲಾಗಿದೆ :");
                        kannadaKeys.put("Representative Type :", "ಚುನಾಯಿತ ಪ್ರತಿನಿಧಿ :");
                        kannadaKeys.put("MLA Constituency :", "ಶಾಸಕರು - ವಿಧಾನ ಸಭೆ ಕ್ಷೇತ್ರ :");
                        kannadaKeys.put("MP-Lok Sabha Constituency :", "ಲೋಕಸಭಾ ಸದಸ್ಯರ ಕ್ಷೇತ್ರ :");
                        kannadaKeys.put("MP-Rajya Sabha Constituency :", "ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರ ಕ್ಷೇತ್ರ :");
                        kannadaKeys.put("Ex MLC Constituency :", "ವಿಧಾನ ಪರಿಷತ್ತು - ಮಾಜಿ ಶಾಸಕರ ಕ್ಷೇತ್ರ :");
                        kannadaKeys.put("MLC Constituency :", "ವಿಧಾನ ಪರಿಷತ್ - ಶಾಸಕರ ಕ್ಷೇತ್ರ :");
                        kannadaKeys.put("CM Remark :", "ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳ ಟಿಪ್ಪಣಿ ಸಾರಾಂಶ :");
                        kannadaKeys.put("Priority :", "ಆದ್ಯತೆ :");
                        kannadaKeys.put("Number of Days :", "ದಿನಗಳ ಸಂಖ್ಯೆ :");
                        kannadaKeys.put("eOffice Receipt Cmp No :", "ಇ-ಕಚೇರಿ ಕಂಪ್ಯೂಟರ್ ರಶೀದಿ ನಂ. :");
                        kannadaKeys.put("eOffice Status :", "ಇ-ಕಚೇರಿ ಸ್ಥಿತಿ  :");
                        kannadaKeys.put("eOffice Receipt No :", "ಇ-ಕಚೇರಿ ರಶೀದಿ ನಂ. :");
                        kannadaKeys.put("eOffice Currently With :", "ಇ-ಕಚೇರಿ ಪ್ರಸ್ತುತ ಹಂತ :");
                        kannadaKeys.put("eOffice Since When :", "ಇ-ಕಚೇರಿ ಯಾವಾಗಿಂದ :");
                        kannadaKeys.put("eOffice Closing Remarks :", "ಇ-ಕಚೇರಿ ಮುಕ್ತಾಯ ಟಿಪ್ಪಣಿಗಳು :");
                        kannadaKeys.put("eOffice FileNumber :", "ಇ-ಕಚೇರಿ ಪತ್ರ ನಂ. :");
                        kannadaKeys.put("eOffice File Cmp No :", "ಇ-ಕಚೇರಿ ಪತ್ರ ಸಿಎಂಪಿ ನಂ. :");
                        kannadaKeys.put("eOffice Receipt Updated On :", "ಇ-ಕಚೇರಿ ರಸೀತು ನವೀಕರಿಸಲಾದ ದಿನಾಂಕ :");
                        kannadaKeys.put("eOffice Dep Code :", "ಇ-ಕಚೇರಿ ಇಲಾಖೆ ಸಂಖ್ಯೆ :");

                        kannadaDataMap.put(kannadaKeys.get("Representative No :"), dataMap.get("Representative No :"));
                        // Time formatting
                        String time = dataMap.get("Date :");
                        try {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                            Date date = inputFormat.parse(time);
                            String formattedDate = outputFormat.format(date);

                            System.out.println("Formatted Date and Time: " + formattedDate);

                            kannadaDataMap.put(kannadaKeys.get("Date :"), formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (java.text.ParseException e) {
                            throw new RuntimeException(e);
                        }
                        kannadaDataMap.put(kannadaKeys.get("Letter No :"), dataMap.get("Letter No :"));
                        kannadaDataMap.put(kannadaKeys.get("Representative Name :"), dataMap.get("Representative Name :"));
                        kannadaDataMap.put(kannadaKeys.get("Representative Mob :"), dataMap.get("Representative Mob :"));
                        kannadaDataMap.put(kannadaKeys.get("Representative Address :"), dataMap.get("Representative Address :"));
                        kannadaDataMap.put(kannadaKeys.get("Representative Griv Category :"), dataMap.get("Representative Griv Category :"));
                        kannadaDataMap.put(kannadaKeys.get("Attachment :"), dataMap.get("Attachment :"));
                        kannadaDataMap.put(kannadaKeys.get("CM Note Path :"), dataMap.get("CM Note Path :"));
                        kannadaDataMap.put(kannadaKeys.get("Letter Description :"), dataMap.get("Letter Description :"));
                        if (Objects.equals(dataMap.get("Status :"), "1")) {
                            kannadaDataMap.put(kannadaKeys.get("Status :"), "ರವಾನಿಸಲಾಗಿದೆ");
                        } else {
                            kannadaDataMap.put(kannadaKeys.get("Status :"), "ಮುಚ್ಚಲಾಗಿದೆ");
                        }
                        kannadaDataMap.put(kannadaKeys.get("Forwarded Dept Id :"), dataMap.get("Forwarded Dept Id :"));
                        kannadaDataMap.put(kannadaKeys.get("Forwarded LineDepartment ID :"), dataMap.get("Forwarded LineDepartment ID :"));
                        kannadaDataMap.put(kannadaKeys.get("Forwarded District ID :"), dataMap.get("Forwarded District ID :"));
                        kannadaDataMap.put(kannadaKeys.get("Post Name :"), dataMap.get("Post Name :"));
                        kannadaDataMap.put(kannadaKeys.get("Representative Type :"), dataMap.get("Representative Type :"));
                        kannadaDataMap.put(kannadaKeys.get("MLA Constituency :"), dataMap.get("MLA Constituency :"));
                        kannadaDataMap.put(kannadaKeys.get("MP-Lok Sabha Constituency :"), dataMap.get("MP-Lok Sabha Constituency :"));
                        kannadaDataMap.put(kannadaKeys.get("MP-Rajya Sabha Constituency :"), dataMap.get("MP-Rajya Sabha Constituency :"));
                        kannadaDataMap.put(kannadaKeys.get("MLC Constituency :"), dataMap.get("MLC Constituency :"));
                        kannadaDataMap.put(kannadaKeys.get("CM Remark :"), dataMap.get("CM Remark :"));
                        kannadaDataMap.put(kannadaKeys.get("Priority :"), dataMap.get("Priority :"));
                        kannadaDataMap.put(kannadaKeys.get("Number of Days :"), dataMap.get("Number of Days :"));
                        kannadaDataMap.put(kannadaKeys.get("Closure Date :"), dataMap.get("Closure Date :"));
                        kannadaDataMap.put(kannadaKeys.get("Closure Description :"), dataMap.get("Closure Description :"));
                        kannadaDataMap.put(kannadaKeys.get("Rg is Atr Filled :"), dataMap.get("Rg is Atr Filled :"));
                        kannadaDataMap.put(kannadaKeys.get("Rg is Active :"), dataMap.get("Rg is Active :"));
                        kannadaDataMap.put(kannadaKeys.get("Rg Created On :"), dataMap.get("Rg Created On :"));
                        kannadaDataMap.put(kannadaKeys.get("Rg Created By :"), dataMap.get("Rg Created By :"));
                        kannadaDataMap.put(kannadaKeys.get("Rg Updated On :"), dataMap.get("Rg Updated On :"));
                        kannadaDataMap.put(kannadaKeys.get("Rg Updated By :"), dataMap.get("Rg Updated By :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Receipt Cmp No :"), dataMap.get("eOffice Receipt Cmp No :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Status :"), dataMap.get("eOffice Status :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Receipt No :"), dataMap.get("eOffice Receipt No :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Currently With :"), dataMap.get("eOffice Currently With :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Since When :"), dataMap.get("eOffice Since When :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Closing Remarks :"), dataMap.get("eOffice Closing Remarks :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice FileNumber :"), dataMap.get("eOffice FileNumber :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice File Cmp No :"), dataMap.get("eOffice File Cmp No :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Receipt Updated On :"), dataMap.get("eOffice Receipt Updated On :"));
                        kannadaDataMap.put(kannadaKeys.get("eOffice Dep Code :"), dataMap.get("eOffice Dep Code :"));

                        recyclerView_name = findViewById(R.id.recycleView_letter_name);
                        LetterDetails_Adapter adapter_name = new LetterDetails_Adapter(context, kannadaDataMap);
                        recyclerView_name.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView_name.setAdapter(adapter_name);

                    } else {

                        dataMap.put("Representative Name :", english_map.get("Representative Name :"));
                        dataMap.put("Representative Griv Category :", english_map.get("Representative Griv Category :"));
                        dataMap.put("Forwarded Dept Id :", english_map.get("Forwarded Dept Id :"));
                        dataMap.put("Forwarded LineDepartment ID :", english_map.get("Forwarded LineDepartment ID :"));
                        dataMap.put("Post Name :", repDetails.getPostName());

                        if (Objects.equals(dataMap.get("Representative Type :"), "MLA")) {
                            dataMap.put("MLA Constituency :", english_map.get("Constituency :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "MP-Lok Sabha")) {
                            dataMap.put("MP-Lok Sabha Constituency :", english_map.get("Constituency :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "MLC")) {
                            dataMap.put("MLC Constituency :", english_map.get("Constituency :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "MP-Rajya Sabha")) {
                            dataMap.put("MP-Rajya Sabha Constituency :", english_map.get("Constituency :"));
                        } else if (Objects.equals(dataMap.get("Representative Type :"), "EX-MLC")) {
                            dataMap.put("Ex MLC Constituency :", english_map.get("Constituency :"));
                        } else {
                            dataMap.put("MLA Constituency :", english_map.get("Constituency :"));
                        }

                        // Time formatting
                        String time = dataMap.get("Date :");
                        try {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                            Date date = inputFormat.parse(time);
                            String formattedDate = outputFormat.format(date);

                            System.out.println("Formatted Date and Time: " + formattedDate);

                            dataMap.put("Date :", formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (java.text.ParseException e) {
                            throw new RuntimeException(e);
                        }

                        //  Status value setting
                        if (Objects.equals(dataMap.get("Status :"), "1")) {
                            dataMap.put("Status :", "Forwarded");
                        } else {
                            dataMap.put("Status :", "Closed");
                        }

                        recyclerView_name = findViewById(R.id.recycleView_letter_name);
                        LetterDetails_Adapter adapter_name = new LetterDetails_Adapter(context, dataMap);
                        recyclerView_name.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView_name.setAdapter(adapter_name);
                    }

                    Map<String, String> kannadaKeys_eOffice = new LinkedHashMap<>();
                    Map<String, String> eOffice_dataMap = new LinkedHashMap<>();

                    if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                        kannadaKeys_eOffice.put("Receipt CMP No :", "ಕಂಪ್ಯೂಟರ್ ರಶೀದಿ ನಂ. :");
                        kannadaKeys_eOffice.put("Status :", "ಸ್ಥಿತಿ :");
                        kannadaKeys_eOffice.put("Receipt No :", "ರಶೀದಿ ನಂ. :");
                        kannadaKeys_eOffice.put("Currently With :", "ಪ್ರಸ್ತುತ ಹಂತ :");
                        kannadaKeys_eOffice.put("Since When :", "ಯಾವಾಗಿಂದ :");
                        kannadaKeys_eOffice.put("Closing Remarks :", "ಮುಕ್ತಾಯ ಟಿಪ್ಪಣಿಗಳು :");
                        kannadaKeys_eOffice.put("File Number :", "ಪತ್ರ ನಂ. :");
                        kannadaKeys_eOffice.put("File CMP No :", "ಪತ್ರ ಸಿಎಂಪಿ ನಂ. :");
                        kannadaKeys_eOffice.put("Receipt Updated On :", "ರಸೀತು ನವೀಕರಿಸಲಾದ ದಿನಾಂಕ :");

                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Receipt CMP No :"), dataMap.get("eOffice Receipt Cmp No :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Status :"), dataMap.get("eOffice Status :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Receipt No :"), dataMap.get("eOffice Receipt No :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Currently With :"), dataMap.get("eOffice Currently With :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Since When :"), dataMap.get("eOffice Since When :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Closing Remarks :"), dataMap.get("eOffice Closing Remarks :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("File Number :"), dataMap.get("eOffice FileNumber :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("File CMP No :"), dataMap.get("eOffice File Cmp No :"));
                        eOffice_dataMap.put(kannadaKeys_eOffice.get("Receipt Updated On :"), dataMap.get("eOffice Receipt Updated On :"));

                        if (Objects.equals(eOffice_dataMap.get("ಸ್ಥಿತಿ :"), "ACTIVE")) {
                            eOffice_dataMap.put(kannadaKeys_eOffice.get("Closing Remarks :"), null);
                        }

                        LetterDetailsEofficeAdapter adapter_eOffice_details = new LetterDetailsEofficeAdapter(context, eOffice_dataMap);
                        recycleView_letter_eOffice.setLayoutManager(new LinearLayoutManager(context));
                        recycleView_letter_eOffice.setAdapter(adapter_eOffice_details);

                    } else {

                        eOffice_dataMap.put("Receipt CMP No :", dataMap.get("eOffice Receipt Cmp No :"));
                        eOffice_dataMap.put("Status :", dataMap.get("eOffice Status :"));
                        eOffice_dataMap.put("Receipt No :", dataMap.get("eOffice Receipt No :"));
                        eOffice_dataMap.put("Currently With :", dataMap.get("eOffice Currently With :"));
                        eOffice_dataMap.put("Since When :", dataMap.get("eOffice Since When :"));
                        eOffice_dataMap.put("Closing Remarks :", dataMap.get("eOffice Closing Remarks :"));
                        eOffice_dataMap.put("File Number :", dataMap.get("eOffice FileNumber :"));
                        eOffice_dataMap.put("File CMP No :", dataMap.get("eOffice File Cmp No :"));
                        eOffice_dataMap.put("Receipt Updated On :", dataMap.get("eOffice Receipt Updated On :"));

                        if (Objects.equals(eOffice_dataMap.get("Status :"), "ACTIVE")) {
                            eOffice_dataMap.put("Closing Remarks :", null);
                        }

                        LetterDetailsEofficeAdapter adapter_eOffice_details = new LetterDetailsEofficeAdapter(context, eOffice_dataMap);
                        recycleView_letter_eOffice.setLayoutManager(new LinearLayoutManager(context));
                        recycleView_letter_eOffice.setAdapter(adapter_eOffice_details);

                    }

                } else {
                    Log.e("fetchRepDetails", "Unsuccessful response: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RepDetailsResponse> call, @NonNull Throwable t) {
                Log.e("fetchRepDetails", "API call failed: " + t.getMessage());
            }
        });
    }
}
