package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.activity.LoginActivity;
import com.tech.twe_tutorside.adapter.tutor_category_Adapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.TutorCategoryModel;
import com.tech.twe_tutorside.model.TutorCategory_Model;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment1 extends Fragment {

    FragmentListener listener;
    RelativeLayout card_category1;

    private SessionManager sessionManager;
    private String android_id;
    private ProgressBar progressBar;

    private RecyclerView recycler_get_tutor_category;
    tutor_category_Adapter mAdapter;
        private ArrayList<TutorCategoryModel> modelList = new ArrayList<>();

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

    public CategoryFragment1(FragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category1, container, false);

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


        card_category1=view.findViewById(R.id.card_category1);
        card_category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(new CategoryFragment2(listener));
            }
        });

        progressBar=view.findViewById(R.id.progressBar);
        recycler_get_tutor_category=view.findViewById(R.id.recycler_get_tutor_category);
        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isNetworkAvailable()) {

            //LL_submit.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            tutorCategory();

        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void tutorCategory() {

        Call<TutorCategory_Model> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_tutor_category();

        call.enqueue(new Callback<TutorCategory_Model>() {
            @Override
            public void onResponse(Call<TutorCategory_Model> call, Response<TutorCategory_Model> response) {

                try {

                    TutorCategory_Model finallyPr = response.body();

                    progressBar.setVisibility(View.GONE);

                    //JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = finallyPr.getStatus ();
                    String message = finallyPr.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<TutorCategoryModel>) finallyPr.getResult();

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
            public void onFailure(Call<TutorCategory_Model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            //    LL_submit.setEnabled(true);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter(ArrayList<TutorCategoryModel> modelList) {

        mAdapter = new tutor_category_Adapter(getActivity(), modelList);

        recycler_get_tutor_category.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recycler_get_tutor_category.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recycler_get_tutor_category.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new tutor_category_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TutorCategoryModel model) {

                Preference.save(getActivity(),Preference.KEY_tutor_category_id,model.getId());

                listener.click(new CategoryFragment2(listener));
            }
        });
    }
}