package com.asus.uasamub_pintaria_ti7a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PelatihanDetailAct extends AppCompatActivity {

    Button btn_buy_ticket;
    TextView title_pelatihan, location_pelatihan,
            guru_spot_pelatihan, konsul_pelatihan,
            sertifikat_pelatihan, short_desc_pelatihan;

    DatabaseReference reference;

    ImageView header_ticket_detail;

    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelatihan_detail);

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        btn_back = findViewById(R.id.btn_back);

        title_pelatihan = findViewById(R.id.title_pelatihan);
        location_pelatihan = findViewById(R.id.location_pelatihan);
        guru_spot_pelatihan = findViewById(R.id.guru_spot_pelatihan);
        konsul_pelatihan = findViewById(R.id.konsul_pelatihan);
        sertifikat_pelatihan = findViewById(R.id.sertifikat_pelatihan);
        short_desc_pelatihan = findViewById(R.id.short_desc_pelatihan);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_pelatihan_baru = bundle.getString("jenis_pelatihan");

        // mengambil data dari firebase bedasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Kelas").child(jenis_pelatihan_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data yang baru
                title_pelatihan.setText(dataSnapshot.child("judul").getValue().toString());
                location_pelatihan.setText(dataSnapshot.child("lokasi").getValue().toString());
                guru_spot_pelatihan.setText(dataSnapshot.child("nama_pelatihan").getValue().toString());
                konsul_pelatihan.setText(dataSnapshot.child("konsul").getValue().toString());
                sertifikat_pelatihan.setText(dataSnapshot.child("konsul").getValue().toString());
                short_desc_pelatihan.setText(dataSnapshot.child("deskripsi").getValue().toString());
                Picasso.with(PelatihanDetailAct.this)
                        .load(dataSnapshot.child("url_thumbnail")
                                .getValue().toString()).centerCrop().fit()
                        .into(header_ticket_detail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(PelatihanDetailAct.this, PelatihanCheckoutAct.class);
                // meletakan data kepada intent
                gotocheckout.putExtra("jenis_pelatihan", jenis_pelatihan_baru);
                startActivity(gotocheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoback = new Intent(PelatihanDetailAct.this,HomeAct.class);
                startActivity(gotoback);
            }
        });

    }
}