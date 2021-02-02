package com.tech.twe_tutorside.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.CurrentRequestAdapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.CurrentRequestModal;

import java.util.ArrayList;
import java.util.List;


public class TutorIndivisualPriceFragment extends Fragment {


    public TutorIndivisualPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_indivisual, container, false);

        return view;
    }

}