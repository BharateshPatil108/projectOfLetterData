package com.example.cmo_lms.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private final String[] outer_array;
    private final Map<String, Map<String, Integer>> innerDataMap;
    Context context;
    private int lastPosition = -1;
    private OnItemClickListener onItemClickListener;
    private OnImageClickListener imageClickListener;
    InnerRecyclerViewAdapter innerRecycleAdapter;
    private final boolean[] isExpandedArray;

    public RecycleAdapter(String[] array, Map<String, Map<String, Integer>> innerData_List, Context context) {
        this.outer_array = array;
        this.innerDataMap = innerData_List;
        this.context = context;
        this.isExpandedArray = new boolean[array.length];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indiv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.indivItem.setText(outer_array[position]);
        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(outer_array[adapterPosition], adapterPosition);
                }
                if (imageClickListener != null) {
                    imageClickListener.onImageClick(position, v);
                }
            }
        });

        holder.arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clickedName = outer_array[position];
                innerRecycleAdapter = new InnerRecyclerViewAdapter(outer_array, innerDataMap, context);
                holder.innerRecyclerView.setAdapter(innerRecycleAdapter);
                holder.innerRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                Log.e("CLicked no", clickedName);
                Map<String, Integer> innerData = innerDataMap.get(clickedName);
                innerRecycleAdapter.updateInnerData(innerData);
                Log.e("CLicked name data is: ", clickedName + " " + innerData.toString());
                // Update the inner adapter with the new data

                isExpandedArray[position] = !isExpandedArray[position];
                holder.inner_linearLayout.setVisibility(isExpandedArray[position] ? View.VISIBLE : View.GONE);
                holder.arrowIcon.setBackgroundResource(isExpandedArray[position] ? R.drawable.keyboard_arrow_up_24 : R.drawable.arrow_down_24);
            }
        });
        holder.arrowIcon.setBackgroundResource(isExpandedArray[position] ? R.drawable.keyboard_arrow_up_24 : R.drawable.arrow_down_24);
    }

    @Override
    public int getItemCount() {
        return outer_array.length;
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
        TextView indivItem;
        Button arrowIcon;
        LinearLayout inner_linearLayout;
        RecyclerView innerRecyclerView;

        public ViewHolder(View itemview) {
            super(itemview);
            indivItem = itemView.findViewById(R.id.txt_indivitem);
            arrowIcon = itemView.findViewById(R.id.btn_drop_down);
            innerRecyclerView = itemView.findViewById(R.id.recycle_indiv);
            inner_linearLayout = itemview.findViewById(R.id.lin_lay_indiv);
        }
    }


}
