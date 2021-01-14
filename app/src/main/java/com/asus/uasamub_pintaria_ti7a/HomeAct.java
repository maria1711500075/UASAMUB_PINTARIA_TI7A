package com.asus.uasamub_pintaria_ti7a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_pelatihan_menggambar,
            btn_pelatihan_sains, btn_pelatihan_mtk,
            btn_pelatihan_ips, btn_pelatihan_komputer,
            btn_pelatihan_geografi;

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
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_pelatihan_menggambar = findViewById(R.id.btn_pelatihan_menggambar);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        btn_pelatihan_sains = findViewById(R.id.btn_pelatihan_sains);
        btn_pelatihan_mtk = findViewById(R.id.btn_pelatihan_mtk);
        btn_pelatihan_ips = findViewById(R.id.btn_pelatihan_ips);
        btn_pelatihan_komputer = findViewById(R.id.btn_pelatihan_komputer);
        btn_pelatihan_geografi = findViewById(R.id.btn_pelatihan_geografi);

        photo_home_user = findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("sekolah_kamu").getValue().toString());
                //user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit()
                        .into(photo_home_user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        //edit ini dulu
        btn_pelatihan_menggambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomenggambarticket = new Intent(HomeAct.this, PelatihanDetailAct.class);
                // meletakan data kepada intent
                gotomenggambarticket.putExtra("jenis_pelatihan", "Bahasa");
                startActivity(gotomenggambarticket);
            }
        });

        btn_pelatihan_sains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, PelatihanDetailAct.class);
                gotopisaticket.putExtra("jenis_pelatihan", "Tourism");
                startActivity(gotopisaticket);
            }
        });

        btn_pelatihan_mtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, PelatihanDetailAct.class);
                gotopisaticket.putExtra("jenis_pelatihan", "Data_science");
                startActivity(gotopisaticket);
            }
        });

        btn_pelatihan_ips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, PelatihanDetailAct.class);
                gotopisaticket.putExtra("jenis_pelatihan", "Hospitality");
                startActivity(gotopisaticket);
            }
        });

        btn_pelatihan_komputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, PelatihanDetailAct.class);
                gotopisaticket.putExtra("jenis_pelatihan", "Programming");
                startActivity(gotopisaticket);
            }
        });

        btn_pelatihan_geografi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, PelatihanDetailAct.class);
                gotopisaticket.putExtra("jenis_pelatihan", "Digital_marketing");
                startActivity(gotopisaticket);
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
