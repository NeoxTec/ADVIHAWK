package com.example.fanny.advihawk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Registro extends AppCompatActivity {
    private EditText campoCorreo,campoPass,campoNombre,campoApe;
    private Spinner campoGrado,campoCarrera;
    private RadioGroup campoTipo;
    public String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        campoNombre = (EditText)findViewById(R.id.nombre);
        campoApe = (EditText)findViewById(R.id.apellidos);
        campoCorreo = (EditText) findViewById(R.id.correo_reg);
        campoPass = (EditText) findViewById(R.id.pass_reg);
        campoTipo = (RadioGroup) findViewById(R.id.tipo);
        campoGrado = (Spinner) findViewById(R.id.spinner_grado);
        campoCarrera = (Spinner) findViewById(R.id.spinner);
    }
}
