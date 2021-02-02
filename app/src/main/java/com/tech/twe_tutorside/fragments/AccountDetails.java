package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.activity.LoginActivity;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.TutorCategory_Model;
import com.tech.twe_tutorside.model.myprofile_model;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountDetails extends Fragment {

   FragmentListener listener;
    ImageView iv_back;
    ImageView img_profile;
    TextView txt_name;
    TextView txt_email;
    TextView txt_phone;
    TextView txt_address;
    ProgressBar progressBar;
    private SessionManager sessionManager;

    public AccountDetails(FragmentListener listener) {
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
        View view= inflater.inflate(R.layout.fragment_account, container, false);


        iv_back=view.findViewById(R.id.iv_back);
        txt_name=view.findViewById(R.id.txt_name);
        txt_email=view.findViewById(R.id.txt_email);
        txt_phone=view.findViewById(R.id.txt_phone);
        txt_address=view.findViewById(R.id.txt_address);
        img_profile=view.findViewById(R.id.img_profile);
        progressBar=view.findViewById(R.id.progressBar);
        sessionManager = new SessionManager(getActivity());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(new HomeFragment(listener));
            }
        });

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);
            myProfile();
        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void myProfile() {
        String UserId = Preference.get(getActivity(), Preference.KEY_USER_ID);

        Call<myprofile_model> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_profile(UserId);
        call.enqueue(new Callback<myprofile_model>() {
            @Override
            public void onResponse(Call<myprofile_model> call, Response<myprofile_model> response) {

                try {

                    progressBar.setVisibility(View.GONE);

                    myprofile_model finallyPr = response.body();

                    String status=finallyPr.getStatus();
                    String message=finallyPr.getStatus();

                    if (status.equalsIgnoreCase("1")) {

                        String Email = finallyPr.getResult().get(0).getEmail().toString();
                        String name = finallyPr.getResult().get(0).getUsername().toString();
                        String Phone = finallyPr.getResult().get(0).getMobile().toString();
                        String address = finallyPr.getResult().get(0).getAddressDetails().getAddress();
                        String profile_image = finallyPr.getResult().get(0).getProfileImage();

                         txt_name.setText(name);
                         txt_email.setText(Email);
                         txt_phone.setText(Phone);
                         txt_address.setText(address);

                        if(profile_image !=null)
                        {
                            Glide.with(getActivity()).load(profile_image).placeholder(R.drawable.logo)
                                    .into(img_profile);
                        }

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<myprofile_model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}