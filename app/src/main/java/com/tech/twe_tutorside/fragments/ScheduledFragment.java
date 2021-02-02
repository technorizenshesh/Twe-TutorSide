package com.tech.twe_tutorside.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.ClassTodayAdapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.ClassTodayModal;

import java.util.ArrayList;
import java.util.List;


public class ScheduledFragment extends Fragment  {

    FragmentListener listener;

    View view;



    public ScheduledFragment(FragmentListener listener) {
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
         view= inflater.inflate(R.layout.fragment_scheduled, container, false);








        init1();
        init();


        return  view;
    }


    public void init1(){


         List<ClassTodayModal> classTodayModalList;

         RecyclerView classToday_recyclerview;

        LinearLayoutManager layoutManager;

         RecyclerView.Adapter classToday_mAdapter;

        classToday_recyclerview = view.findViewById(R.id.classToday_recyclerview);
        classToday_recyclerview.setHasFixedSize(true);

        layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL,
                false);

        classTodayModalList=new ArrayList<>();


        classToday_recyclerview.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

        classToday_mAdapter = new ClassTodayAdapter(getActivity(), classTodayModalList);
        // current_Request_recyclerview.removeAllViews();
        classToday_recyclerview.setAdapter(classToday_mAdapter);



    }


    public void init(){

         List<ClassTodayModal> classTodayModalList1;

         RecyclerView upcomingClass_recyclerview;

        LinearLayoutManager layoutManager1;

         RecyclerView.Adapter classToday_mAdapter1;

        upcomingClass_recyclerview = view.findViewById(R.id.upcomingClass_recyclerview);
        upcomingClass_recyclerview.setHasFixedSize(true);

         layoutManager1
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL,
                false);

        classTodayModalList1=new ArrayList<>();


        upcomingClass_recyclerview.setLayoutManager(layoutManager1); // set LayoutManager to RecyclerView

        classToday_mAdapter1 = new ClassTodayAdapter(getActivity(), classTodayModalList1);
        // current_Request_recyclerview.removeAllViews();
        upcomingClass_recyclerview.setAdapter(classToday_mAdapter1);



    }


}