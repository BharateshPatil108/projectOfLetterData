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

public class ConstituencyWise_MLC_Category_Adapter extends RecyclerView.Adapter<ConstituencyWise_MLC_Category_Adapter.ViewHolder> {
    private final String[] const_mlc_cat_dummy_data;
    private final Map<String, Integer> mlc_cat_inner_dummy_data;
    Context context;
    private int lastPosition = -1;
    private OnItemClickListener onItemClickListener;
    private OnImageClickListener imageClickListener;
    private final boolean[] isExpandedArray;

    public ConstituencyWise_MLC_Category_Adapter(String[] array, Map<String, Integer> innerDataList, Context context) {
        this.const_mlc_cat_dummy_data = array;
        this.mlc_cat_inner_dummy_data = innerDataList;
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
        holder.const_mlc_cat_detail_indiv_item.setText(const_mlc_cat_dummy_data[position]);
        setAnimation(holder.itemView, position);

        // Set up the inner RecyclerView
        ConstituencyWise_MLC_Category_Inner_Adapter constituencyWiseMlcCategoryInnerAdapter = new ConstituencyWise_MLC_Category_Inner_Adapter(const_mlc_cat_dummy_data, mlc_cat_inner_dummy_data, context);
        holder.mlc_cat_inner_recyclerView.setAdapter(constituencyWiseMlcCategoryInnerAdapter);
        holder.mlc_cat_inner_recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(const_mlc_cat_dummy_data[adapterPosition], adapterPosition);
                }
                if (imageClickListener != null) {
                    imageClickListener.onImageClick(position, v);
                }
            }
        });

        holder.mlc_cat_deatil_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isExpandedArray[position] = !isExpandedArray[position];
                holder.mlc_cat_inner_linear_lay.setVisibility(isExpandedArray[position] ? View.VISIBLE : View.GONE);
                holder.mlc_cat_deatil_dropdown.setBackgroundResource(isExpandedArray[position] ? R.drawable.keyboard_arrow_up_24 : R.drawable.arrow_down_24);

            }
        });
        holder.mlc_cat_deatil_dropdown.setBackgroundResource(isExpandedArray[position] ? R.drawable.keyboard_arrow_up_24 : R.drawable.arrow_down_24);
    }

    @Override
    public int getItemCount() {
        return const_mlc_cat_dummy_data.length;
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
        TextView const_mlc_cat_detail_indiv_item;
        Button mlc_cat_deatil_dropdown;
        LinearLayout mlc_cat_inner_linear_lay;
        RecyclerView mlc_cat_inner_recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            const_mlc_cat_detail_indiv_item = itemView.findViewById(R.id.txt_indivitem);
            mlc_cat_deatil_dropdown = itemView.findViewById(R.id.btn_drop_down);
            mlc_cat_inner_linear_lay = itemView.findViewById(R.id.lin_lay_indiv);
            mlc_cat_inner_recyclerView = itemView.findViewById(R.id.recycle_indiv);
        }
    }
}
