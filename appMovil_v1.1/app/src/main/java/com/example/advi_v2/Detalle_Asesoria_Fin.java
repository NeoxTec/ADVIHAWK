package com.example.advi_v2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detalle_Asesoria_Fin extends AppCompatActivity {
    TextView t_asesor, t_estado, t_fecha, t_hora, t_desc,t_valu;
    private String webservice_url = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as=" + Lista_Asesorias.ASESORIA;
    private String web_val = "http://advihawk.herokuapp.com/api_valoracion?user_hash=12345&action=get&asesoria="+Lista_Asesorias.ASESORIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__asesoria__fin);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        t_asesor = findViewById(R.id.daf_as);
        t_estado = findViewById(R.id.daf_edo);
        t_desc = findViewById(R.id.daf_desc);
        t_hora = findViewById(R.id.daf_time);
        t_fecha = findViewById(R.id.daf_date);
        t_valu = findViewById(R.id.daf_val);

        Log.e("URL=", webservice_url);
        webServiceRest(webservice_url);
        web_valor(web_val);
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
        String asesor;
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
                asesor = jsonObject.getString("asesor");//toma dato json y lo hace texto

                //Se muestran los datos del cliente en su respectivo EditText
                t_estado.setText(estado);
                t_fecha.setText(fecha);
                t_desc.setText(tema);
                t_hora.setText(hora);
                t_asesor.setText(asesor);
            } catch (JSONException e) {
                Log.e("Error parseo ", e.getMessage());
            }
        }
    }

    private void web_valor(String requestURL) {
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
            parseI(webServiceResult);
        } catch (Exception e) {
            Log.e("Error 110", e.getMessage());
        }
    }
    private void parseI(String jsonResult) {
        JSONArray jsonArray = null;
        String valor;
        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            Log.e("Error 101", e.getMessage());
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                valor = jsonObject.getString("valor");//toma dato json y lo hace texto

                //Se muestran los datos del cliente en su respectivo EditText
                t_valu.setText(valor);
            } catch (JSONException e) {
                Log.e("Error parseo ", e.getMessage());
            }
        }
    }


}
