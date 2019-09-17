package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ticketDetail extends AppCompatActivity {

    Button buy_ticket;
    DatabaseReference reference;
    TextView title_ticket, location_ticket, photo_ticket, wifi_ticket, party_ticket, short_desc;
    ImageView header_ticket_detail;
    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        buy_ticket = findViewById(R.id.buy_ticket);
        title_ticket = findViewById(R.id.title_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        photo_ticket = findViewById(R.id.photo_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        party_ticket = findViewById(R.id.party_ticket);
        short_desc = findViewById(R.id.short_desc);
        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        btn_back = findViewById(R.id.btn_back);


        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_ticket_baru = bundle.getString("jenis_ticket");
        //Toast.makeText(getApplicationContext(),"Jenis Tiket : " + jenis_ticket_baru, Toast.LENGTH_SHORT).show();

        //mengambil data dari database intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_ticket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data dengan data yang baru
                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photo_ticket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());
                party_ticket.setText(dataSnapshot.child("is_fest").getValue().toString());
                Picasso.with(ticketDetail.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });

        buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotobuy = new Intent(ticketDetail.this, ticketCheckout.class);
                //menyimpan data ke intent
                gotobuy.putExtra("jenis_ticket",jenis_ticket_baru);
                startActivity(gotobuy);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(ticketDetail.this, homeScreen.class);
                startActivity(gohome);
            }
        });
    }
}
