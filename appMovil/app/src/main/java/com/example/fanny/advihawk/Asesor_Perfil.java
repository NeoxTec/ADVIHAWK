package com.example.fanny.advihawk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Asesor_Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asesor_perfil);
    }

    public void asesoria(View view) {
        Intent intent = new Intent(getApplicationContext(),Solicitar_Asesoria.class);
        startActivity(intent);
    }

    public void Informacion(View view) {
        Intent intent = new Intent(getApplicationContext(),Informacion.class);
        startActivity(intent);
    }
}
