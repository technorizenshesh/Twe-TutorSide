package com.tech.twe_tutorside.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.listner.FragmentListener;

import java.util.Objects;


public class RequestHistoryFragment extends Fragment implements FragmentListener, View.OnClickListener{

    FragmentListener listener;

    TextView completedId,CancelId;
    ImageView iv_backRequestH;



    public RequestHistoryFragment(FragmentListener listener) {
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
        View view = inflater.inflate(R.layout.fragment_request_history, container, false);

        completedId=view.findViewById(R.id.RcompletedId);
        completedId.setOnClickListener(this);

        CancelId=view.findViewById(R.id.CancelId);
        CancelId.setOnClickListener(this);

        iv_backRequestH=view.findViewById(R.id.iv_backRequestH);
        iv_backRequestH.setOnClickListener(this);
        loadFragment(new CompletedFragment(this));



        return view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {


            case R.id.RcompletedId:


                CancelId.setTextColor(Color.parseColor("#ffffff"));
                completedId.setTextColor(Color.parseColor("#ff5d5d"));
                completedId.setTextSize(18);
                CancelId.setTextSize(16);
                loadFragment(new CompletedFragment(this));
                break;

            case R.id.CancelId:


                CancelId.setTextColor(Color.parseColor("#ff5d5d"));
                completedId.setTextColor(Color.parseColor("#ffffff"));
                completedId.setTextSize(16);
                CancelId.setTextSize(18);
                loadFragment(new CompletedFragment(this));
                break;



            case R.id.iv_backRequestH:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;




        }
    }


    private void loadFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.frameRequestHistory, fragment).
                addToBackStack("home").commit();
    }


    @Override
    public void click(Fragment fragment) {

        loadFragment(fragment);
    }
}