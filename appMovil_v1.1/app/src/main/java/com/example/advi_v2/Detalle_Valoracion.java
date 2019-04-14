package com.example.advi_v2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detalle_Valoracion extends AppCompatActivity {
    TextView et_dia;
    TextView et_hora;
    TextView et_tema;
    TextView et_estado;
    TextView et_solicitante;
    RatingBar rb;

    private String websurl = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as=";
    private String webvalor = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__valoracion);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        et_estado = findViewById(R.id.edo_val);
        et_dia = findViewById(R.id.v_fecha_as);
        et_hora = findViewById(R.id.v_hr_as);
        et_tema = findViewById(R.id.v_tema_as);
        et_solicitante = findViewById(R.id.solicitante);
        rb = (RatingBar) findViewById(R.id.ratb_das);

        Intent intent = getIntent();
        String num_ase = intent.getStringExtra(Lista_Asesorias.ASESORIA);

        websurl += num_ase;
        webvalor += num_ase;
        webServiceRest(websurl);
        Log.e("URL_DETALLE",websurl);
        webRest(webvalor);
        Log.e("VALORACION",webvalor);
    }

    private void webServiceRest(String requestURL) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult = "";
            while ((line = bufferedReader.readLine()) != null) {
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        } catch (Exception e) {
            Log.e("Error 100", e.getMessage());
        }
    }

    private void parseInformation(String jsonResult) {
        JSONArray jsonArray = null;
        String estado;
        String fecha;
        String hora;
        String tema;
        String solicitante;
        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            Log.e("Error 101", e.getMessage());
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                estado = jsonObject.getString("estado");
                fecha = jsonObject.getString("dia");
                hora = jsonObject.getString("hora");
                tema = jsonObject.getString("tema");
                solicitante = jsonObject.getString("solicitante");//toma dato json y lo hace texto

                //Se muestran los datos del cliente en su respectivo EditText
                et_estado.setText(estado);
                et_dia.setText(fecha);
                et_tema.setText(tema);
                et_hora.setText(hora);
                et_solicitante.setText(solicitante);
            } catch (JSONException e) {
                Log.e("Error parseo ", e.getMessage());
            }
        }
    }

    private void webRest(String requestURL) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webResult = "";
            while ((line = bufferedReader.readLine()) != null) {
                webResult += line;
            }
            bufferedReader.close();
            parseInfo(webResult);
        } catch (Exception e) {
            Log.e("Error 103 valoracion", e.getMessage());
        }
    }

    private void parseInfo(String jsonResult) {
        JSONArray jsonArray = null;
        float valor;
        String dato;
        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            Log.e("Error 104 valoracion", e.getMessage());
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                dato = jsonObject.getString("estado");
                valor = Float.parseFloat(dato);
                //Se muestran los datos del cliente en su respectivo EditText
                rb.setRating(valor);
            } catch (JSONException e) {
                Log.e("Error parseo ", e.getMessage());
            }
        }
    }
}