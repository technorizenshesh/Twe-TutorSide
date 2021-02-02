package com.tech.twe_tutorside.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.fragments.CategoryFragment3;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.TutorCategoryModel;
import com.tech.twe_tutorside.model.TutorSubDataCategory;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class tutor_sub_category_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TutorSubDataCategory> modelList;
    private tutor_sub_category_Adapter.OnItemClickListener mItemClickListener;


    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public tutor_sub_category_Adapter(Context context, ArrayList<TutorSubDataCategory> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<TutorSubDataCategory> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public tutor_sub_category_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tutor_sub_category, viewGroup, false);

        return new tutor_sub_category_Adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof tutor_sub_category_Adapter.ViewHolder) {

            final TutorSubDataCategory model = getItem(position);

            final tutor_sub_category_Adapter.ViewHolder genericViewHolder = (tutor_sub_category_Adapter.ViewHolder) holder;

            genericViewHolder.check_box_category.setText(model.getSubCatName());

            genericViewHolder.check_box_category.setTag(new Integer(position));
            //for default check in first item
           /* if(position == 0 &&  modelList.get(0).isSelected() && genericViewHolder.check_box_category.isChecked())
            {
                lastChecked = genericViewHolder.check_box_category;
                lastCheckedPos = 0;
            }

            genericViewHolder.check_box_category.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //CheckBox cb = (CheckBox)v;
                    int clickedPos = ((Integer)genericViewHolder.check_box_category.getTag()).intValue();

                    if(genericViewHolder.check_box_category.isChecked())
                    {
                        if(lastChecked != null)
                        {
                            lastChecked.setChecked(false);
                            modelList.get(lastCheckedPos).setSelected(false);
                        }

                        lastChecked = genericViewHolder.check_box_category;
                        lastCheckedPos = clickedPos;
                    }
                    else
                        lastChecked = null;

                    modelList.get(clickedPos).setSelected(genericViewHolder.check_box_category.isChecked());
                }
            });*/


        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final tutor_sub_category_Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private TutorSubDataCategory getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, TutorSubDataCategory model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView check_box_category;

        public ViewHolder(final View itemView) {
            super(itemView);

        this.check_box_category = itemView.findViewById(R.id.check_box_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }

}

