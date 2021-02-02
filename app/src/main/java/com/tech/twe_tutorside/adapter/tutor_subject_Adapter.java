package com.tech.twe_tutorside.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.model.TutorSubDataCategory;
import com.tech.twe_tutorside.model.TutorSubjectDataModel;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class tutor_subject_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TutorSubjectDataModel> modelList;
    private tutor_subject_Adapter.OnItemClickListener mItemClickListener;

    public tutor_subject_Adapter(Context context, ArrayList<TutorSubjectDataModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<TutorSubjectDataModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public tutor_subject_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tutor_subject, viewGroup, false);
        return new tutor_subject_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof tutor_subject_Adapter.ViewHolder) {

            final TutorSubjectDataModel model = getItem(position);

            final tutor_subject_Adapter.ViewHolder genericViewHolder = (tutor_subject_Adapter.ViewHolder) holder;

           /* String image_URL = model.getImage().toString();

            if(image_URL !=null)
            {
                Glide.with(mContext).load(image_URL).placeholder(R.drawable.aa)
                        .into(genericViewHolder.img_category);
            }*/
          genericViewHolder.check_subject.setText(model.getName());

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final tutor_subject_Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private TutorSubjectDataModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, TutorSubjectDataModel model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private CheckBox check_subject;

        public ViewHolder(final View itemView) {
            super(itemView);

        this.check_subject = itemView.findViewById(R.id.check_subject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }

}

