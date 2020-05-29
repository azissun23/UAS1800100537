package com.bc181.ximenes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Mahasiswa> dataMahasiswa = new ArrayList<>();
    private RecyclerView rvMahasiswa;
    private MahasiswaAdapter mahasiswaAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMahasiswa = findViewById(R.id.rv_tampil_mahasiwa);
        dbHandler = new DatabaseHandler(this);
        tampilDataMahasiswa();
    }

    private void tampilDataMahasiswa(){
        dataMahasiswa = dbHandler.getAllMahasiswa();
        mahasiswaAdapter = new MahasiswaAdapter (this, dataMahasiswa);
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(MainActivity.this);
        rvMahasiswa.setLayoutManager(layManager);
        rvMahasiswa.setAdapter(mahasiswaAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.item_menu_tambah){
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert")
            ;            startActivity(bukaInput);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDataMahasiswa();
    }
}
