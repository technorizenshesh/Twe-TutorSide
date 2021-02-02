package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.RatingActivity;
import com.tech.twe_tutorside.activity.TrackingPageActivity;
import com.tech.twe_tutorside.activity.TutorTrackngActivity;
import com.tech.twe_tutorside.listner.FragmentListener;


public class TodayClassDetailFragment extends Fragment {


    FragmentListener listener;

    public TodayClassDetailFragment(FragmentListener listener) {
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
        View view= inflater.inflate(R.layout.fragment_today_class_detail, container, false);

        CardView cardclassDeatils=view.findViewById(R.id.cardclassDeatils);

        cardclassDeatils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RatingActivity.class));
            }
        });

        return view;
    }
}