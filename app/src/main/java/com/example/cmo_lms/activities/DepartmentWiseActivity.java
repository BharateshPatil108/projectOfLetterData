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
import com.example.cmo_lms.adapters.DepartmentWise_Adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentWiseActivity extends AppCompatActivity {
    TextView textView_dep, textview_pdf_dep;
    LinearLayout linearLayout_btn_dep, linearLayout_pdf_dep;
    ImageView imageView_dep, search_img_dep;
    DepartmentWise_Adapter departmentWiseAdapter;
    RecyclerView recyclerView_dep;
    Toolbar customToolbar_dep;
    ImageView backBtn_dep;
    EditText edt_txt_search_dep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_wise);

        textView_dep = findViewById(R.id.cmo_lms_dep);
        textview_pdf_dep = findViewById(R.id.pdf_txt_btn_dep);
        linearLayout_btn_dep = findViewById(R.id.lin_lay_dep_1);
        linearLayout_pdf_dep = findViewById(R.id.lin_lay_dep_2);
        recyclerView_dep = findViewById(R.id.recycleView_dep);
        imageView_dep = findViewById(R.id.file_img_pdf_dep);
        customToolbar_dep = findViewById(R.id.custom_toolbar_dep);
        backBtn_dep = customToolbar_dep.findViewById(R.id.back_btn);
        search_img_dep = customToolbar_dep.findViewById(R.id.toolbar_search_img);
        edt_txt_search_dep = customToolbar_dep.findViewById(R.id.edit_txt_toolbar);

        textview_pdf_dep.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        imageView_dep.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        search_img_dep.setOnClickListener(v -> edt_txt_search_dep.requestFocus());

        List<String> sub_outerData = Arrays.asList("Backward Classes Welfare Department", "Department of Health", "DPAR (Administrative Reforms)", "Transport Department", "Department of Home Affairs", "Department of Co-operation");

        Map<String, Integer> sub_innerDataList = new HashMap<>();
        sub_innerDataList.put("Received", 136);
        sub_innerDataList.put("Disposed", 136);
        sub_innerDataList.put("Accepted", 0);
        sub_innerDataList.put("Rejected", 16);
        sub_innerDataList.put("Pending", 136);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_dep.setLayoutManager(layoutManager);
        departmentWiseAdapter = new DepartmentWise_Adapter(sub_outerData.toArray(new String[0]), sub_innerDataList, this);
        recyclerView_dep.setAdapter(departmentWiseAdapter);

        backBtn_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(customToolbar_dep);
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