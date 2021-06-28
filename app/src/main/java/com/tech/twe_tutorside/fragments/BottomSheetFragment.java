package com.tech.twe_tutorside.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.tech.twe_tutorside.NewMap.GoogleMapNewActivity;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.SessionManager;
import com.tech.twe_tutorside.activity.GooglePlacesAutocompleteActivity;
import com.tech.twe_tutorside.activity.VerifyOtpActivity;
import com.tech.twe_tutorside.adapter.GetAddress;
import com.tech.twe_tutorside.listner.MyClickListner;
import com.tech.twe_tutorside.model.getAddress;
import com.tech.twe_tutorside.model.getShiipingAddressData;
import com.tech.twe_tutorside.utils.RetrofitClients;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    private TextView txt_empty;
    private LinearLayout LL_Add_address;
    int increment = 4;
    GetAddress mAdapter;
    MyClickListner listner;
    private static final int REQUEST_CODE = 1353;

    LinearLayout LL_save;
    private ArrayList<getShiipingAddressData> modelList=new ArrayList<>();

    private LinearLayout LL_CurrentLocation;
    GPSTracker gpsTracker;

   public BottomSheetFragment(MyClickListner listner) {
        this.listner = listner;
    }

    public BottomSheetFragment() {

    }


/*

    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }
*/

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
        txt_empty=contentView.findViewById(R.id.txt_empty);
        LL_save=contentView.findViewById(R.id.LL_save);

        bottomSheet_cancelId.setOnClickListener(this);
        LL_CurrentLocation.setOnClickListener(this);

        modelList.clear();
        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getAddress();

        }else {

            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();

        }


        LL_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listner.clickListen("harshit");
                dismiss();

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
                dismiss();
                break;
                case R.id.LL_CurrentLocation:
                   // Intent intent = new Intent(getActivity(), GooglePlacesAutocompleteActivity.class);
                    Intent intent = new Intent(getActivity(), GoogleMapNewActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }


    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

            if (sessionManager.isNetworkAvailable()) {
                progressBar.setVisibility(View.VISIBLE);
                getAddress();
            }else {
                Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
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

             //   dismiss();

/*              Preference.save(getActivity(),Preference.KEY_product_id,model.getId());
                listener.click(new ItemDetailsFragment(listener));*/
            }
        });
    }


    private void AddAddress() {

      //  String UserId = sessionManager.getUserID();
        String UserId = "66";

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

       String UserId =  Preference.get(getActivity(), Preference.KEY_USER_ID);

        Call<getAddress> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_address(UserId);

        call.enqueue(new Callback<getAddress>() {
            @Override
            public void onResponse(Call<getAddress> call, Response<getAddress> response) {

                progressBar.setVisibility(View.GONE);

                try {

                   /* JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");
                    JSONObject resultOne = jsonObject.getJSONObject("result");*/
                    getAddress myProductList=response.body();

                    String status= myProductList.getStatus().toString();
                    String Message= myProductList.getMessage().toString();

                    if (status.equalsIgnoreCase("1")) {
                        txt_empty.setVisibility(View.GONE);
                        modelList = (ArrayList<getShiipingAddressData>) myProductList.getResult();

                        setAdapter(modelList);
                        // getActivity().finish();
                        // startActivity(new Intent(getActivity(), HomeActvity.class));

                    } else {
                        txt_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    txt_empty.setVisibility(View.VISIBLE);
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<getAddress> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_empty.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}