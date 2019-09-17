package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class successBuyTicket extends AppCompatActivity {

    Button btn_back_dashboard, btn_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_back_dashboard = findViewById(R.id.btn_back_dashboard);
        btn_explore = findViewById(R.id.btn_explore);

        btn_back_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodahshboard = new Intent(successBuyTicket.this, homeScreen.class);
                startActivity(gotodahshboard);
            }
        });

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototicket = new Intent(successBuyTicket.this, myDetailTicket.class);
                startActivity(gototicket);
            }
        });
    }
}
