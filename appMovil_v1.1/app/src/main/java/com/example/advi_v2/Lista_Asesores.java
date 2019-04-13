package com.example.advi_v2;

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

public class Lista_Asesores extends AppCompatActivity {
    private String getAllAsesoresURL ="http://advi01.herokuapp.com/api_asesorias?user_hash=12345&action=get";
    private ListView lista_asesores;
    private ArrayAdapter adapter;
    public static final String ASESOR = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asesores);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_asesores = findViewById(R.id.lista_asesores);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_asesores.setAdapter(adapter);
        webREST(getAllAsesoresURL);

        lista_asesores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_asesores.getItemAtPosition(position).toString());
                String datos_asesor[] =
                        lista_asesores.getItemAtPosition(position).toString().split(":");
                String id_asesor = datos_asesor[0];
                Log.e("ASESOR",id_asesor);
                Intent i = new Intent(Lista_Asesores.this,Detalle_Asesor.class);
                i.putExtra(ASESOR,id_asesor);
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
        String correo;
        String id_as;
        String validado;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error de datos",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_as = jsonObject.getString("id_as");
                correo = jsonObject.getString("correo");
                validado = jsonObject.getString("validado");
                adapter.add(id_as + ": " + correo +" |validado: "+validado);
            }catch (JSONException e){
                Log.e("Error parseo",e.getMessage());
            }
        }

    }
}
