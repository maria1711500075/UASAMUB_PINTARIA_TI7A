package com.asus.uasamub_pintaria_ti7a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class PelatihanCheckoutAct extends AppCompatActivity {

    Button btn_buy_ticket, btnmines, btnplus;
    TextView textjumlahpelatihan, texttotalharga, textmybalance,
            nama_pelatihan, location_pelatihan, ketentuan;
    Integer valuejumlahpelatihan = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargapelatihan = 0;
    ImageView notice_uang;
    Integer sisa_balance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_pelatihan = "13 Januari 2021";
    String time_pelatihan = "20:00";

    // generate nomor integer secara random
    // karena kita ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelatihan_checkout);

        getUsernameLocal();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_pelatihan_baru = bundle.getString("jenis_pelatihan");

        btnmines = findViewById(R.id.btnmines);
        btnplus = findViewById(R.id.btnplus);
        textjumlahpelatihan = findViewById(R.id.textjumlahpelatihan);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        notice_uang = findViewById(R.id.notice_uang);

        nama_pelatihan = findViewById(R.id.nama_pelatihan);
        location_pelatihan = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);

        // setting value baru untuk beberapa komponen
        textjumlahpelatihan.setText(valuejumlahpelatihan.toString());

        // secara default, kita hide btnmines
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        // mengambil data user dari firebase
        /*reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ " + mybalance+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        // mengambil data dari firebase bedasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Kelas").child(jenis_pelatihan_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data yang baru
                nama_pelatihan.setText(dataSnapshot.child("judul").getValue().toString());
                location_pelatihan.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                valuehargapelatihan = Integer.valueOf(dataSnapshot.child("harga_pelatihan").getValue().toString());

                valuetotalharga = valuehargapelatihan * valuejumlahpelatihan;
                texttotalharga.setText("US$ " + valuetotalharga + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahpelatihan += 1;
                textjumlahpelatihan.setText(valuejumlahpelatihan.toString());
                if (valuejumlahpelatihan > 1) {
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }
                valuetotalharga = valuehargapelatihan * valuejumlahpelatihan;
                texttotalharga.setText("US$ " + valuetotalharga + "");
                if (valuetotalharga < mybalance) {
                    btn_buy_ticket.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahpelatihan -= 1;
                textjumlahpelatihan.setText(valuejumlahpelatihan.toString());
                if (valuejumlahpelatihan < 2) {
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }
                valuetotalharga = valuehargapelatihan * valuejumlahpelatihan;
                texttotalharga.setText("US$ " + valuetotalharga + "");
                if (valuetotalharga < mybalance) {
                    btn_buy_ticket.animate().translationY(0)
                            .alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // menyimpan data user kepada firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance()
                        .getReference().child("KelasSaya")
                        .child(username_key_new).child(nama_pelatihan.getText().toString() + nomor_transaksi);

                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_pelatihan.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_kelas").setValue(nama_pelatihan.getText().toString());
                        reference3.getRef().child("lokasi").setValue(location_pelatihan.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_kelas").setValue(valuejumlahpelatihan.toString());
                        reference3.getRef().child("date_kelas").setValue(date_pelatihan);
                        reference3.getRef().child("time_kelas").setValue(time_pelatihan);

                        Intent gotosuccessticket = new
                                Intent(PelatihanCheckoutAct.this, SuccessBuyTicketAct.class);
                        startActivity(gotosuccessticket);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}