package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class homeScreen extends AppCompatActivity {

    LinearLayout btn_ticket_pisa, btn_ticket_jumiang, btn_ticket_branta, btn_ticket_mercusuar, btn_ticket_talang, btn_ticket_surs;
    CircleView btn_to_profile;
    ImageView photo_home_user;
    TextView user_balance, nama_lengkap, bio;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        getUsernameLocal();

        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_jumiang = findViewById(R.id.btn_ticket_jumiang);
        btn_ticket_branta = findViewById(R.id.btn_ticket_branta);
        btn_ticket_mercusuar = findViewById(R.id.btn_ticket_mercusuar);
        btn_ticket_talang = findViewById(R.id.btn_ticket_talang);
        btn_ticket_surs = findViewById(R.id.btn_ticket_surs);


        photo_home_user = findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        btn_to_profile = findViewById(R.id.btn_to_profile);


        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(homeScreen.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT);
            }
        });

        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(homeScreen.this, ticketDetail.class);
                //menyimpan data ke intent
                gotopisaticket.putExtra("jenis_ticket","ArekLancor");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_branta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(homeScreen.this, ticketDetail.class);
                gotopisaticket.putExtra("jenis_ticket","Branta");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_jumiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(homeScreen.this, ticketDetail.class);
                gotopisaticket.putExtra("jenis_ticket","JumiangBeachClub");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_mercusuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(homeScreen.this, ticketDetail.class);
                gotopisaticket.putExtra("jenis_ticket","TlanakanSQHQ");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_talang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(homeScreen.this, ticketDetail.class);
                gotopisaticket.putExtra("jenis_ticket","TuguSelamatDatang");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_surs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(homeScreen.this, ticketDetail.class);
                gotopisaticket.putExtra("jenis_ticket","TlanakanDowntown");
                startActivity(gotopisaticket);
            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(homeScreen.this, profileAct.class);
                startActivity(gotoprofile);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
