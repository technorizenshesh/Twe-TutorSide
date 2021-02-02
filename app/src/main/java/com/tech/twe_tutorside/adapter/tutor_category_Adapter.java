package com.tech.twe_tutorside.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.model.TutorCategoryModel;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class tutor_category_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TutorCategoryModel> modelList;
    private tutor_category_Adapter.OnItemClickListener mItemClickListener;

    public tutor_category_Adapter(Context context, ArrayList<TutorCategoryModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<TutorCategoryModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public tutor_category_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tutor_category, viewGroup, false);
        return new tutor_category_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof tutor_category_Adapter.ViewHolder) {

            final TutorCategoryModel model = getItem(position);

            final tutor_category_Adapter.ViewHolder genericViewHolder = (tutor_category_Adapter.ViewHolder) holder;

            String image_URL = model.getImage().toString();

            if(image_URL !=null)
            {
                Glide.with(mContext).load(image_URL).placeholder(R.drawable.aa)
                        .into(genericViewHolder.img_category);
            }
            //genericViewHolder.txt_gender.setText(model.getName()); img_category

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final tutor_category_Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private TutorCategoryModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, TutorCategoryModel model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_category;

        public ViewHolder(final View itemView) {
            super(itemView);

         this.img_category = itemView.findViewById(R.id.img_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }

}

