package com.example.fanny.advihawk;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Solicitudes_Asesorias extends AppCompatActivity {
    private String getSolicitudesURL ="http://advi01.herokuapp.com/api_asesorias?user_hash=12345&action=get";
    private ListView lista_solicitudes;
    private ArrayAdapter adapter;
    public static final String SOLICITUD = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes__asesorias);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_solicitudes = findViewById(R.id.lista_solicitudes);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_solicitudes.setAdapter(adapter);
        webREST(getSolicitudesURL);

        lista_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_solicitudes.getItemAtPosition(position).toString());
                String datos_asesoria[] =
                        lista_solicitudes.getItemAtPosition(position).toString().split(":");
                String id_solicitu = datos_asesoria[0];
                Log.e("SOLICITUD",id_solicitu);
                Intent i = new Intent(Solicitudes_Asesorias.this,Valoracion_Asesoria.class);
                i.putExtra(SOLICITUD,id_solicitu);
                startActivity(i);
            }
        });
    }

    private void webREST(String respuestaURL){
        try{
            URL url = new URL(respuestaURL);
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
        String id_asesoria;
        String hora;
        String dia;
        String asesor;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error de datos",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_asesoria = jsonObject.getString("num_as");
                hora = jsonObject.getString("hora");
                dia = jsonObject.getString("dia");
                asesor = jsonObject.getString("asesor");
                adapter.add(id_asesoria + ": " + dia +" |hora: "+hora+"|asesor: "+asesor);
            }catch (JSONException e){
                Log.e("Error parseo",e.getMessage());
            }
        }
    }
}
