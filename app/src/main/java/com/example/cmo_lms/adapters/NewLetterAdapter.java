package com.example.cmo_lms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;

import java.util.Map;

public class NewLetterAdapter extends RecyclerView.Adapter<NewLetterAdapter.MyViewHolder> {
    private final Map<String, String> dataMap;
    private int lastPosition = -1;

    Context context;

    public NewLetterAdapter(Context context_anim, Map<String, String> dataMap) {
        this.context = context_anim;
        this.dataMap = dataMap;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.letter_indiv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
        private final TextView textViewName;
        private final TextView textViewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.txt_indivitem);
            textViewDescription = itemView.findViewById(R.id.detail_of_indivitem);
        }
        public void bind(Map<String, String> dataMap, int position) {

            String key = (String) dataMap.keySet().toArray()[position];

            String value = dataMap.get(key);

            textViewName.setText(key);
            textViewDescription.setText(value);
        }

    }
}
