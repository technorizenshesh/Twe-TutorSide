package com.tech.twe_tutorside.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.adapter.CurrentRequestAdapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.CurrentRequestModal;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  implements FragmentListener, View.OnClickListener {


    FragmentListener listener;
    TextView tab_txt1,tab_txt2;
    TextView txt_name;

    TextView viewAll_homeId;

    private List<CurrentRequestModal> currentRequestModalList;

    private RecyclerView current_Request_recyclerview;
    private RecyclerView.Adapter current_Request_mAdapter;

    public HomeFragment(FragmentListener listener) {
        this.listener = listener;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        viewAll_homeId=view.findViewById(R.id.viewAll_homeId);
        viewAll_homeId.setOnClickListener(this);

        String UserName = Preference.get(getActivity(), Preference.KEY_username);

        tab_txt1 = view.findViewById(R.id.tab_txt1);
        txt_name = view.findViewById(R.id.txt_name);
        tab_txt2 = view.findViewById(R.id.tab_txt2);
        txt_name.setText(UserName);
        // adding a listner
        tab_txt1.setOnClickListener(this);
        tab_txt2.setOnClickListener(this);

        current_Request_recyclerview = view.findViewById(R.id.currentRequest_recyclerview);
        current_Request_recyclerview.setHasFixedSize(true);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL,
                false);

        currentRequestModalList=new ArrayList<>();

//        currentRequestModalList.add(new CurrentRequestModal("","","","",""));


        current_Request_recyclerview.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

        current_Request_mAdapter = new CurrentRequestAdapter(getActivity(), currentRequestModalList);
       // current_Request_recyclerview.removeAllViews();
        current_Request_recyclerview.setAdapter(current_Request_mAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.viewAll_homeId:
                listener.click(new RequestFragment(listener));
                break;

            case R.id.tab_txt1:

                tab_txt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg));
                tab_txt2.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_whitebg));
                tab_txt1.setTextColor(Color.parseColor("#000000"));
                tab_txt2.setTextColor(Color.parseColor("#000000"));

                //loadFragment(new MyDonationFragment(this));


                break;


            case R.id.tab_txt2:

                tab_txt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_whitebg));
                tab_txt2.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg));
                tab_txt1.setTextColor(Color.parseColor("#000000"));
                tab_txt2.setTextColor(Color.parseColor("#000000"));

               // loadFragment(new ProductDonateFragment(this));


                break;



        }

    }

    @Override
    public void click(Fragment fragment) {

    }
}