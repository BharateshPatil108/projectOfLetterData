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

import org.json.JSONObject;

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

                        List<String> keysForApiCalls = Arrays.asList("Representative Name :", "Representative Griv Category :", "Forwarded Dept Id :", "Forwarded LineDepartment ID :", "Forwarded District ID :", "MLA Constituency :", "MPL Constituency :");

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
                    Map<String,String> kannada_map = new LinkedHashMap<>();
                    Map<String,String> english_map = new LinkedHashMap<>();

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

                        Log.e("kannada data",kannada_map.toString());
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

                    if (LanguageUtil.getCurrentLanguage().equals("kn")) {

                        recyclerView_name = findViewById(R.id.recycleView_letter_name);
                        LetterDetails_Adapter adapter_name = new LetterDetails_Adapter(context, dataMap);
                        recyclerView_name.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView_name.setAdapter(adapter_name);
                    } else {
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

                    }

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
            public void onFailure(@NonNull Call<RepDetailsResponse> call, @NonNull Throwable t) {
                Log.e("fetchRepDetails", "API call failed: " + t.getMessage());
            }
        });
    }

    public static boolean containsKannadaCharacters(String str) {
        // Check if the string contains any Kannada characters (Unicode range: \u0C80 to \u0CFF)
        return str != null && str.matches(".*[\\u0C80-\\u0CFF].*");
    }
}
