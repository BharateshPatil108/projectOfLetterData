package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmo_lms.R;

public class SearchLettersActivity extends AppCompatActivity {

    TextView searchText;
    ImageView dropdown_img, search_img;
    RecyclerView recyclerView_const_select_criteria, recyclerView_const_search_data;
    EditText search_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_letters);

        searchText = findViewById(R.id.select_criteria_txt);
        dropdown_img = findViewById(R.id.drop_down_select_criteria_img);
        search_img = findViewById(R.id.search_engin_img);
        recyclerView_const_select_criteria = findViewById(R.id.recyclerView_const_dropdown_criteria);
        recyclerView_const_search_data = findViewById(R.id.recyclerView_const_search_data);
        search_data = findViewById(R.id.search_txt_letter);
    }
}