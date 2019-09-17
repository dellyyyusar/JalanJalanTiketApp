package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class successSignup extends AppCompatActivity {

    Button btn_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_signup);

        btn_explore = findViewById(R.id.btn_explore);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(successSignup.this, homeScreen.class);
                startActivity(gohome);
            }
        });

    }
}
