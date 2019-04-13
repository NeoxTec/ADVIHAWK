package com.example.advi_v2;

import android.content.Intent;
import android.os.StrictMode;
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
import java.sql.Time;

public class Detalle_Asesoria extends AppCompatActivity {
    EditText et_fecha;
    EditText et_hora;
    EditText et_lugar;
    EditText et_tema;
    EditText et_estado;

    private String webservice_url = "http://advi01.herokuapp.com/api_asesorias?user_hash=12345&action=get&id_as=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__asesoria);

        et_estado = findViewById(R.id.edo_asesoria);
        et_fecha = findViewById(R.id.fecha_asesoria);
        et_hora = findViewById(R.id.hr_asesoria);
        et_lugar = findViewById(R.id.lugar_asesoria);
        et_tema = findViewById(R.id.tema_asesoria);

        Intent intent = getIntent();
        String num_ase = intent.getStringExtra(Lista_Asesorias.ASESORIA);
        webservice_url+=num_ase;
        webServiceRest(webservice_url);
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String estado;
        String lugar;
        String fecha;
        String hora;
        String tema;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                estado = jsonObject.getString("estado");
                fecha = jsonObject.getString("fecha");
                hora = jsonObject.getString("hora");
                tema = jsonObject.getString("tema");
                lugar = jsonObject.getString("lugar");

                //Se muestran los datos del cliente en su respectivo EditText
                et_estado.setText(estado);
                et_lugar.setText(lugar);
                et_fecha.setText(fecha);
                et_tema.setText(tema);
                et_hora.setText(hora);

            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
            }
        }
    }
}
