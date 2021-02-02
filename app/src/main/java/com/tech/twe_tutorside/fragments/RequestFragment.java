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


public class RequestFragment extends Fragment implements  View.OnClickListener,FragmentListener{

    FragmentListener listener;
    TextView completedId,scheduledId;
    ImageView iv_backRequest;

    public RequestFragment(FragmentListener listener) {
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
        View view= inflater.inflate(R.layout.fragment_request, container, false);

        scheduledId=view.findViewById(R.id.scheduledId);
        scheduledId.setOnClickListener(this);
        completedId=view.findViewById(R.id.completedId);
        completedId.setOnClickListener(this);
        iv_backRequest=view.findViewById(R.id.iv_backRequest);
        iv_backRequest.setOnClickListener(this);
        loadFragment(new ScheduledFragment(this));


        return view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.scheduledId:
                // listener.click(new RequestHistoryFragment(listener));

                scheduledId.setTextColor(Color.parseColor("#ff5d5d"));
                completedId.setTextColor(Color.parseColor("#ffffff"));
                scheduledId.setTextSize(18);
                completedId.setTextSize(16);
                loadFragment(new ScheduledFragment(this));
                break;


            case R.id.completedId:


                scheduledId.setTextColor(Color.parseColor("#ffffff"));
                completedId.setTextColor(Color.parseColor("#ff5d5d"));
                completedId.setTextSize(18);
                scheduledId.setTextSize(16);
                loadFragment(new ScheduledFragment(this));
                break;


            case R.id.iv_backRequest:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;




        }
    }


    private void loadFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.frameContainerRequest, fragment).addToBackStack("home").commit();
    }


    @Override
    public void click(Fragment fragment) {

        loadFragment(fragment);
    }
}