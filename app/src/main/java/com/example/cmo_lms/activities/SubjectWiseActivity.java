package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.adapters.SubjectWise_Adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SubjectWiseActivity extends AppCompatActivity {
    TextView textView, textview_pdf;
    EditText edit_txt_toolbar;
    LinearLayout linearLayout_btn, linearLayout_pdf;
    ImageView imageView_sub, search_img;
    SubjectWise_Adapter subjectWiseAdapter;
    RecyclerView recyclerView_sub;
    Toolbar customToolbar;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_wise);

        textView = findViewById(R.id.cmo_lms_sub);
        textview_pdf = findViewById(R.id.pdf_txt_btn_sub);
        linearLayout_btn = findViewById(R.id.lin_lay_sub_1);
        linearLayout_pdf = findViewById(R.id.lin_lay_sub_2);
        recyclerView_sub = findViewById(R.id.recycleView_sub);
        imageView_sub = findViewById(R.id.file_img_pdf_sub);
        customToolbar = findViewById(R.id.custom_toolbar_sub);
        backBtn = customToolbar.findViewById(R.id.back_btn);
        search_img = customToolbar.findViewById(R.id.toolbar_search_img);
        edit_txt_toolbar = customToolbar.findViewById(R.id.edit_txt_toolbar);

        textview_pdf.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        imageView_sub.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        List<String> sub_outerData = Arrays.asList("Service Matter", "Subsidies", "Loans and Subsidies", "Shakthi Scheme", "Airports", "Scholarship");
        Map<String, Integer> sub_innerDataList = new HashMap<>();
        sub_innerDataList.put("Received", 136);
        sub_innerDataList.put("Disposed", 136);
        sub_innerDataList.put("Accepted", 0);
        sub_innerDataList.put("Rejected", 16);
        sub_innerDataList.put("Pending", 136);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_sub.setLayoutManager(layoutManager);
        subjectWiseAdapter = new SubjectWise_Adapter(sub_outerData.toArray(new String[0]), sub_innerDataList, this);
        recyclerView_sub.setAdapter(subjectWiseAdapter);

        backBtn.setOnClickListener(v -> onBackPressed());

        search_img.setOnClickListener(v -> edit_txt_toolbar.requestFocus());

        setSupportActionBar(customToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!InternetUtil.isNetworkAvailable(this)) {
            Intent dIntent = new Intent(this, NoInternetActivity.class);
            dIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dIntent);
            finish();
        }
    }
}