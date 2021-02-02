package com.tech.twe_tutorside.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.listner.FragmentListener;


public class RequestDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout view_InvoiceId;
    TextView View_txtInvoiceId;
    ImageView iv_backRequestDetatils;

  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_request_details);


        view_InvoiceId=findViewById(R.id.view_InvoiceId);
        view_InvoiceId.setOnClickListener(this);
        View_txtInvoiceId=findViewById(R.id.View_txtInvoiceId);
        View_txtInvoiceId.setOnClickListener(this);
        iv_backRequestDetatils=findViewById(R.id.iv_backRequestDetatils);
        iv_backRequestDetatils.setOnClickListener(this);


    }

  


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.view_InvoiceId:
                startActivity(new Intent(this, InvoiceActivity.class));
                break;


            case R.id.View_txtInvoiceId:
                startActivity(new Intent(this, InvoiceActivity.class));
                break;

            case R.id.iv_backRequestDetatils:
                startActivity(new Intent(this, HomeActvity.class));
                break;




        }
    }
}