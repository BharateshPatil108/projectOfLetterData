package com.example.cmo_lms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;

import java.util.Map;

public class DepartmentWiseInnerAdapter extends RecyclerView.Adapter<DepartmentWiseInnerAdapter.DepartmentInnerViewHolder> {

    private final String[] dep_outer_data;
    private final Context context;
    private final Map<String, Integer> dep_innerDataList;

    public DepartmentWiseInnerAdapter(String[] dep_outerdata, Map<String, Integer> dep_innerList, Context context) {
        this.dep_outer_data = dep_outerdata;
        this.dep_innerDataList = dep_innerList;
        this.context = context;
    }

    @NonNull
    @Override
    public DepartmentInnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_idev_items, parent, false);
        return new DepartmentInnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentInnerViewHolder holder, int position) {
        //holder.dep_innerTextView.setText(dep_innerDataList.get(position));

        String receivedValue = String.valueOf(dep_innerDataList.get("Received"));
        String disposedValue = String.valueOf(dep_innerDataList.get("Disposed"));
        String acceptedValue = String.valueOf(dep_innerDataList.get("Accepted"));
        String rejectedValue = String.valueOf(dep_innerDataList.get("Rejected"));
        String pendingValue = String.valueOf(dep_innerDataList.get("Pending"));

        // Set the values to the respective TextViews
        holder.setDetailsData("Received", receivedValue);
        holder.setDetailsData("Disposed", disposedValue);
        holder.setDetailsData("Accepted", acceptedValue);
        holder.setDetailsData("Rejected", rejectedValue);
        holder.setDetailsData("Pending", pendingValue);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class DepartmentInnerViewHolder extends RecyclerView.ViewHolder {
        TextView recived_textView, disposed_textView, accepted_textView, rejected_textView, pending_textView;
        ConstraintLayout constraintLayout;

        public DepartmentInnerViewHolder(@NonNull View itemView) {
            super(itemView);
            recived_textView = itemView.findViewById(R.id.received_number_text);
            disposed_textView = itemView.findViewById(R.id.disposed_number_text);
            accepted_textView = itemView.findViewById(R.id.accepted_number_text);
            rejected_textView = itemView.findViewById(R.id.rejected_number_text);
            pending_textView = itemView.findViewById(R.id.pending_number_text);
            constraintLayout = itemView.findViewById(R.id.const_data);
        }

        private void setDetailsData(String key, String value) {
            // Set data to the detailed layout's TextViews based on the key-value pair
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
}

