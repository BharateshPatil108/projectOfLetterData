package com.example.cmo_lms.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.LanguageUtil;
import com.example.cmo_lms.activities.LetterDetailsActivity;
import com.example.cmo_lms.model.SearchResponseModel;

import java.util.List;

public class Search_name_data_Adapter extends RecyclerView.Adapter<Search_name_data_Adapter.ViewHolder> {
    private final List<SearchResponseModel> dataList;
    Context context_ada;

    public Search_name_data_Adapter(Context context, List<SearchResponseModel> dataList) {
        this.context_ada = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_popup, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResponseModel currentItem = dataList.get(position);

        int serialNumber = position + 1;
        holder.tv_total_entries.setText(context_ada.getText(R.string.sl_no_) + " " + serialNumber);

        switch (currentItem.getRepType()) {
            case 1:
                if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                    holder.mp_name.setText("ವಿಧಾನ ಸಭೆ - ಶಾಸಕರ ಹೆಸರು : ");
                    holder.mp_constituency.setText("ವಿಧಾನ ಸಭೆ - ಶಾಸಕರ ಕ್ಷೇತ್ರ : ");
                    holder.Rg_grievance_desc.setText("ಪತ್ರದ ವಿವರಗಳು : ");
                } else {
                    holder.mp_name.setText("MLA Name : ");
                    holder.mp_constituency.setText("MLA Constituency : ");
                    holder.Rg_grievance_desc.setText("Letter Description : ");
                }
                holder.refNoTextView.setText(currentItem.getMlaNames());
                holder.addressTextView.setText(currentItem.getMlaConstituencyDesc());
                holder.grievanceDescTextView.setText(currentItem.getRgGrievanceDesc());
                break;
            case 2:
                if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                    holder.mp_name.setText("ಲೋಕಸಭಾ ಸದಸ್ಯರ ಹೆಸರು : ");
                    holder.mp_constituency.setText("ಲೋಕಸಭಾ ಸದಸ್ಯರ ಕ್ಷೇತ್ರ : ");
                    holder.Rg_grievance_desc.setText("ಪತ್ರದ ವಿವರಗಳು : ");
                } else {
                    holder.mp_name.setText("MP-Lok Sabha Name : ");
                    holder.mp_constituency.setText("MP-Lok Sabha Constituency : ");
                    holder.Rg_grievance_desc.setText("Letter Description : ");
                }
                holder.refNoTextView.setText(currentItem.getMpName());
                holder.addressTextView.setText(currentItem.getMpConstituencyDesc());
                holder.grievanceDescTextView.setText(currentItem.getRgGrievanceDesc());
                break;
            case 3:
                if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                    holder.mp_name.setText("ವಿಧಾನ ಪರಿಷತ್ತಿನ್ - ಶಾಸಕರ ಹೆಸರು : ");
                    holder.Rg_grievance_desc.setText("ಪತ್ರದ ವಿವರಗಳು : ");
                } else {
                    holder.mp_name.setText("MLC Name : ");
                    holder.Rg_grievance_desc.setText("Letter Description : ");
                }
                holder.mp_constituency.setVisibility(View.GONE);
                holder.refNoTextView.setText(currentItem.getMlcMemberName());
                holder.addressTextView.setVisibility(View.GONE);
                holder.cv_letter_second_data.setVisibility(View.GONE);
                holder.grievanceDescTextView.setText(currentItem.getRgGrievanceDesc());
                break;
            case 4:
                if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                    holder.mp_name.setText("ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರ ಹೆಸರು : ");
                    holder.Rg_grievance_desc.setText("ಪತ್ರದ ವಿವರಗಳು : ");
                } else {
                    holder.mp_name.setText("MP-Rajya Sabha Name : ");
                    holder.Rg_grievance_desc.setText("Letter Description : ");
                }
                holder.refNoTextView.setText(currentItem.getMpMemberName());
                holder.cv_letter_second_data.setVisibility(View.GONE);
                holder.grievanceDescTextView.setText(currentItem.getRgGrievanceDesc());
                break;
            case 5:
                if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                    holder.mp_name.setText("ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳ ಹೆಸರು : ");
                    holder.mp_constituency.setText("ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳ ಕ್ಷೇತ್ರ : ");
                    holder.Rg_grievance_desc.setText("ಪತ್ರದ ವಿವರಗಳು : ");
                } else {
                    holder.mp_name.setText("CM Name : ");
                    holder.mp_constituency.setText("CM Constituency : ");
                    holder.Rg_grievance_desc.setText("Letter Description : ");
                }
                holder.refNoTextView.setText(currentItem.getMlaNames());
                holder.addressTextView.setText(currentItem.getMlaConstituencyDesc());
                holder.grievanceDescTextView.setText(currentItem.getRgGrievanceDesc());
                break;
            case 6:
                if (LanguageUtil.getCurrentLanguage().equals("kn")) {
                    holder.mp_name.setText("ವಿಧಾನ ಪರಿಷತ್ತಿನ್ - ಮಾಜಿ ಶಾಸಕರ ಹೆಸರು : ");
                    holder.Rg_grievance_desc.setText("ಪತ್ರದ ವಿವರಗಳು : ");
                } else {
                    holder.mp_name.setText("Ex-MLC Name : ");
                    holder.Rg_grievance_desc.setText("Letter Description : ");
                }
                holder.mp_constituency.setVisibility(View.GONE);
                holder.refNoTextView.setText(currentItem.getEmKname());
                holder.addressTextView.setVisibility(View.GONE);
                holder.cv_letter_second_data.setVisibility(View.GONE);
                holder.grievanceDescTextView.setText(currentItem.getRgGrievanceDesc());
                break;
            default:
                break;
        }

        holder.viewContentButton.setOnClickListener(v -> {
            holder.moveToSeeLetterData(currentItem);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv_letter_second_data;
        TextView refNoTextView;
        TextView addressTextView;
        TextView grievanceDescTextView;
        TextView tv_total_entries;
        TextView mp_name, mp_constituency, Rg_grievance_desc;
        Button viewContentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_letter_second_data = itemView.findViewById(R.id.letter_second_data);
            tv_total_entries = itemView.findViewById(R.id.tv_total_entries_ref);
            refNoTextView = itemView.findViewById(R.id.ref_data);
            addressTextView = itemView.findViewById(R.id.data_name);
            grievanceDescTextView = itemView.findViewById(R.id.rep_type_data);
            viewContentButton = itemView.findViewById(R.id.letter_all_data_view_btn);
            mp_name = itemView.findViewById(R.id.letter_mp_mla_name);
            mp_constituency = itemView.findViewById(R.id.mp_mla_constituency);
            Rg_grievance_desc = itemView.findViewById(R.id.rg_griv_desc_tv);
        }

        private void moveToSeeLetterData(SearchResponseModel currentItem) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, LetterDetailsActivity.class);
            intent.putExtra("searchNameResponseModel", currentItem);
            context.startActivity(intent);
        }
    }

}
