package com.example.fanny.advihawk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private String getAllAsesoresURL ="http://advi01.herokuapp.com/api_users?user_hash=12345&action=get";
    EditText et_correo;
    public String correo;
    Button bt_ins, bt_reg;
    ArrayList lista;
    public static final String Correo = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_correo = findViewById(R.id.et_correo);
        bt_ins = findViewById(R.id.bt_is);
        bt_reg = findViewById(R.id.bt_reg);

        bt_ins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAllAsesoresURL= getAllAsesoresURL+"&user="+et_correo.getText().toString();
                webREST(getAllAsesoresURL);
            }
        });
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

        private void webREST(String respuesta){
            try{
                URL url = new URL(respuesta);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.e("Abriendo conexion",connection.toString());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                String webResult="";
                while ((line = bufferedReader.readLine()) != null){
                    webResult += line;
                }
                bufferedReader.close();
                parseInformation(webResult);
            }catch(Exception e){
                Log.e("Error de conexion",e.getMessage());
            }
        }

        private void parseInformation(String jsonResult){
            JSONArray jsonArray = null;
            String correo;
            try{
                jsonArray = new JSONArray(jsonResult);
            }catch (JSONException e){
                Log.e("Error de datos",e.getMessage());
            }
            for(int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    correo = jsonObject.getString("correo");
                    lista.add(correo);
                }catch (JSONException e){
                    Log.e("Error parseo",e.getMessage());
                }
            }iniciar();
            }


    public void registrar() {
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }

    public void iniciar() {
        correo = et_correo.getText().toString();
        for (int i=0;i<lista.size();i++){
            if(lista.get(i).toString()==correo) {
                Intent intent = new Intent(getApplicationContext(), Acceso.class);
                intent.putExtra(Correo,correo);
                startActivity(intent);
                startActivity(intent);
                finish();
            } else i++;
    }
}

}
