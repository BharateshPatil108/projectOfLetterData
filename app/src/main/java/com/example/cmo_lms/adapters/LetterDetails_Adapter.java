package com.example.cmo_lms.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.LanguageUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        setAnimation(holder.itemView, position);
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

            Map<String, String> kannadaKeys = new LinkedHashMap<>();

            if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                kannadaKeys.put("Representative No :","ಪ್ರತಿನಿಧಿ ಸಂಖ್ಯೆ :");
                kannadaKeys.put("Date :","ದಿನಾಂಕ :");
                kannadaKeys.put("Letter No :","ಪತ್ರದ ಸಂಖ್ಯೆ :");
                kannadaKeys.put("Representative Name :","ಪ್ರತಿನಿಧಿ ಹೆಸರು :");
                kannadaKeys.put("Representative Mob :","ಪ್ರತಿನಿಧಿ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ. :");
                kannadaKeys.put("Representative Address :","ಪತ್ರದಲ್ಲಿರುವಂತೆ ವಿಳಾಸ :");
                kannadaKeys.put("Representative Griv Category :","ಪ್ರತಿನಿಧಿ ವರ್ಗ :");
                kannadaKeys.put("Attachment :","ಲಗತ್ತು :");
                kannadaKeys.put("CM Note Path :","ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳ ಟಿಪ್ಪಣಿ ಸಾರಾಂಶ :");
                kannadaKeys.put("Letter Description :","ಪತ್ರದ/ಟಿಪ್ಪಣಿಯ ಸಂಕ್ಷಿಪ್ತ ವಿವರ :");
                kannadaKeys.put("Forwarded Dept Id :","ಇಲಾಖೆ : ");
                kannadaKeys.put("Forwarded LineDepartment ID :","ಸಾಲಿನ ಇಲಾಖೆ :");
                kannadaKeys.put("Forwarded District ID :","ಜಿಲ್ಲೆ :");
                kannadaKeys.put("Status :","ಸ್ಥಿತಿ :");
                kannadaKeys.put("Post Name :","ಪೋಸ್ಟ್ ಹೆಸರು : ");
                kannadaKeys.put("Representative Type :","ಚುನಾಯಿತ ಪ್ರತಿನಿಧಿ :");
                kannadaKeys.put("MLA Constituency :","ಕ್ಷೇತ್ರ :");
                kannadaKeys.put("MP-Lok Sabha Constituency :","ಕ್ಷೇತ್ರ :");
                kannadaKeys.put("MP-Rajya Sabha Constituency :","ಕ್ಷೇತ್ರ :");
                kannadaKeys.put("Ex MLC Constituency :","ಕ್ಷೇತ್ರ :");
                kannadaKeys.put("MLC Constituency :","ಕ್ಷೇತ್ರ :");
                kannadaKeys.put("CM Remark :","ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳ ಟಿಪ್ಪಣಿ ಸಾರಾಂಶ :");
                kannadaKeys.put("Priority :","ಆದ್ಯತೆ :");
                kannadaKeys.put("Number of Days :","ದಿನಗಳ ಸಂಖ್ಯೆ :");

                dataMap.put(kannadaKeys.get("Representative No :"),dataMap.get("Representative No :"));
                dataMap.put(kannadaKeys.get("Date :"),dataMap.get("Date :"));
                dataMap.put(kannadaKeys.get("Letter No :"),dataMap.get("Letter No :"));
                dataMap.put(kannadaKeys.get("Representative Name :"),dataMap.get("Representative Name :"));
                dataMap.put(kannadaKeys.get("Representative Mob :"),dataMap.get("Representative Mob :"));
                dataMap.put(kannadaKeys.get("Representative Address :"),dataMap.get("Representative Address :"));
                dataMap.put(kannadaKeys.get("Representative Griv Category :"),dataMap.get("Representative Griv Category :"));
                dataMap.put(kannadaKeys.get("Attachment :"),dataMap.get("Attachment :"));
                dataMap.put(kannadaKeys.get("CM Note Path :"),dataMap.get("CM Note Path :"));
                dataMap.put(kannadaKeys.get("Letter Description :"),dataMap.get("Letter Description :"));
                dataMap.put(kannadaKeys.get("Forwarded Dept Id :"),dataMap.get("Forwarded Dept Id :"));
                dataMap.put(kannadaKeys.get("Forwarded LineDepartment ID :"),dataMap.get("Forwarded LineDepartment ID :"));
                dataMap.put(kannadaKeys.get("Forwarded District ID :"),dataMap.get("Forwarded District ID :"));
                dataMap.put(kannadaKeys.get("Status :"),dataMap.get("Status :"));
                dataMap.put(kannadaKeys.get("Post Name :"),dataMap.get("Post Name :"));
                dataMap.put(kannadaKeys.get("Representative Type :"),dataMap.get("Representative Type :"));
                dataMap.put(kannadaKeys.get("MLA Constituency :"),dataMap.get("MLA Constituency :"));
                dataMap.put(kannadaKeys.get("MP-Lok Sabha Constituency :"),dataMap.get("MP-Lok Sabha Constituency :"));
                dataMap.put(kannadaKeys.get("MP-Rajya Sabha Constituency :"),dataMap.get("MP-Rajya Sabha Constituency :"));
                dataMap.put(kannadaKeys.get("MLC Constituency :"),dataMap.get("MLC Constituency :"));
                dataMap.put(kannadaKeys.get("CM Remark :"),dataMap.get("CM Remark :"));
                dataMap.put(kannadaKeys.get("Priority :"),dataMap.get("Priority :"));
                dataMap.put(kannadaKeys.get("Number of Days :"),dataMap.get("Number of Days :"));
                String key = (String) dataMap.keySet().toArray()[position];


            }

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

