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
import android.widget.Toast;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.listner.FragmentListener;


public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {

  FragmentListener listener;



    LinearLayout email_recieptId;
    TextView email_txtrecieptId;
    ImageView iv_backInvoice;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invoice);

        email_recieptId=findViewById(R.id.email_recieptId);
        email_recieptId.setOnClickListener(this);
        email_txtrecieptId=findViewById(R.id.email_txtrecieptId);
        email_txtrecieptId.setOnClickListener(this);
        iv_backInvoice=findViewById(R.id.iv_backInvoice);
        iv_backInvoice.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.email_recieptId:
                startActivity(new Intent(this, HomeActvity.class));
                Toast.makeText(this, "Mail Send, Check your E-Mail", Toast.LENGTH_LONG).show();
                break;


            case R.id.email_txtrecieptId:
                startActivity(new Intent(this, HomeActvity.class));
                Toast.makeText(this, "Mail Send, Check your E-Mail", Toast.LENGTH_LONG).show();

                break;

            case R.id.iv_backInvoice:
                onBackPressed();
                finish();
                break;




        }
    }
}