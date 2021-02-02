package com.tech.twe_tutorside.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.tutor_category_Adapter;
import com.tech.twe_tutorside.adapter.tutor_sub_category_Adapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.TutorCategoryModel;
import com.tech.twe_tutorside.model.TutorCategory_Model;
import com.tech.twe_tutorside.model.TutorSubCategory;
import com.tech.twe_tutorside.model.TutorSubDataCategory;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment2 extends Fragment {

    FragmentListener listener;
    CheckBox category_layoutcard2;

    private SessionManager sessionManager;
    private String android_id;
    private ProgressBar progressBar;

    private RecyclerView recycler_get__sub_tutor_category;
    tutor_sub_category_Adapter mAdapter;
    private ArrayList<TutorSubDataCategory> modelList = new ArrayList<>();

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

    public CategoryFragment2(FragmentListener listener) {
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
        View view= inflater.inflate(R.layout.fragment_category2, container, false);

        category_layoutcard2=view.findViewById(R.id.sub_category1);
        progressBar=view.findViewById(R.id.progressBar);
        recycler_get__sub_tutor_category=view.findViewById(R.id.recycler_get__sub_tutor_category);
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


        category_layoutcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.click(new CategoryFragment3(listener));

            }
        });


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

      String Category_id =  Preference.get(getActivity(),Preference.KEY_tutor_category_id);

        Call<TutorSubCategory> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_tutor_sub_category(Category_id);

        call.enqueue(new Callback<TutorSubCategory>() {
            @Override
            public void onResponse(Call<TutorSubCategory> call, Response<TutorSubCategory> response) {

                try {

                    TutorSubCategory finallyPr = response.body();

                    progressBar.setVisibility(View.GONE);

                    //JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = finallyPr.getStatus ();
                    String message = finallyPr.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<TutorSubDataCategory>) finallyPr.getResult();

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
            public void onFailure(Call<TutorSubCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //    LL_submit.setEnabled(true);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter(ArrayList<TutorSubDataCategory> modelList) {

        mAdapter = new tutor_sub_category_Adapter(getActivity(), modelList);

        recycler_get__sub_tutor_category.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recycler_get__sub_tutor_category.setLayoutManager(linearLayoutManager);

        recycler_get__sub_tutor_category.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new tutor_sub_category_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TutorSubDataCategory model) {

                Preference.save(getActivity(),Preference.KEY_tutor_category_sub_id,model.getId());

                listener.click(new CategoryFragment3(listener));

            }
        });
    }
}