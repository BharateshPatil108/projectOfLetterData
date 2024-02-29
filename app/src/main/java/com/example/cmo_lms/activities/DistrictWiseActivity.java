package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.example.cmo_lms.adapters.CustomSpinnerAdapter;

import java.util.Arrays;
import java.util.List;

public class DistrictWiseActivity extends AppCompatActivity {
    Toolbar custom_toolbar;
    TextView select_district_tv, tv_pdf_btn_dist;
    ImageView dropDown_district_img, search_img, img_pdf_btn_dist;
    RecyclerView RV_sel_district, lok_sabha_recycler_view, vidhana_sabha_recycler_view;
    ImageView back_btn_toolbar;
    EditText search_edt_txt;
    LinearLayout linearLayout_dist;
    private PopupWindow popupWindow;
    private boolean isDropDownOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise);

        custom_toolbar = findViewById(R.id.district_search_tool_bar);
        select_district_tv = findViewById(R.id.select_district);
        dropDown_district_img = findViewById(R.id.drop_down_district_img);
        lok_sabha_recycler_view = findViewById(R.id.lok_sabha_recycler_view);
        vidhana_sabha_recycler_view = findViewById(R.id.vidhana_sabha_recycler_view);
        back_btn_toolbar = custom_toolbar.findViewById(R.id.back_btn);
        search_edt_txt = custom_toolbar.findViewById(R.id.edit_txt_toolbar);
        search_img = custom_toolbar.findViewById(R.id.toolbar_search_img);
        linearLayout_dist = findViewById(R.id.lin_rv_select_dist);
        img_pdf_btn_dist = findViewById(R.id.file_img_pdf_dist_wise);
        tv_pdf_btn_dist = findViewById(R.id.pdf_txt_btn_dist_wise);

        tv_pdf_btn_dist.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        img_pdf_btn_dist.setOnClickListener(v -> Toast.makeText(this, "Sorry Could not download dummy data..", Toast.LENGTH_SHORT).show());

        search_img.setOnClickListener(v -> search_edt_txt.requestFocus());

        back_btn_toolbar.setOnClickListener(v -> onBackPressed());

        isDropDownOpen = true;
        dropDown_district_img.setOnClickListener(v -> {
            if (isDropDownOpen) {
                dropDown_district_img.setImageResource(R.drawable.keyboard_arrow_up_24);
                dropDown_district_img.setBackground(null);
                isDropDownOpen = false;
                showPopupWindow();
            }
        });

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

        RV_sel_district = popupView.findViewById(R.id.recyclerView_popup_const);
        RV_sel_district.setLayoutManager(new LinearLayoutManager(this));

        List<String> spinnerItems = Arrays.asList("Belagavi", "Vijayapura", "Hasan", "Gulbarga", "Mandya" , "Mysore", "Shivamogga");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(spinnerItems, item -> {
            select_district_tv.setText(item);
            linearLayout_dist.setVisibility(View.VISIBLE);
            popupWindow.dismiss();
        });

        RV_sel_district.setAdapter(adapter);

        popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOnDismissListener(() -> {
            dropDown_district_img.setImageResource(R.drawable.arrow_down_24);
            isDropDownOpen = true;
        });
        popupWindow.showAsDropDown(select_district_tv);
    }
}