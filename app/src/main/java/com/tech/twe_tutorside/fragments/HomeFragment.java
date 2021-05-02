package com.tech.twe_tutorside.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.BuildingProfiActivity;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.activity.LoginActivity;
import com.tech.twe_tutorside.activity.TimeSelectedWeekView;
import com.tech.twe_tutorside.adapter.CurrentRequestAdapter;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.CurrentRequestModal;
import com.tech.twe_tutorside.model.GetRequestModel;
import com.tech.twe_tutorside.model.GetRequestModelData;
import com.tech.twe_tutorside.model.SaloonSpecialistModel;
import com.tech.twe_tutorside.model.TimeSlotModel;
import com.tech.twe_tutorside.onClickLisner.onCliklisnermethod;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment  implements FragmentListener, View.OnClickListener , onCliklisnermethod {

    FragmentListener listener;
    TextView tab_txt1,tab_txt2;
    TextView txt_name;
    TextView viewAll_homeId;
    public static ProgressBar progressBar;
    
    private ArrayList<GetRequestModelData> currentRequestModalList;

    private RecyclerView current_Request_recyclerview;
    private RecyclerView.Adapter current_Request_mAdapter;

    ImageView profile_image;
    TextView txt_empty;
    private SessionManager sessionManager;
    private View promptsView;
    private Button btn_no;
    private Button btn_yes;
    private AlertDialog alertDialog;

    public HomeFragment(FragmentListener listener) {
        this.listener = listener;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        viewAll_homeId=view.findViewById(R.id.viewAll_homeId);
        txt_empty=view.findViewById(R.id.txt_empty);
        progressBar=view.findViewById(R.id.progressBar);
        viewAll_homeId.setOnClickListener(this);
        sessionManager = new SessionManager(getActivity());

        String UserName = Preference.get(getActivity(), Preference.KEY_username);
        String ProfileImage = Preference.get(getActivity(), Preference.KEY_Profile_image);

        tab_txt1 = view.findViewById(R.id.tab_txt1);
        profile_image = view.findViewById(R.id.profile_image);
        txt_name = view.findViewById(R.id.txt_name);
        tab_txt2 = view.findViewById(R.id.tab_txt2);
        txt_name.setText(UserName);
        // adding a listner
        tab_txt1.setOnClickListener(this);
        tab_txt2.setOnClickListener(this);

        if(ProfileImage !=null)
        {
            Glide.with(getActivity()).load(ProfileImage).circleCrop().circleCrop().placeholder(R.drawable.home_banner3)
                    .into(profile_image);
        }

        current_Request_recyclerview = view.findViewById(R.id.currentRequest_recyclerview);
        current_Request_recyclerview.setHasFixedSize(true);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL,
                false);

        current_Request_recyclerview.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

        currentRequestModalList=new ArrayList<>();


        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            get_current_request();

        }else {

            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.viewAll_homeId:
                listener.click(new RequestFragment(listener));
                break;

            case R.id.tab_txt1:

                tab_txt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg));
                tab_txt2.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_whitebg));
                tab_txt1.setTextColor(Color.parseColor("#000000"));
                tab_txt2.setTextColor(Color.parseColor("#000000"));
                //loadFragment(new MyDonationFragment(this));
                break;

            case R.id.tab_txt2:
                tab_txt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_whitebg));
                tab_txt2.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg));
                tab_txt1.setTextColor(Color.parseColor("#000000"));
                tab_txt2.setTextColor(Color.parseColor("#000000"));

               // loadFragment(new ProductDonateFragment(this));
                break;
        }

    }

    @Override
    public void click(Fragment fragment) {
    }

    private void get_current_request() {

        String UserId  = Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<GetRequestModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_current_request(UserId);

        call.enqueue(new Callback<GetRequestModel>() {
            @Override
            public void onResponse(Call<GetRequestModel> call, Response<GetRequestModel> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    GetRequestModel myAllrequest=response.body();
                    String status =myAllrequest.getStatus();
                    String message =myAllrequest.getMessage();
                    if (status.equalsIgnoreCase("1")) {
                        txt_empty.setVisibility(View.GONE);
                        currentRequestModalList.clear();
                        currentRequestModalList = (ArrayList<GetRequestModelData>) myAllrequest.getResult();

                        current_Request_mAdapter = new CurrentRequestAdapter(getActivity(), currentRequestModalList,HomeFragment.this);
                        current_Request_recyclerview.setAdapter(current_Request_mAdapter);

                      }else {
                        currentRequestModalList.clear();
                        current_Request_mAdapter.notifyDataSetChanged();
                        txt_empty.setVisibility(View.VISIBLE);

                    }

                }catch (Exception e) {
                    txt_empty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetRequestModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_empty.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update_current_request(String Request_id,String status){

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .update_current_request(Request_id,status);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    HomeFragment.progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                         get_current_request();

                    } else {

                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                HomeFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void AlertDaliog(int position,String status) {

        LayoutInflater li;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(getActivity());
        li = LayoutInflater.from(getActivity());
        promptsView = li.inflate(R.layout.alert_dailoge, null);
        btn_no = (Button) promptsView.findViewById(R.id.btn_no);
        btn_yes = (Button) promptsView.findViewById(R.id.btn_yes);
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_current_request(currentRequestModalList.get(position).getId(),status);
                alertDialog.dismiss();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void click(int position,String status) {
        AlertDaliog(position,status);

    }


}