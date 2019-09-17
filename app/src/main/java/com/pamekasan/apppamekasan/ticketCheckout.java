package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ticketCheckout extends AppCompatActivity {

    Button buy_ticket,btn_ples,btn_mines;
    TextView textjumlahtiket, texttotalharga, textmybalance, nama_wisata, lokasi, ketentuan;
    Integer valuejumlahtiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    ImageView notice_uang;
    DatabaseReference reference,reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String tanggal = "";
    String waktu = "";
    Integer sisabalance=0;

    //generate nomor transaksi secara random
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        buy_ticket = findViewById(R.id.buy_ticket);
        btn_mines = findViewById(R.id.btn_mines);
        btn_ples = findViewById(R.id.btn_ples);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);
        notice_uang = findViewById(R.id.notice_uang);

        //set value baru untuk beberapa komponen
        textjumlahtiket.setText(valuejumlahtiket.toString());


        //secara default hide btnmines
        btn_mines.animate().alpha(0).setDuration(300).start();
        btn_mines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_ticket_baru = bundle.getString("jenis_ticket");

        //mengambi data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ "+ mybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_ticket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_ticket").getValue().toString());
                tanggal = dataSnapshot.child("tanggal").getValue().toString();
                waktu = dataSnapshot.child("waktu").getValue().toString();

                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("US$ "+ valuetotalharga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_ples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahtiket+=1;
                textjumlahtiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket > 1){
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("US$ "+valuetotalharga+"");
                if (valuetotalharga > mybalance){
                    buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    buy_ticket.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahtiket-=1;
                textjumlahtiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket < 2){
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("US$ "+valuetotalharga+"");
                if (valuetotalharga < mybalance){
                    buy_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    buy_ticket.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_ticket").setValue(valuejumlahtiket.toString());
                        reference3.getRef().child("tanggal").setValue(tanggal.toString());
                        reference3.getRef().child("waktu").setValue(waktu.toString());
                        Intent gotosuccessbuy = new Intent(ticketCheckout.this, successBuyTicket.class);
                        startActivity(gotosuccessbuy);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //update data balance kepada user yang login
                //mengambil data user dari firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisabalance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisabalance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
