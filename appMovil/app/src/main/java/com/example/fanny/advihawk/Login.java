package com.example.fanny.advihawk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registrar(View view) {
        Intent intent = new Intent(getApplicationContext(),Registro.class);
        startActivity(intent);
    }
}
