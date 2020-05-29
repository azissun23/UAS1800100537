package com.bc181.ximenes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    private Context context;
    private ArrayList<Mahasiswa> dataMahasiswa;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());

    public MahasiswaAdapter(Context context, ArrayList<Mahasiswa> dataMahasiswa) {
        this.context = context;
        this.dataMahasiswa = dataMahasiswa;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_mahasiswa, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        Mahasiswa tempMahasiswa = dataMahasiswa.get(position);
        holder.idMahasiswa = tempMahasiswa.getIdMahasiswa();
        holder.tvNim.setText(tempMahasiswa.getNim());
        holder.tvNama.setText(tempMahasiswa.getNama());
        holder.foto_mh = tempMahasiswa.getFoto();
        holder.jurusan = tempMahasiswa.getJurusan();
        holder.tanggal_lahir = sdFormat.format(tempMahasiswa.getTanggal());
        holder.jenisKelamin = tempMahasiswa.getJenisKelamin();
        holder.alamat = tempMahasiswa.getAlamat();
        holder.telp = tempMahasiswa.getTelp();
        holder.email = tempMahasiswa.getEmail();


        try {
            File file = new File(holder.foto_mh);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgMahasiswa.setImageBitmap(bitmap);
            holder.imgMahasiswa.setContentDescription(holder.foto_mh);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil Foto dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataMahasiswa.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{

        private ImageView imgMahasiswa;
        private TextView tvNim, tvNama ;
        private int idMahasiswa;
        private String foto_mh, jurusan, tanggal_lahir, jenisKelamin, alamat, telp, email;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMahasiswa = itemView.findViewById(R.id.foto_mh);
            tvNim = itemView.findViewById(R.id.nim);
            tvNama = itemView.findViewById(R.id.nama);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Intent bukaMahasiswa = new Intent(context, TampilActivity.class);
            bukaMahasiswa.putExtra("ID", idMahasiswa);
            bukaMahasiswa.putExtra("NIM", tvNim.getText().toString());
            bukaMahasiswa.putExtra("NAMA", tvNama.getText().toString());
            bukaMahasiswa.putExtra("FOTO", foto_mh);
            bukaMahasiswa.putExtra("JURUSAN", jurusan);
            bukaMahasiswa.putExtra("TANGGAL", tanggal_lahir);
            bukaMahasiswa.putExtra("JENIS_KELAMIN", jenisKelamin);
            bukaMahasiswa.putExtra("ALAMAT", alamat);
            bukaMahasiswa.putExtra("TELP", telp);
            bukaMahasiswa.putExtra("EMAIL", email);
            context.startActivity(bukaMahasiswa);

        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idMahasiswa);
            bukaInput.putExtra("NIM", tvNim.getText().toString());
            bukaInput.putExtra("NAMA", tvNama.getText().toString());
            bukaInput.putExtra("FOTO", foto_mh);
            bukaInput.putExtra("JURUSAN", jurusan);
            bukaInput.putExtra("TANGGAL", tanggal_lahir);
            bukaInput.putExtra("JENIS_KELAMIN", jenisKelamin);
            bukaInput.putExtra("ALAMAT", alamat);
            bukaInput.putExtra("TELP", telp);
            bukaInput.putExtra("EMAIL", email);
            context.startActivity(bukaInput);
            return true;
        }
    }
}

