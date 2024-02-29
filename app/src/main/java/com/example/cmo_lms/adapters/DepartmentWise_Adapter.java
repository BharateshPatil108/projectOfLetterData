package com.example.cmo_lms.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmo_lms.R;

import java.util.Map;

public class DepartmentWise_Adapter extends RecyclerView.Adapter<DepartmentWise_Adapter.ViewHolder> {
    private final String[] dep_dummy_data;
    private final Map<String, Integer> dep_inner_dummy_data;
    Context context;
    private int lastPosition = -1;
    private OnItemClickListener onItemClickListener;
    private OnImageClickListener imageClickListener;
    private final boolean[] isExpandedArray;

    public DepartmentWise_Adapter(String[] array, Map<String, Integer> innerDataList, Context context) {
        this.dep_dummy_data = array;
        this.dep_inner_dummy_data = innerDataList;
        this.context = context;
        this.isExpandedArray = new boolean[array.length];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indiv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sub_indiv_item.setText(dep_dummy_data[position]);
        setAnimation(holder.itemView, position);

        // Set up the inner RecyclerView
        DepartmentWiseInnerAdapter sub_innerRecycleAdapter = new DepartmentWiseInnerAdapter(dep_dummy_data, dep_inner_dummy_data, context);
        holder.sub_inner_recyclerView.setAdapter(sub_innerRecycleAdapter);
        holder.sub_inner_recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(dep_dummy_data[adapterPosition], adapterPosition);
                }
                if (imageClickListener != null) {
                    imageClickListener.onImageClick(position, v);
                }
            }
        });

        holder.sub_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpandedArray[position] = !isExpandedArray[position];
                holder.sub_inner_linear_lay.setVisibility(isExpandedArray[position] ? View.VISIBLE : View.GONE);
                holder.sub_dropdown.setBackgroundResource(isExpandedArray[position] ? R.drawable.keyboard_arrow_up_24 : R.drawable.arrow_down_24);
            }
        });
        holder.sub_dropdown.setBackgroundResource(isExpandedArray[position] ? R.drawable.keyboard_arrow_up_24 : R.drawable.arrow_down_24);
    }

    @Override
    public int getItemCount() {
        return dep_dummy_data.length;
    }

    private void setAnimation(View viewToAnimation, int position) {
        if (position > lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.recyle_anim);
            viewToAnimation.startAnimation(slideIn);
            lastPosition = position;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.imageClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String item, int position);
    }

    public interface OnImageClickListener {
        void onImageClick(int position, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_indiv_item;
        Button sub_dropdown;
        LinearLayout sub_inner_linear_lay;
        RecyclerView sub_inner_recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sub_indiv_item = itemView.findViewById(R.id.txt_indivitem);
            sub_dropdown = itemView.findViewById(R.id.btn_drop_down);
            sub_inner_linear_lay = itemView.findViewById(R.id.lin_lay_indiv);
            sub_inner_recyclerView = itemView.findViewById(R.id.recycle_indiv);
        }
    }
}
