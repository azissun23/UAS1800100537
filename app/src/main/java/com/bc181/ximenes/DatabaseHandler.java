package com.bc181.ximenes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_mahasiswa";
    private final static String TABLE_MAHASISWA = "t_mahasiswa";
    private final static String KEY_ID_MAHASISWA = "ID_Mahasiswa";
    private final static String KEY_NIM = "Nim";
    private final static String KEY_NAMA = "Nama";
    private final static String KEY_FOTO = "Foto";
    private final static String KEY_JURUSAN = "Jurusan";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_JENIS_KELAMIN = "Jenis_Kelamin";
    private final static String KEY_ALAMAT = "Alamat";
    private final static String KEY_TELP = "Telp";
    private final static String KEY_EMAIL = "Email";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAHASISWA = " CREATE TABLE " + TABLE_MAHASISWA
                + "(" + KEY_ID_MAHASISWA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + KEY_NIM + " TEXT," + KEY_NAMA + " TEXT, " + KEY_FOTO + " TEXT, " + KEY_JURUSAN + " TEXT, "  + KEY_TGL + " DATE, "
                + KEY_JENIS_KELAMIN + " TEXT, " + KEY_ALAMAT + " TEXT, "
                + KEY_TELP + " TEXT , " + KEY_EMAIL + " TEXT);";

        db.execSQL(CREATE_TABLE_MAHASISWA);
        inisialisasiMahasiswaAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_MAHASISWA;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahMahasiswa(Mahasiswa dataMahasiswa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_NAMA, dataMahasiswa.getNama());
        cv.put(KEY_FOTO, dataMahasiswa.getFoto());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_JENIS_KELAMIN, dataMahasiswa.getJenisKelamin());
        cv.put(KEY_ALAMAT, dataMahasiswa.getAlamat());
        cv.put(KEY_TELP, dataMahasiswa.getTelp());
        cv.put(KEY_EMAIL, dataMahasiswa.getEmail());

        db.insert(TABLE_MAHASISWA, null, cv);
        db.close();
    }

    public void tambahMahasiswa(Mahasiswa dataMahasiswa, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_NAMA, dataMahasiswa.getNama());
        cv.put(KEY_FOTO, dataMahasiswa.getFoto());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_JENIS_KELAMIN, dataMahasiswa.getJenisKelamin());
        cv.put(KEY_ALAMAT, dataMahasiswa.getAlamat());
        cv.put(KEY_TELP, dataMahasiswa.getTelp());
        cv.put(KEY_EMAIL, dataMahasiswa.getEmail());

        db.insert(TABLE_MAHASISWA, null, cv);
    }

    public void editMahasiswa(Mahasiswa dataMahasiswa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_NAMA, dataMahasiswa.getNama());
        cv.put(KEY_FOTO, dataMahasiswa.getFoto());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_JENIS_KELAMIN, dataMahasiswa.getJenisKelamin());
        cv.put(KEY_ALAMAT, dataMahasiswa.getAlamat());
        cv.put(KEY_TELP, dataMahasiswa.getTelp());
        cv.put(KEY_EMAIL, dataMahasiswa.getEmail());

        db.update(TABLE_MAHASISWA, cv, KEY_ID_MAHASISWA + "=?", new String[]{String.valueOf(dataMahasiswa.getIdMahasiswa())});

        db.close();
    }

    public void hapusMahasiswa(int idMahasiswa) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_MAHASISWA, KEY_ID_MAHASISWA + "=?", new String[]{String.valueOf(idMahasiswa)});
        db.close();
    }

    public ArrayList<Mahasiswa> getAllMahasiswa() {
        ArrayList<Mahasiswa> dataMahasiswa = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MAHASISWA;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do{
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString( 5));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Mahasiswa tempMahasiswa = new Mahasiswa(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        tempDate,
                        csr.getString(6),
                        csr.getString(7),
                        csr.getString(8),
                        csr.getString(9)
                );

                dataMahasiswa.add(tempMahasiswa);
            } while (csr.moveToNext());
        }

        return dataMahasiswa;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private  void inisialisasiMahasiswaAwal(SQLiteDatabase db){
        int idMahasiswa = 0;
        Date tempDate = new Date();

        //menambah data Mahasiswa
        try{
            tempDate = sdFormat.parse("13/03/2000 06:22");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa mahasiswa1 = new Mahasiswa(
                idMahasiswa,
                "1800100111","JUSTIN",
                storeImageFile(R.drawable.foto1),
                "SITEM KOMPUTER",
                tempDate,
                "LAKI_LAKI",
                "AMERIKA",
                "+7628299000098","jutin@gmail.com"
        );

        tambahMahasiswa(mahasiswa1, db);
        idMahasiswa++;

        //menambah data Mahasiswa
        try{
            tempDate = sdFormat.parse("13/03/2000 06:22");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa mahasiswa2 = new Mahasiswa(
                idMahasiswa,
                "1800100222","JUBY",
                storeImageFile(R.drawable.foto2),
                "SITEM INFORMASI",
                tempDate,
                "LAKI_LAKI",
                "AUSTRALIA",
                "+7628299000098","JUBI@gmail.com"
        );

        tambahMahasiswa(mahasiswa2, db);
        idMahasiswa++;

    }
}
