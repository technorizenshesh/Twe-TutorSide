package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.BuildingProfiActivity;
import com.tech.twe_tutorside.activity.CategorySelectActivity;
import com.tech.twe_tutorside.activity.FeeCalculatorActivity;
import com.tech.twe_tutorside.activity.TimeSelectedWeekView;
import com.tech.twe_tutorside.adapter.tutor_sub_category_Adapter;
import com.tech.twe_tutorside.adapter.tutor_subject_Adapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.TutorSubCategory;
import com.tech.twe_tutorside.model.TutorSubDataCategory;
import com.tech.twe_tutorside.model.TutorSubjectDataModel;
import com.tech.twe_tutorside.model.TutorSubjectMode;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment3 extends Fragment {

    FragmentListener listener;
    CheckBox category_layoutcard3;


    private String teach_distance="";
    private String TimeZone="";
    private String where_to_teach="";
    private String about ="";
    private String dob ="";
    private String education ="";
    private String language ="";
    private String Affilations ="";
    private String Awards ="";

    private String monday="";
    private  String tuesday="";
    private  String wednesday="";
    private  String thursday="";
    private  String friday="";
    private String saturday="";
    private String sunday="";
    private String location ="";
    private String gender ="";
    private String certification ="";



    private SessionManager sessionManager;
    private String android_id;
    private ProgressBar progressBar;
    private LinearLayout LL_choose_sub;
    private RecyclerView recycler_choose_subject;
    tutor_subject_Adapter mAdapter;
    private ArrayList<TutorSubjectDataModel> modelList = new ArrayList<>();

    public CategoryFragment3(FragmentListener listener) {
        this.listener=listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category3, container, false);

        category_layoutcard3=view.findViewById(R.id.category_layoutcard3);
        progressBar=view.findViewById(R.id.progressBar);
        recycler_choose_subject=view.findViewById(R.id.recycler_choose_subject);
        LL_choose_sub=view.findViewById(R.id.LL_choose_sub);
        sessionManager = new SessionManager(getActivity());

        modelList.clear();

        Bundle args = getArguments();
        if(args!=null)
        {
            about = args.getString("about").toString();
            dob =  args.getString("dob").toString();
            education =args.getString("education").toString();
            language = args.getString("language").toString();
            Affilations = args.getString("Affilations").toString();
            Awards =  args.getString("Awards").toString();
            where_to_teach =args.getString("where_to_teach").toString();
            teach_distance = args.getString("teach_distance").toString();
            location = args.getString("location").toString();
            gender =  args.getString("gender").toString();
            certification =  args.getString("certification").toString();
            TimeZone =  args.getString("TimeZone").toString();
            monday =  args.getString("mondayTime").toString();
            tuesday =  args.getString("tuesday").toString();
            wednesday =  args.getString("wednesday").toString();
            thursday =  args.getString("thursday").toString();
            friday =  args.getString("friday").toString();
            saturday = args.getString("saturday").toString();
            sunday = args.getString("sunday").toString();
        }


        LL_choose_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FeeCalculatorActivity.class);
                intent.putExtra("about",about);
                intent.putExtra("dob",dob);
                intent.putExtra("education",education);
                intent.putExtra("language",language);
                intent.putExtra("Affilations",Affilations);
                intent.putExtra("Awards",Awards);
                intent.putExtra("where_to_teach",where_to_teach);
                intent.putExtra("teach_distance",teach_distance);
                intent.putExtra("teach_distance",teach_distance);
                intent.putExtra("location",location);
                intent.putExtra("gender",gender);
                intent.putExtra("certification",certification);
                intent.putExtra("TimeZone",TimeZone);
                intent.putExtra("mondayTime",""+monday);
                intent.putExtra("tuesday",""+tuesday);
                intent.putExtra("wednesday",""+wednesday);
                intent.putExtra("thursday",""+thursday);
                intent.putExtra("friday",""+friday);
                intent.putExtra("saturday",""+saturday);
                intent.putExtra("sunday",""+sunday);
                intent.putExtra("subject","math");
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
               /* startActivity(new Intent(getActivity(), FeeCalculatorActivity.class));
                Objects.requireNonNull(getActivity()).finish();*/

            }
        });

        category_layoutcard3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FeeCalculatorActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        if (sessionManager.isNetworkAvailable()) {

            //LL_submit.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            tutorSubject();

        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


        return view;
    }


    private void tutorSubject() {

        String Category_id =  Preference.get(getActivity(),Preference.KEY_tutor_category_id);
        String Sub_Category_id =  Preference.get(getActivity(),Preference.KEY_tutor_category_sub_id);

        Call<TutorSubjectMode> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_tutor_subject(Category_id,Sub_Category_id);

        call.enqueue(new Callback<TutorSubjectMode>() {
            @Override
            public void onResponse(Call<TutorSubjectMode> call, Response<TutorSubjectMode> response) {

                try {

                    TutorSubjectMode finallyPr = response.body();

                    progressBar.setVisibility(View.GONE);

                    //JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = finallyPr.getStatus ();
                    String message = finallyPr.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<TutorSubjectDataModel>) finallyPr.getResult();

                        setAdapter(modelList);

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        //LL_submit.setEnabled(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<TutorSubjectMode> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //    LL_submit.setEnabled(true);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter(ArrayList<TutorSubjectDataModel> modelList) {

        mAdapter = new tutor_subject_Adapter(getActivity(), modelList);

        recycler_choose_subject.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recycler_choose_subject.setLayoutManager(linearLayoutManager);

        recycler_choose_subject.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new tutor_subject_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TutorSubjectDataModel model) {

                startActivity(new Intent(getActivity(), FeeCalculatorActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}