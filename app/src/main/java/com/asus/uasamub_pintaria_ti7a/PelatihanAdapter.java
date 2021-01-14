package com.asus.uasamub_pintaria_ti7a;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PelatihanAdapter extends RecyclerView.Adapter<PelatihanAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyPelatihan> myPelatihan;

    public PelatihanAdapter(Context c, ArrayList<MyPelatihan> p){
        context = c;
        myPelatihan = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_mypelatihan,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.xnama_pelatihan.setText(myPelatihan.get(i).getNama_kelas());
        myViewHolder.xlokasi.setText(myPelatihan.get(i).getLokasi());
        myViewHolder.xjumlah_pelatihan.setText(myPelatihan.get(i).getJumlah_kelas() + " Kelas");

        final String getNamaPelatihan = myPelatihan.get(i).getNama_kelas();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetails = new Intent(context, MyPelatihanDetailAct.class);
                gotomyticketdetails.putExtra("nama_pelatihan", getNamaPelatihan);
                context.startActivity(gotomyticketdetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPelatihan.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_pelatihan, xlokasi, xjumlah_pelatihan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_pelatihan = itemView.findViewById(R.id.xnama_pelatihan);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_pelatihan = itemView.findViewById(R.id.xjumlah_pelatihan);

        }
    }

}
