package com.bc181.ximenes;

import java.util.Date;

public class Mahasiswa {
    private int idMahasiswa;
    private String nim;
    private String nama;
    private String foto_mh;
    private String jurusan;
    private Date tanggal_lahir;
    private String jenisKelamin;
    private String alamat;
    private String telp;
    private String email;

    public Mahasiswa(int idMahasiswa, String nim, String nama, String foto_mh, String jurusan, Date tanggal_lahir, String jenisKelamin, String alamat, String telp, String email) {
        this.idMahasiswa = idMahasiswa;
        this.nim = nim;
        this.nama = nama;
        this.foto_mh = foto_mh;
        this.jurusan = jurusan;
        this.tanggal_lahir = tanggal_lahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.telp = telp;
        this.email = email;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto_mh;
    }

    public void setFoto(String foto_mh) {
        this.foto_mh = foto_mh;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public Date getTanggal() {
        return tanggal_lahir;
    }

    public void setTanggal(Date tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }


    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
