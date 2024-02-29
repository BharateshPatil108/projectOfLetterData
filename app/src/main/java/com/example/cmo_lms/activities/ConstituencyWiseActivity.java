package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;
import com.example.cmo_lms.adapters.ConstituencyWise_MLC_Category_Adapter;
import com.example.cmo_lms.adapters.CustomSpinnerAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstituencyWiseActivity extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView1;
    LinearLayout linearLayout, linearLayout1;
    ImageView drop_down_btn, search_img_const,img_pdf_btn;
    TextView textView,tv_pdf_btn;
    private PopupWindow popupWindow;
    Context context = this;
    Toolbar customToolbar_const_wise;
    ImageView backBtn_const_wise;
    EditText edt_txt_search_const;
    private boolean isDropDownOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constituency_wise);

        recyclerView1 = findViewById(R.id.recyclerView_const);
        recyclerView = findViewById(R.id.const_recycler_view);
        linearLayout = findViewById(R.id.lin_lay_const_wise_2);
        drop_down_btn = findViewById(R.id.drop_down_mlc_img);
        textView = findViewById(R.id.select_mlc);
        linearLayout1 = findViewById(R.id.mlc_names_recycler_lin_lay);
        customToolbar_const_wise = findViewById(R.id.constituency_search_bar);
        backBtn_const_wise = customToolbar_const_wise.findViewById(R.id.back_btn);
        search_img_const = customToolbar_const_wise.findViewById(R.id.toolbar_search_img);
        edt_txt_search_const = customToolbar_const_wise.findViewById(R.id.edit_txt_toolbar);
        tv_pdf_btn = findViewById(R.id.pdf_txt_btn_const_wise);
        img_pdf_btn = findViewById(R.id.file_img_pdf_const_wise);

        tv_pdf_btn.setOnClickListener(v -> Toast.makeText(context, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        img_pdf_btn.setOnClickListener(v -> Toast.makeText(context, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        search_img_const.setOnClickListener(v -> edt_txt_search_const.requestFocus());

        isDropDownOpen = true;
        drop_down_btn.setOnClickListener(v -> {
            if (isDropDownOpen) {
                drop_down_btn.setImageResource(R.drawable.keyboard_arrow_up_24);
                drop_down_btn.setBackground(null);
                isDropDownOpen = false;
                showPopupWindow();
            }
        });

        Map<String, Integer> const_mlc_detail_innerDataList = new HashMap<>();
        const_mlc_detail_innerDataList.put("Received", 136);
        const_mlc_detail_innerDataList.put("Disposed", 136);
        const_mlc_detail_innerDataList.put("Accepted", 0);
        const_mlc_detail_innerDataList.put("Rejected", 16);
        const_mlc_detail_innerDataList.put("Pending", 136);

        String[] secondItems = {"Bhimrao Patil", "P. H. Poojara", "Lakhan Jarakiholi"};

        ConstituencyWise_MLC_Category_Adapter secondAdapter = new ConstituencyWise_MLC_Category_Adapter(secondItems,const_mlc_detail_innerDataList,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(secondAdapter);

        backBtn_const_wise.setOnClickListener(v -> onBackPressed());
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
    private void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_recycler_view, null);

        recyclerView = popupView.findViewById(R.id.recyclerView_popup_const);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

        List<String> spinnerItems = Arrays.asList("Graduates", "Teachers", "Nominated by the Governor", "Elected by Legislative Assembly", "Local Authorities constituencies");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(spinnerItems, item -> {
            textView.setText(item);
            linearLayout1.setVisibility(View.VISIBLE);
            popupWindow.dismiss();
        });

        recyclerView.setAdapter(adapter);

        popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOnDismissListener(() -> {
            drop_down_btn.setImageResource(R.drawable.arrow_down_24);
            isDropDownOpen = true;
        });
        popupWindow.showAsDropDown(textView);
    }

}