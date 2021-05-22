package com.tech.twe_tutorside.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.BuildingProfiActivity;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.activity.LoginActivity;
import com.tech.twe_tutorside.activity.TrackingPageActivity;
import com.tech.twe_tutorside.activity.TutorTrackngActivity;
import com.tech.twe_tutorside.fragments.HomeFragment;
import com.tech.twe_tutorside.model.CurrentRequestModal;
import com.tech.twe_tutorside.model.GetRequestModel;
import com.tech.twe_tutorside.model.GetRequestModelData;
import com.tech.twe_tutorside.onClickLisner.onCliklisnermethod;
import com.tech.twe_tutorside.utils.RetrofitClients;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrentRequestAdapter extends RecyclerView.Adapter<CurrentRequestAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetRequestModelData> currentRequestModalList = new ArrayList<>();


    onCliklisnermethod listner;

    public CurrentRequestAdapter(Context context, ArrayList currentRequestModal,onCliklisnermethod listner) {
        this.context = context;
        this.currentRequestModalList = currentRequestModal;
        this.listner = listner;
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

                listner.click(position,"Accept");
              //  context.startActivity(new Intent(context, TutorTrackngActivity.class));
            }
        });
        holder.reject_reuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listner.click(position,"Reject");
              //  context.startActivity(new Intent(context, TutorTrackngActivity.class));
            }
        });

      // holder.currentName_reqItemId.setText(currentRequestModalList.get(position).getStudentDetails().getUsername());
        holder.txt_order_id.setText("Order Id : "+currentRequestModalList.get(position).getId());
      //  holder.txt_subject.setText(currentRequestModalList.get(position).getStudentDetails().getSubject());
    //  holder.txt_requst_for.setText(currentRequestModalList.get(position).getStudentDetails().getTutorFor());
     //   holder.txt_request_duration_time.setText(currentRequestModalList.get(position).getStudentDetails().getWhereToTeach());
        holder.txt_start_date.setText("Starting date : "+currentRequestModalList.get(position).getStartdate());

       // String Dob= currentRequestModalList.get(position).getStudentDetails().getDob();

      //  String ProfileImage= currentRequestModalList.get(position).getStudentDetails().getProfileImage();

      /*  if(ProfileImage !=null)
        {
            Glide.with(context).load(ProfileImage).circleCrop().circleCrop().placeholder(R.drawable.home_banner3)
                    .into(holder.img_profile);
        }

        if(Dob!=null && !Dob.equalsIgnoreCase(""))
        {
            String datrrr[] = Dob.split("-");
            String CalcuAge= getAge(Integer.parseInt(datrrr[2]),Integer.parseInt(datrrr[1]),Integer.parseInt(datrrr[0]));
            holder.txt_DOB.setText(CalcuAge+" Years");
        }*/

    }

    @Override
    public int getItemCount() {
        return currentRequestModalList.size();
    }

    private GetRequestModelData getItem(int position) {
        return currentRequestModalList.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView trackId;
        public ImageView reject_reuest;
        public TextView currentName_reqItemId;
        public TextView txt_DOB;
        public TextView txt_gender;
        public TextView txt_order_id;
        public TextView txt_subject;
        public TextView txt_classes;
        public TextView txt_requst_for;
        public TextView txt_request_duration_time;
        public TextView txt_start_date;


        public CircleImageView item_enquiryImg;
        public ImageView img_profile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trackId = itemView.findViewById(R.id.trackId);
            reject_reuest = itemView.findViewById(R.id.reject_reuest);
            currentName_reqItemId = itemView.findViewById(R.id.currentName_reqItemId);
            txt_DOB = itemView.findViewById(R.id.txt_DOB);
            txt_gender = itemView.findViewById(R.id.txt_gender);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_subject = itemView.findViewById(R.id.txt_subject);
            txt_classes = itemView.findViewById(R.id.txt_classes);
            txt_requst_for = itemView.findViewById(R.id.txt_requst_for);
            txt_request_duration_time = itemView.findViewById(R.id.txt_request_duration_time);
            txt_start_date = itemView.findViewById(R.id.txt_start_date);
            img_profile = itemView.findViewById(R.id.img_profile);

        }
    }


    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

}
