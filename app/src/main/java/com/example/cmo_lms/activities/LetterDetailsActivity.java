package com.example.cmo_lms.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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
    ConstraintLayout letter_const_lay;
    static Map<String, String> dataMap = new LinkedHashMap<>();
    static Map<String, Integer> apiCallMap_searchIds = new LinkedHashMap<>();
    static RepDetailsResponse repDetails;
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
                JsonUtils.generatePdf(dataMap, context);
            }
        });

        pdfImg.setOnClickListener(v -> {
            String data = dataMap.get("Attachment :");
            if (data == null || data.isEmpty()) {
                Toast.makeText(context, "Letter path is not present.", Toast.LENGTH_SHORT).show();
            } else {
                JsonUtils.generatePdf(dataMap, context);
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ipgrs.karnataka.gov.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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

                        List<String> keysForApiCalls = Arrays.asList("Representative Name :", "Representative Griv Category :", "Forwarded Dept Id :", "Forwarded LineDepartment ID :", "Forwarded District ID :", "MLA Constituency :", "MPL Constituency :");

                        if (!(dataMap.get("Attachment :") == null) || Objects.requireNonNull(dataMap.get("Attachment :")).isEmpty()) {
                            button.setVisibility(View.VISIBLE);
                            viewPdf = dataMap.get("Attachment :");

                            Log.e("viewPdf_data", viewPdf);
                        }

                        for (String key : keysForApiCalls) {
                            String value = dataMap.get(key);
                            int intValue = value != null && !value.equals("null") ? Integer.parseInt(value) : 0;
                            apiCallMap_searchIds.put(key, intValue);
                        }

                        fetchRepDetails(dataMap);
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

    public void fetchRepDetails(Map<String, String> idsData) {
        JsonObject requestBody = new JsonObject();

        requestBody.addProperty("rg_griv_category_id", idsData.get("Representative Griv Category :"));
        requestBody.addProperty("rg_forwarded_dept_id", idsData.get("Forwarded Dept Id :"));
        requestBody.addProperty("rg_line_department_id", idsData.get("Forwarded LineDepartment ID :"));
        requestBody.addProperty("mla_constituency", idsData.get("MLA Constituency :"));
        requestBody.addProperty("mpl_constituency", idsData.get("MPL Constituency :"));
        requestBody.addProperty("mpr_constituency", idsData.get("MPR Constituency :"));
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

                    dataMap.put("Representative Name :", repDetails.getRepName());
                    dataMap.put("Representative Griv Category :", repDetails.getCategoryName());
                    dataMap.put("Forwarded Dept Id :", repDetails.getDeptName());
                    dataMap.put("Forwarded LineDepartment ID :", repDetails.getLineDepName());
                    dataMap.put("Post Name :", repDetails.getPostName());

                    if (Objects.equals(dataMap.get("Representative Type :"), "ಶಾಸಕರು - ವಿಧಾನ ಸಭೆ")) {
                        dataMap.put("MLA Constituency :", repDetails.getConName());
                    } else if (Objects.equals(dataMap.get("Representative Type :"), "ಲೋಕಸಭಾ ಸದಸ್ಯರು")) {
                        dataMap.put("MPL Constituency :", repDetails.getConName());
                    } else if (Objects.equals(dataMap.get("Representative Type :"), "ಶಾಸಕರು - ವಿಧಾನ ಪರಿಷತ್‌")) {
                        dataMap.put("MLC Constituency :", repDetails.getConName());
                    } else if (Objects.equals(dataMap.get("Representative Type :"), "ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರು")) {
                        dataMap.put("MPR Constituency :", repDetails.getConName());
                    } else if (Objects.equals(dataMap.get("Representative Type :"), "ಮಾಜಿ ಶಾಸಕರು – ವಿಧಾನ ಪರಿಷತ್ತು")) {
                        dataMap.put("Ex MLC Constituency :", repDetails.getConName());
                    } else {
                        dataMap.put("MLA Constituency :", repDetails.getConName());
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

                    Map<String, String> eOffice_dataMap = new LinkedHashMap<>();
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

                } else {
                    Log.e("fetchRepDetails", "Unsuccessful response: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RepDetailsResponse> call, Throwable t) {
                Log.e("fetchRepDetails", "API call failed: " + t.getMessage());
            }
        });
    }
}