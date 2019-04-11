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

public class Asesorias_Tomadas extends AppCompatActivity {
    private String getAllAsesoresURL ="http://advi01.herokuapp.com/api_asesorias?user_hash=12345&action=get";
    private ListView lista_asesorias_t;
    private ArrayAdapter adapter;
    public static final String ASESORIAS_T = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asesorias__tomadas);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_asesorias_t = findViewById(R.id.lista_asesorias_t);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_asesorias_t.setAdapter(adapter);
        webREST(getAllAsesoresURL);

        lista_asesorias_t.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_asesorias_t.getItemAtPosition(position).toString());
                String datos_asesor[] =
                        lista_asesorias_t.getItemAtPosition(position).toString().split(":");
                String id_asesoria = datos_asesor[0];
                Log.e("ASESOR",id_asesoria);
                Intent i = new Intent(Asesorias_Tomadas.this,Valoracion_Asesoria.class);
                i.putExtra(ASESORIAS_T,id_asesoria);
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
