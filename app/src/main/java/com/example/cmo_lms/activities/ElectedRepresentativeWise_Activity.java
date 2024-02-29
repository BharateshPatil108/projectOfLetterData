package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.adapters.ElectedRepresentativeWise_Adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectedRepresentativeWise_Activity extends AppCompatActivity {

    TextView textView_ele_rep, textview_pdf_ele_rep;
    LinearLayout linearLayout_btn_ele_rep, linearLayout_pdf_ele_rep;
    ImageView imageView_ele_rep;
    ElectedRepresentativeWise_Adapter electedRepresentativeWiseAdapter;
    RecyclerView recyclerView_ele_rep;
    Toolbar customToolbar_ele_rep;
    ImageView backBtn_ele_rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elected_representative_wise);

        textView_ele_rep = findViewById(R.id.cmo_lms_ele_rep);
        textview_pdf_ele_rep = findViewById(R.id.pdf_txt_btn_ele_rep);
        linearLayout_btn_ele_rep = findViewById(R.id.lin_lay_ele_rep_1);
        linearLayout_pdf_ele_rep = findViewById(R.id.lin_lay_ele_rep_2);
        recyclerView_ele_rep = findViewById(R.id.recycleView_ele_rep);
        imageView_ele_rep = findViewById(R.id.file_img_pdf_ele_rep);
        customToolbar_ele_rep = findViewById(R.id.custom_toolbar_ele_rep);
        backBtn_ele_rep = customToolbar_ele_rep.findViewById(R.id.back_btn);

        textview_pdf_ele_rep.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        imageView_ele_rep.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        List<String> ele_rep_outerData = Arrays.asList("Bhimrao Patil", "B. G. Patil", "Channaraj Hattiholi", "Ganapathi Ulvekar", "K. S. Naveen", "Lakhan Jarakiholi");
        Map<String, Integer> ele_rep_innerDataList = new HashMap<>();
        ele_rep_innerDataList.put("Received", 136);
        ele_rep_innerDataList.put("Disposed", 136);
        ele_rep_innerDataList.put("Accepted", 0);
        ele_rep_innerDataList.put("Rejected", 16);
        ele_rep_innerDataList.put("Pending", 136);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_ele_rep.setLayoutManager(layoutManager);
        electedRepresentativeWiseAdapter = new ElectedRepresentativeWise_Adapter(ele_rep_outerData.toArray(new String[0]), ele_rep_innerDataList, this);
        recyclerView_ele_rep.setAdapter(electedRepresentativeWiseAdapter);

        backBtn_ele_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(customToolbar_ele_rep);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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