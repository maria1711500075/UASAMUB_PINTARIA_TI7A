package com.asus.uasamub_pintaria_ti7a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.asus.uasamub_pintaria_ti7a.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    Button btn_continue;
    LinearLayout btn_back;
    EditText username, password, email_address;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_login = database.getReference("User");

        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ubah state menjadi loading
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading ...");

                // menyimpan data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, username.getText().toString());
                editor.apply();
                table_login.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = new User(username.getText().toString(),
                                password.getText().toString(),
                                email_address.getText().toString());
                        table_login.child(username.getText().toString()).setValue(user);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // berpindah activity
                Intent gotonextregister = new Intent(RegisterOneAct.this,RegisterTwoAct.class);
                startActivity(gotonextregister);
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtosignin = new Intent(RegisterOneAct.this,SignInAct.class);
                startActivity(backtosignin);
            }
        });
    }

}
