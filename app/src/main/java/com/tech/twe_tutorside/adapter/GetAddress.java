package com.tech.twe_tutorside.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.model.getShiipingAddressData;
import com.tech.twe_tutorside.utils.SessionManager;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class GetAddress extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<getShiipingAddressData> modelList;
    private GetAddress.OnItemClickListener mItemClickListener;
    private View promptsView;
    private AlertDialog alertDialog;
    private Button btn_no;
    private Button btn_yes;
    Fragment fragment;
    private SessionManager sessionManager;
    boolean isLike=true;

    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public GetAddress(Context context, ArrayList<getShiipingAddressData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<getShiipingAddressData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public GetAddress.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shippping_address, viewGroup, false);
        return new GetAddress.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof GetAddress.ViewHolder) {

            final getShiipingAddressData model = getItem(position);

            final GetAddress.ViewHolder genericViewHolder = (GetAddress.ViewHolder) holder;

           // String image_URL = model.getImage().toString();
       //   genericViewHolder.txt_city.setText(model.getState()+","+model.getCountry());
          // genericViewHolder.txt_address.setText(model.getStreetOne()+","+model.getStreetwo()+""+model.getCity());
          // genericViewHolder.txt_address_type.setText(model.getStreetOne()+","+model.getStreetwo()+""+model.getCity());

            genericViewHolder.checkBox.setTag(new Integer(position));
            //for default check in first item
            if(position == 0 &&  modelList.get(0).isSelected() && genericViewHolder.checkBox.isChecked())
            {
                lastChecked = genericViewHolder.checkBox;
                lastCheckedPos = 0;
            }

            genericViewHolder.checkBox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //CheckBox cb = (CheckBox)v;
                    int clickedPos = ((Integer)genericViewHolder.checkBox.getTag()).intValue();

                    if(genericViewHolder.checkBox.isChecked())
                    {
                        if(lastChecked != null)
                        {
                            lastChecked.setChecked(false);
                            modelList.get(lastCheckedPos).setSelected(false);
                        }

                        lastChecked = genericViewHolder.checkBox;
                        lastCheckedPos = clickedPos;
                    }
                    else
                        lastChecked = null;

                    modelList.get(clickedPos).setSelected(genericViewHolder.checkBox.isChecked());
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final GetAddress.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private getShiipingAddressData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, getShiipingAddressData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_address;
        private TextView txt_address_type;
        private CardView RR_select;
        private CheckBox checkBox;

        public ViewHolder(final View itemView) {
            super(itemView);

           this.txt_address = itemView.findViewById(R.id.txt_address);
           this.txt_address_type = itemView.findViewById(R.id.txt_address_type);
           this.checkBox = itemView.findViewById(R.id.img_check);
           this.RR_select = itemView.findViewById(R.id.RR_select);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }
/*
    public void loadFragment(Fragment fragment) {
        // load fragment
        ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }*/

}

