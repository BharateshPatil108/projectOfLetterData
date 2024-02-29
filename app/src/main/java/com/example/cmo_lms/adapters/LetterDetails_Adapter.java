package com.example.cmo_lms.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.net.ParseException;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LetterDetails_Adapter extends RecyclerView.Adapter<LetterDetails_Adapter.MyViewHolder> {
    private final Map<String, String> dataMap;
    Context context;
    private int lastPosition = -1;

    public LetterDetails_Adapter(Context context_anim, Map<String, String> dataMap) {
        this.context = context_anim;
        this.dataMap = dataMap;
    }

    @NonNull
    @Override
    public LetterDetails_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.letter_indiv_item, parent, false);
        return new LetterDetails_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterDetails_Adapter.MyViewHolder holder, int position) {
        holder.bind(dataMap, position);
        Log.e("adapter data for search refno", dataMap.toString());
        Log.e("adapter data for search name ", dataMap.toString());
        // setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return dataMap.size();
    }

    private void setAnimation(View viewToAnimation, int position) {
        if (position > lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.recyle_anim);
            viewToAnimation.startAnimation(slideIn);
            lastPosition = position;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CardView sub_cv_dropdown;
        private final TextView textViewName;
        private final TextView textViewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sub_cv_dropdown = itemView.findViewById(R.id.Sub_cv_dropdown);
            textViewName = itemView.findViewById(R.id.txt_indivitem);
            textViewDescription = itemView.findViewById(R.id.detail_of_indivitem);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Map<String, String> dataMap, int position) {

            String key = (String) dataMap.keySet().toArray()[position];

            String value = dataMap.get(key);

            if (!shouldSkipKey(key)) {
                if (value != null && !value.equalsIgnoreCase("null")) {
                    textViewName.setText(key);
                    textViewDescription.setText(value);

                } else {
                    // Hide or disable the views if the value is null or "null"
                    textViewName.setVisibility(View.GONE);
                    textViewDescription.setVisibility(View.GONE);
                    sub_cv_dropdown.setVisibility(View.GONE);
                }
                textViewName.setText(key);
                textViewDescription.setText(value);
            } else {
                // Hide or disable the views if the key should be skipped
                textViewName.setVisibility(View.GONE);
                textViewDescription.setVisibility(View.GONE);
                sub_cv_dropdown.setVisibility(View.GONE);
            }
        }

        private boolean shouldSkipKey(String key) {
            List<String> keysToSkip = Arrays.asList("Rg ID :", "Rg is Atr Filled :", "Rg is Active :", "Rg Created On :", "Rg Created By :", "Rg Updated On :", "Rg Updated By :", "eOffice Dep Code :", "eOffice Receipt Cmp No :", "eOffice Status :", "eOffice Receipt No :", "eOffice Currently With :", "eOffice Since When :", "eOffice Closing Remarks :", "eOffice FileNumber :", "eOffice File Cmp No :", "eOffice Receipt Updated On :", "Attachment :");
            return keysToSkip.contains(key);
        }

    }
}

