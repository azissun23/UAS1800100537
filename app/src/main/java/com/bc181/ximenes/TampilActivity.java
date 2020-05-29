package com.bc181.ximenes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {
    private ImageView imgMahasiswa;
    private TextView tvNim, tvNama, tvJurusan, tvTanggal,  tvJenisKelamin, tvAlamat, tvTelp, tvEmail;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
    private String emailMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgMahasiswa = findViewById(R.id.foto_mh);
        tvNim = findViewById(R.id.nim);
        tvNama = findViewById(R.id.nama);
        tvJurusan = findViewById(R.id.jurusan);
        tvTanggal = findViewById(R.id.tangall_lahir);
        tvJenisKelamin = findViewById(R.id.jeniskelamin);
        tvAlamat = findViewById(R.id.alamat);
        tvTelp = findViewById(R.id.nomor);
        tvEmail = findViewById(R.id.email);

        Intent terimaData = getIntent();
        tvNim.setText(terimaData.getStringExtra("NIM"));
        tvNama.setText(terimaData.getStringExtra("NAMA"));
        tvJurusan.setText(terimaData.getStringExtra("JURUSAN"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvJenisKelamin.setText(terimaData.getStringExtra("JENIS_KELAMIN"));
        tvAlamat.setText(terimaData.getStringExtra("ALAMAT"));
        tvTelp.setText(terimaData.getStringExtra("TELP"));
        tvEmail.setText(terimaData.getStringExtra("EMAIL"));

        String imgLocation = terimaData.getStringExtra("FOTO");
        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgMahasiswa.setImageBitmap(bitmap);
            imgMahasiswa.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil Foto dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

        emailMahasiswa = terimaData.getStringExtra("EMAIL");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_bagikan)
        {
            Intent bagikanMahasiswa = new Intent(Intent.ACTION_SEND);
            bagikanMahasiswa.putExtra(Intent.EXTRA_SUBJECT, tvNim.getText().toString());
            bagikanMahasiswa.putExtra(Intent.EXTRA_TEXT, emailMahasiswa);
            bagikanMahasiswa.setType("text/plain");
            startActivity(Intent.createChooser(bagikanMahasiswa, "Bagikan Mahasiswa"));
        }

        return super.onOptionsItemSelected(item);

    }
}
