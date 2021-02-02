package com.tech.twe_tutorside.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.model.SaloonSpecialistModel;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class AvailableSlot extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<SaloonSpecialistModel> modelList;
    private OnItemClickListener mItemClickListener;



    public AvailableSlot(Context context, ArrayList<SaloonSpecialistModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<SaloonSpecialistModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_avalible_slot, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final SaloonSpecialistModel model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

           genericViewHolder.txt_time.setText(modelList.get(position).getName());

            genericViewHolder.LL_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    model.setSelected(!model.isSelected());

                    if(model.isSelected())
                    {
                        genericViewHolder.LL_time.setBackgroundResource(R.drawable.edit_background_red);

                    }else
                    {
                        genericViewHolder.LL_time.setBackgroundResource(R.drawable.edit_background);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private SaloonSpecialistModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, SaloonSpecialistModel model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView user_profile;
        private TextView txt_name;
        private TextView txt_time;
        private LinearLayout LL_time;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txt_time=itemView.findViewById(R.id.txt_time);
            this.LL_time=itemView.findViewById(R.id.LL_time);
            //this.txt_name=itemView.findViewById(R.id.txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }



}

