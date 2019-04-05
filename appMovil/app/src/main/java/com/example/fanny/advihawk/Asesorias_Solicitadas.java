package com.example.fanny.advihawk;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Asesorias_Solicitadas extends AppCompatActivity {
    private ListView lista_asesores;
    private ArrayList<String>datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asesorias__solicitadas);
    }

}
