package com.example.cmo_lms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.LanguageUtil;

import java.util.Map;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerViewHolder> {
    private final String[] outerdata;
    private final Context context;
    private final Map<String, Map<String, Integer>> innerDataMap;

    Map<String, Integer> innerDataList;

    public InnerRecyclerViewAdapter(String[] outer_data, Map<String, Map<String, Integer>> innerMap, Context context) {
        this.outerdata = outer_data;
        this.innerDataMap = innerMap;
        this.context = context;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_idev_items, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        if (innerDataList != null) {
            Log.e("innerrecycler data :", innerDataList.toString());
            if (LanguageUtil.getCurrentLanguage().equals("en")) {
                String receivedValue = String.valueOf(innerDataList.get("Count"));
                String disposedValue = String.valueOf(innerDataList.get("Closed Count"));
                String acceptedValue = String.valueOf(innerDataList.get("Accepted Closed Count"));
                String rejectedValue = String.valueOf(innerDataList.get("Rejected Closed Count"));
                String pendingValue = String.valueOf(innerDataList.get("Pending Count"));

                holder.setDetailsData("Received", receivedValue);
                holder.setDetailsData("Disposed", disposedValue);
                holder.setDetailsData("Accepted", acceptedValue);
                holder.setDetailsData("Rejected", rejectedValue);
                holder.setDetailsData("Pending", pendingValue);
            } else {
                String receivedValue = String.valueOf(innerDataList.get("ಎಣಿಕೆ"));
                String disposedValue = String.valueOf(innerDataList.get("ಮುಚ್ಚಿದ ಎಣಿಕೆ"));
                String acceptedValue = String.valueOf(innerDataList.get("ಮುಚ್ಚಿದ ಎಣಿಕೆಯನ್ನು ಸ್ವೀಕರಿಸಲಾಗಿದೆ"));
                String rejectedValue = String.valueOf(innerDataList.get("ಮುಚ್ಚಿದ ಎಣಿಕೆಯನ್ನು ತಿರಸ್ಕರಿಸಲಾಗಿದೆ"));
                String pendingValue = String.valueOf(innerDataList.get("ಬಾಕಿಯಿರುವ ಎಣಿಕೆ"));

                holder.setDetailsData("Received", receivedValue);
                holder.setDetailsData("Disposed", disposedValue);
                holder.setDetailsData("Accepted", acceptedValue);
                holder.setDetailsData("Rejected", rejectedValue);
                holder.setDetailsData("Pending", pendingValue);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        TextView recived_textView, disposed_textView, accepted_textView, rejected_textView, pending_textView;
        ConstraintLayout constraintLayout;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            recived_textView = itemView.findViewById(R.id.received_number_text);
            disposed_textView = itemView.findViewById(R.id.disposed_number_text);
            accepted_textView = itemView.findViewById(R.id.accepted_number_text);
            rejected_textView = itemView.findViewById(R.id.rejected_number_text);
            pending_textView = itemView.findViewById(R.id.pending_number_text);
            constraintLayout = itemView.findViewById(R.id.const_data);
        }

        private void setDetailsData(String key, String value) {
            switch (key) {
                case "Received":
                    recived_textView.setText(value);
                    break;
                case "Disposed":
                    disposed_textView.setText(value);
                    break;
                case "Accepted":
                    accepted_textView.setText(value);
                    break;
                case "Rejected":
                    rejected_textView.setText(value);
                    break;
                case "Pending":
                    pending_textView.setText(value);
                    break;
            }
        }
    }

    public void updateInnerData(Map<String, Integer> innerData) {
        this.innerDataList = innerData;
        notifyDataSetChanged();
    }
}
