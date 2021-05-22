package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.EditProfileActivity;
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
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountDetails extends Fragment {

   FragmentListener listener;
    RoundedImageView profile_img;
    ProgressBar progressBar;
    ImageView iv_back;
    LinearLayout ll_edit_profile;
    private SessionManager sessionManager;
    TextView txt_year_count;
    TextView txt_gender;
    TextView txt_payment_per_hr;
    TextView txt_Education;
    TextView txt_Language;
    TextView txt_abouts;
    TextView txt_Certification;
    TextView txt_Affilation;
    TextView seeall_recommend;
    TextView txt_address;
    TextView txt_tutor_name;

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
      //  View view= inflater.inflate(R.layout.fragment_account, container, false);
        View view= inflater.inflate(R.layout.activity_profile_tutor, container, false);

        txt_year_count  =view.findViewById(R.id.txt_year_count);
        txt_gender  =view.findViewById(R.id.txt_gender);
        profile_img  =view.findViewById(R.id.profile_img);
        txt_payment_per_hr  =view.findViewById(R.id.txt_payment_per_hr);
        txt_abouts  =view.findViewById(R.id.txt_abouts);
        txt_Education  =view.findViewById(R.id.txt_Education);
        txt_Language  =view.findViewById(R.id.txt_Language);
        txt_Certification  =view.findViewById(R.id.txt_Certification);
        txt_Affilation  =view.findViewById(R.id.txt_Affilation);
        seeall_recommend  =view.findViewById(R.id.seeall_recommend);
        txt_address  =view.findViewById(R.id.txt_address);
        progressBar  =view.findViewById(R.id.progressBar);
        iv_back  =view.findViewById(R.id.iv_back);
        ll_edit_profile  =view.findViewById(R.id.ll_edit_profile);
        txt_tutor_name  =view.findViewById(R.id.txt_tutor_name);

        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            myProfile();

        }else {

            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });

        ll_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

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

                        String profile_image = finallyPr.getResult().get(0).getProfileImage();

                        txt_tutor_name.setText(finallyPr.getResult().get(0).getUsername());

                        String Dob= finallyPr.getResult().get(0).getUserDetails().getDob();

                        if(Dob!=null && !Dob.equalsIgnoreCase(""))
                        {
                            String datrrr[] = Dob.split("-");
                            String CalcuAge= getAge(Integer.parseInt(datrrr[2]),Integer.parseInt(datrrr[1]),Integer.parseInt(datrrr[0]));
                            txt_year_count.setText(CalcuAge+" Years");
                        }

                        txt_gender.setText(finallyPr.getResult().get(0).getUserDetails().getGender());
                        txt_abouts.setText(finallyPr.getResult().get(0).getUserDetails().getAbout());
                        txt_Education.setText(finallyPr.getResult().get(0).getUserDetails().getEducation());
                        txt_Language.setText(finallyPr.getResult().get(0).getUserDetails().getLanguage());
                        txt_Certification.setText(finallyPr.getResult().get(0).getUserDetails().getCertification());
                        txt_Affilation.setText(finallyPr.getResult().get(0).getUserDetails().getAffiliations());
                       // seeall_recommend.setText(finallyPr.getResult().getAffiliations());
                     //   txt_payment_per_hr.setText("\u20B9"+finallyPr.getResult().get(0).getTotalChargesIndividual());
                        txt_address.setText(finallyPr.getResult().get(0).getAddressDetails().getAddress());
                        if(profile_image !=null)
                        {

                            Glide.with(getActivity()).load(profile_image).circleCrop().placeholder(R.drawable.logo)
                                    .into(profile_img);
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

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}