package com.tech.twe_tutorside.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.listner.FragmentListener;


public class TotalPaidFragment extends Fragment {


   FragmentListener listener;

    public TotalPaidFragment(FragmentListener listener) {
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
        View view= inflater.inflate(R.layout.fragment_total_paid, container, false);

        return view;
    }
}