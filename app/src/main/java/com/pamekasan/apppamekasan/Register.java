package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username, password, email_address;
    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goback = new Intent(Register.this, signInB.class);
                startActivity(goback);
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //state menjadi continue
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading ...");

                //mengambil username pada database
                reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //cek username apakah ada
                        if(dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "Username sudah tersedia", Toast.LENGTH_SHORT).show();

                            //state menjadi continue
                            btn_continue.setEnabled(true);
                            btn_continue.setText("Continue");

                        }
                        else{
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, username.getText().toString());
                            editor.apply();

                            //tes username apa sudah masuk
                            //Toast.makeText(getApplicationContext(), "Username" + username.getText().toString(), Toast.LENGTH_SHORT).show();

                            //simpan ke database
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                    dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                    dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                    dataSnapshot.getRef().child("user_balance").setValue(800);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            Intent gotosetprofile = new Intent(Register.this, registerProfile.class);
                            startActivity(gotosetprofile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //simpan data di local storage

            }
        });

    }
}

