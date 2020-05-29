package com.bc181.ximenes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editNim, editNama, editJurusan, editTanggal, editJenisKelamin, editAlamat, editTelp, editEmail;
    private ImageView ivMahasiswa;
    private DatabaseHandler dbHandler;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private boolean updateData = false;
    private int idMahasiswa = 0;
    private Button btnSimpan, btnPilihTanggal;
    private String tanggalMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editNim =findViewById(R.id.edit_nim);
        editNama =findViewById(R.id.edit_nama);
        editJurusan = findViewById(R.id.edit_jurusan);
        editTanggal = findViewById(R.id.edit_tanggal);
        editJenisKelamin = findViewById(R.id.edit_kelamin);
        editAlamat = findViewById(R.id.edit_alamat);
        editTelp = findViewById(R.id.edit_telp);
        editEmail = findViewById(R.id.edit_email);
        ivMahasiswa = findViewById(R.id.foto_mh);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnPilihTanggal = findViewById(R.id.btn_pilih_tanggal);

        dbHandler = new DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if (data.getString("OPERASI").equals("insert")){
            updateData = false;
        }else {
            updateData = true;
            idMahasiswa = data.getInt("ID");
            editNim.setText(data.getString("NIM"));
            editNama.setText(data.getString("NAMA"));
            editJurusan.setText(data.getString("JURUSAN"));
            editTanggal.setText(data.getString("TANGGAL"));
            editJenisKelamin.setText(data.getString("JENIS_KELAMIN"));
            editAlamat.setText(data.getString("ALAMAT"));
            editTelp.setText(data.getString("TELP"));
            editEmail.setText(data.getString("EMAIL"));
            loadImageFromInternalStorage(data.getString("FOTO"));
        }

        ivMahasiswa.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        btnPilihTanggal.setOnClickListener(this);

    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines. ON)
                .setAspectRatio(4,3)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                } catch (FileNotFoundException er) {
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kesalahan dalam pemilihan Foto", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Anda belum memilih Foto", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx) {
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("image", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "mahasiswa-"+ uniqueID + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException er) {
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imageLocation) {
        try {
            File file = new File(imageLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ivMahasiswa.setImageBitmap(bitmap);
            ivMahasiswa.setContentDescription(imageLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil foto dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if (updateData==true) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        }else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.item_menu_hapus){
            hapusData();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void simpanData() {
        String nim, nama, foto_mh, jurusan, jenisKelamin, alamat, telp, email;
        Date tangall_lahir = new Date();
        nim = editNim.getText().toString();
        nama = editNama.getText().toString();
        foto_mh = ivMahasiswa.getContentDescription().toString();
        jurusan = editJurusan.getText().toString();
        jenisKelamin = editJenisKelamin.getText().toString();
        alamat = editAlamat.getText().toString();
        telp = editTelp.getText().toString();
        email = editEmail.getText().toString();

        try{
            tangall_lahir = sdFormat.parse(editTanggal.getText().toString());
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Mahasiswa tempMahasiswa = new Mahasiswa(
                idMahasiswa, nim,nama, foto_mh, jurusan, tangall_lahir, jenisKelamin, alamat, telp, email
        );

        if (updateData == true) {
            dbHandler.editMahasiswa(tempMahasiswa);
            Toast.makeText(this, "Data Mahasiswa telah diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahMahasiswa(tempMahasiswa);
            Toast.makeText(this, "Data Mahasiswa telah ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void hapusData() {
        dbHandler.hapusMahasiswa(idMahasiswa);
        Toast.makeText(this, "Data Mahasisa dihapus", Toast.LENGTH_SHORT).show();
    }

    private void pilihTanggal() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tanggalMahasiswa = dayOfMonth + "/" + month + "/" + year;

                pilihWaktu();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        pickerDialog.show();
    }

    private void pilihWaktu() {
        final  Calendar calendar = Calendar.getInstance();
        TimePickerDialog pickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tanggalMahasiswa = tanggalMahasiswa + " " + hourOfDay + ":" + minute;
                editTanggal.setText(tanggalMahasiswa);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        pickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if(idView == R.id.btn_simpan) {
            simpanData();
        } else if(idView == R.id.foto_mh) {
            pickImage();
        } else if (idView == R.id.btn_pilih_tanggal) {
            pilihTanggal();
        }
    }
}

