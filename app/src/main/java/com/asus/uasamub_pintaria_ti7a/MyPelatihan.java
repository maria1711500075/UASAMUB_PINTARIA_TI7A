package com.asus.uasamub_pintaria_ti7a;

public class MyPelatihan {

    String nama_kelas, lokasi;
    String jumlah_kelas;

    public MyPelatihan() {
    }

    public MyPelatihan(String nama_kelas, String lokasi, String jumlah_kelas) {
        this.nama_kelas = nama_kelas;
        this.lokasi = lokasi;
        this.jumlah_kelas = jumlah_kelas;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJumlah_kelas() {
        return jumlah_kelas;
    }

    public void setJumlah_kelas(String jumlah_kelas) {
        this.jumlah_kelas = jumlah_kelas;
    }
}
