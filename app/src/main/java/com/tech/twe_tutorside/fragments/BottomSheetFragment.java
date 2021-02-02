package com.tech.twe_tutorside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tech.twe_tutorside.GPSTracker;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.FillDetailsActivity;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.activity.LoginActivity;
import com.tech.twe_tutorside.adapter.GetAddress;
import com.tech.twe_tutorside.model.getAddress;
import com.tech.twe_tutorside.model.getShiipingAddressData;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    ImageView bottomSheet_cancelId;
    RecyclerView recycler_address;
    private SessionManager sessionManager;
    private ProgressBar progressBar;
    private TextView txt_save;
    private LinearLayout LL_Add_address;
    int increment = 4;
    GetAddress mAdapter;
    private ArrayList<getShiipingAddressData> modelList=new ArrayList<>();

    private LinearLayout LL_CurrentLocation;
    GPSTracker gpsTracker;
    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        bottomSheet_cancelId=contentView.findViewById(R.id.bottomSheet_cancelId);
        LL_CurrentLocation=contentView.findViewById(R.id.LL_CurrentLocation);
        recycler_address=contentView.findViewById(R.id.recycler_address);
        sessionManager = new SessionManager(getActivity());
        progressBar=contentView.findViewById(R.id.progressBar);
        LL_CurrentLocation=contentView.findViewById(R.id.LL_CurrentLocation);
        txt_save=contentView.findViewById(R.id.txt_save);
        LL_Add_address=contentView.findViewById(R.id.LL_Add_address);

        bottomSheet_cancelId.setOnClickListener(this);
        LL_CurrentLocation.setOnClickListener(this);
        modelList.clear();
        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getAddress();

        }else {

            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<modelList.size();i++)
                {
                    if(modelList.get(i).isSelected())
                    {
                        Preference.save(getActivity(), Preference.KEY_Address_id,modelList.get(i).getId());

                        Toast.makeText(getActivity(), modelList.get(i).getId(), Toast.LENGTH_SHORT).show();

                        getActivity().onBackPressed();
                        //getFragmentManager().popBackStack();
                    }
                    //modelList.get(i).isChecked();
                }
            }
        });

        LL_Add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        //setAdapter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bottomSheet_cancelId:
                getFragmentManager().popBackStack();
                //Objects.requireNonNull(getActivity()).onBackPressed();
                break;
                case R.id.LL_CurrentLocation:

                    if (sessionManager.isNetworkAvailable()) {

                        progressBar.setVisibility(View.VISIBLE);

                        AddAddress();

                    }else {

                        Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }

                /*    gpsTracker = new GPSTracker(getActivity());
                    if (gpsTracker.getIsGPSTrackingEnabled())
                    {
                        String stringLatitude = String.valueOf(gpsTracker.getLatitude());

                        String stringLongitude = String.valueOf(gpsTracker.getLongitude());

                        String country = gpsTracker.getCountryName(getActivity());

                        String city = gpsTracker.getLocality(getActivity());

                        String postalCode = gpsTracker.getPostalCode(getActivity());

                        String addressLine = gpsTracker.getAddressLine(getActivity());

                        Toast.makeText(getActivity(), ""+stringLatitude, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gpsTracker.showSettingsAlert();
                    }*/
                    //Toast.makeText(getActivity(), "Current Location Add", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void setAdapter(ArrayList<getShiipingAddressData> modelList) {

        // Collections.shuffle(modelList, new Random(System.currentTimeMillis()));
       // modelList.add(new getShiipingAddressData("kjbkjb","hgv"));
      //  modelList.add(new getShiipingAddressData("kjbkjb","hgv"));

        mAdapter = new GetAddress(getActivity(), modelList);

        recycler_address.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_address.setLayoutManager(linearLayoutManager);
        recycler_address.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new GetAddress.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, getShiipingAddressData model) {

              /*  Preference.save(getActivity(),Preference.KEY_product_id,model.getId());
                listener.click(new ItemDetailsFragment(listener));*/
            }
        });
    }


    private void AddAddress() {

        String UserId = sessionManager.getUserID();

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_address(UserId,"Home","Sagar","23.8388","78.7378");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                       // getActivity().finish();
                       // startActivity(new Intent(getActivity(), HomeActvity.class));

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAddress() {

       // String UserId = Preference.get(getActivity(), Preference.KEY_USER_ID);
        String UserId = "3";

        Call<getAddress> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_address(UserId);

        call.enqueue(new Callback<getAddress>() {
            @Override
            public void onResponse(Call<getAddress> call, Response<getAddress> response) {

                try {

                   /* JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");
                    JSONObject resultOne = jsonObject.getJSONObject("result");*/
                    getAddress myProductList=response.body();

                    String status= myProductList.getStatus().toString();
                    String Message= myProductList.getMessage().toString();

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                        modelList = (ArrayList<getShiipingAddressData>) myProductList.getResult();

                        setAdapter(modelList);
                        // getActivity().finish();
                        // startActivity(new Intent(getActivity(), HomeActvity.class));

                    } else {

                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<getAddress> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}