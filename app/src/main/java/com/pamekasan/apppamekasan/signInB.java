package com.pamekasan.apppamekasan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signInB extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_b);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregister = new Intent(signInB.this, Register.class);
                startActivity(gotoregister);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ubah state
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"username kosong !", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("Sign In");
                }
                else{
                    if (password.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"password kosong !", Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("Sign In");
                    }
                    else{
                        //set database
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //Toast.makeText(getApplicationContext(), "Username ada!", Toast.LENGTH_SHORT).show();

                                    //ambil data password di firebase
                                    String passworddariFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password local dengan firebase
                                    if (password.equals(passworddariFirebase)){

                                        //pindah ctivity
                                        Intent gohome = new Intent(signInB.this, homeScreen.class);
                                        startActivity(gohome);

                                        //simpan password ke local
                                        SharedPreferences sharedPreferences =  getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();


                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT).show();
                                        //ubah state
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("Sign In");
                                    }


                                } else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT).show();
                                    //ubah state
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("Sign In");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database tidak ada", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });
    }
}
