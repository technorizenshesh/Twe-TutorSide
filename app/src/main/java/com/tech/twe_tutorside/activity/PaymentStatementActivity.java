package com.tech.twe_tutorside.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tech.twe_tutorside.R;

public class PaymentStatementActivity extends AppCompatActivity {

    FrameLayout frameLayout_payment;
    TextView upcoming_txtId, totalTab_txtId;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_statement);

    }

    public void OliviaParkerInit(View view) {
        startActivity(new Intent(PaymentStatementActivity.this,TutorPaymentDetailsActivity.class));
    }
}