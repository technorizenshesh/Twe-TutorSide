package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.RequestDetailsActivity;
import com.tech.twe_tutorside.listner.FragmentListener;


public class CompletedFragment extends Fragment {

    FragmentListener listener;

    public CompletedFragment(FragmentListener listener) {
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
        View view= inflater.inflate(R.layout.fragment_completed, container, false);

        TextView req_historyViewId=view.findViewById(R.id.req_historyViewId);
        req_historyViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RequestDetailsActivity.class));
            }
        });


        return view;
    }
}