package com.example.advi_v2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

public class Detalle_Asesoria extends AppCompatActivity {
    TextView t_asesor,t_estado,t_fecha,t_hora,t_desc;
    private String webservice_url = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as="+Lista_Asesorias.ASESORIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__asesoria);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        t_asesor = findViewById(R.id.da_ase);
        t_estado = findViewById(R.id.da_edo);
        t_desc = findViewById(R.id.da_desc);
        t_hora = findViewById(R.id.da_time);
        t_fecha = findViewById(R.id.da_date);

        Log.e("URL=", webservice_url);
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
        String fecha;
        String hora;
        String tema;
        String asesor;
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
            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
            }
        }
    }
    // "http://advihawk.herokuapp.com/api_valoracion?user_hash=12345&action=put&num_as=";//valor
}
