package com.tech.twe_tutorside.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.TrackingPageActivity;
import com.tech.twe_tutorside.activity.TutorTrackngActivity;
import com.tech.twe_tutorside.model.CurrentRequestModal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CurrentRequestAdapter extends RecyclerView.Adapter<CurrentRequestAdapter.ViewHolder> {


    private Context context;
    private List<CurrentRequestModal> currentRequestModalList = new ArrayList<>();


    public CurrentRequestAdapter(Context context, List currentRequestModal) {
        this.context = context;
        this.currentRequestModalList = currentRequestModal;
    }


    @NonNull
    @Override
    public CurrentRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_request_itemlist, parent,
                false);
        CurrentRequestAdapter.ViewHolder viewHolder = new CurrentRequestAdapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CurrentRequestAdapter.ViewHolder holder, int position) {

        holder.trackId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, TutorTrackngActivity.class));
            }
        });

    }


    @Override
    public int getItemCount() {
       /* if (currentRequestModalList == null) {

            return 0;
        } else {
            return currentRequestModalList.size();
        }*/

        return 3;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView trackId;

        public CircleImageView item_enquiryImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trackId = itemView.findViewById(R.id.trackId);


        }
    }

}
