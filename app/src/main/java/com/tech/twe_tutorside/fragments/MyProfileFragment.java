package com.tech.twe_tutorside.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.Activity_LoginOption;
import com.tech.twe_tutorside.activity.AttendanceActivity;
import com.tech.twe_tutorside.activity.ContactUsActivity;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.activity.LoginActivity;
import com.tech.twe_tutorside.activity.MyHistoryActivity;
import com.tech.twe_tutorside.activity.PaymentStatementActivity;
import com.tech.twe_tutorside.activity.PrivacyPolicy;
import com.tech.twe_tutorside.activity.TermsCondition;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.utils.SessionManager;


public class MyProfileFragment extends Fragment implements View.OnClickListener {


    LinearLayout myHistoryId,payment_statementId,notifi_Id,my_attendanceId,myrequestHistoryId,ll_profile,ll_terms_condition,ll_privacy_policy,ll_ContactUs;
    TextView txt_myHistoryId,payment_txtId,notifi_txtId,Txtmy_attendanceId,txt_myrequestHistoryId,txt_logout;
    ImageView iv_back;
    FirebaseAuth mAuth;
    FragmentListener listener;
    private SessionManager sessionManager;

    public MyProfileFragment(FragmentListener listener) {
        this.listener = listener;
    }

    public MyProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile, container, false);

        //Google SignIn
        mAuth = FirebaseAuth.getInstance();

        myHistoryId=view.findViewById(R.id.myHistoryId);
        myHistoryId.setOnClickListener(this);
        txt_myHistoryId=view.findViewById(R.id.txt_myHistoryId);
        txt_myHistoryId.setOnClickListener(this);
        iv_back=view.findViewById(R.id.iv_back);
        ll_ContactUs=view.findViewById(R.id.ll_ContactUs);
        iv_back.setOnClickListener(this);

        sessionManager = new SessionManager(getActivity());

        payment_statementId=view.findViewById(R.id.payment_statementId);
        payment_statementId.setOnClickListener(this);
        payment_txtId=view.findViewById(R.id.payment_txtId);
        payment_txtId.setOnClickListener(this);



        notifi_Id=view.findViewById(R.id.notifi_Id);
        notifi_Id.setOnClickListener(this);
        notifi_txtId=view.findViewById(R.id.notifi_txtId);
        notifi_txtId.setOnClickListener(this);


        my_attendanceId=view.findViewById(R.id.my_attendanceId);
        my_attendanceId.setOnClickListener(this);
        Txtmy_attendanceId=view.findViewById(R.id.Txtmy_attendanceId);
        Txtmy_attendanceId.setOnClickListener(this);

        myrequestHistoryId=view.findViewById(R.id.myrequestHistoryId);
        ll_profile=view.findViewById(R.id.ll_profile);
        myrequestHistoryId.setOnClickListener(this);
        ll_profile.setOnClickListener(this);

        txt_myrequestHistoryId=view.findViewById(R.id.txt_myrequestHistoryId);
        txt_myrequestHistoryId.setOnClickListener(this);

        txt_logout=view.findViewById(R.id.txt_logout);
        ll_privacy_policy=view.findViewById(R.id.ll_privacy_policy);
        ll_terms_condition=view.findViewById(R.id.ll_terms_condition);
        txt_logout.setOnClickListener(this);

        ll_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PrivacyPolicy.class));
            }
        });

        ll_terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), TermsCondition.class));
            }
        });

        ll_ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ContactUsActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.myHistoryId:
                listener.click(new RequestHistoryFragment(listener));
                break;

            case R.id.txt_myHistoryId:
                listener.click(new RequestHistoryFragment(listener));
                break;

            case R.id.my_attendanceId:
                startActivity(new Intent(getActivity(), AttendanceActivity.class));
                break;

            case R.id.Txtmy_attendanceId:
                startActivity(new Intent(getActivity(), AttendanceActivity.class));
                break;

            case R.id.payment_statementId:
                startActivity(new Intent(getActivity(), PaymentStatementActivity.class));
                break;

            case R.id.payment_txtId:
                startActivity(new Intent(getActivity(), PaymentStatementActivity.class));
                break;

            case R.id.notifi_Id:
                //startActivity(new Intent(getActivity(), NotificationFragment.class));
                listener.click(new NotificationFragment(listener));
                break;

            case R.id.notifi_txtId:
                listener.click(new NotificationFragment(listener));
                // startActivity(new Intent(getActivity(), PaymentStatementActivity.class));
                break;

            case R.id.iv_back:
                listener.click(new HomeFragment(listener));
                break;

            case R.id.myrequestHistoryId:
                listener.click(new RequestFragment(listener));
                break;

            case R.id.txt_myrequestHistoryId:
                listener.click(new RequestFragment(listener));
                break;

            case R.id.txt_logout:
                String UserType = Preference.get(getActivity(),Preference.KEYType_login);
                if(UserType.equalsIgnoreCase("social_login"))
                {
                    sessionManager.logoutUser();
                    Preference.clearPreference(getActivity());
                    mAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else
                {
                    sessionManager.logoutUser();
                    Preference.clearPreference(getActivity());
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            case R.id.ll_profile:
                listener.click(new AccountDetails(listener));
                break;

        }

    }
}