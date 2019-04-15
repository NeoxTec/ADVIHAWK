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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Lista_Asesorias extends AppCompatActivity {
     private String getAse_peURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_solicitante_estado&estado=pendiente&solicitante="+MainActivity.mail_user;
     private String getAse_acURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_solicitante_estado&estado=aceptado&solicitante="+MainActivity.mail_user;
     private String getAse_reURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_solicitante_estado&estado=rechazado&solicitante="+MainActivity.mail_user;
     private String getAse_fiURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_solicitante_estado&estado=finalizado&solicitante="+MainActivity.mail_user;
    private  String getAse_caURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_solicitante_estado&estado=calificado&solicitante="+MainActivity.mail_user;
    ListView lista_asesorias;
    private ArrayAdapter adapter;
    private Spinner sp_as;
    public static String ASESORIA;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asesorias);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_asesorias = findViewById(R.id.lista_solicitudes);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        sp_as =(Spinner)findViewById(R.id.tipo_asesoria);

        lista_asesorias.setAdapter(adapter);
        Log.e("solicitante",getAse_peURL);
        webREST(getAse_peURL);
        i = new Intent(Lista_Asesorias.this,Detalle_Asesoria.class);

        sp_as.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int itemSeleccionado, long l) {
                if (itemSeleccionado==0) {
                    adapter =null;
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                    lista_asesorias.setAdapter(adapter);
                    webREST(getAse_peURL);
                    i = new Intent(Lista_Asesorias.this,Detalle_Asesoria.class);
                }else  if(itemSeleccionado==1){
                    adapter =null;
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                    lista_asesorias.setAdapter(adapter);
                    webREST(getAse_acURL);
                    i = new Intent(Lista_Asesorias.this,Detalle_Asesoria.class);
                }else if(itemSeleccionado==2){
                    adapter =null;
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                    lista_asesorias.setAdapter(adapter);
                    webREST(getAse_reURL);
                    i = new Intent(Lista_Asesorias.this,Detalle_Asesoria.class);
                }else if(itemSeleccionado==3){
                    adapter =null;
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                    lista_asesorias.setAdapter(adapter);
                    webREST(getAse_fiURL);
                    i = new Intent(Lista_Asesorias.this,Detalle_Asesoria_Fin.class);
                }else{
                    adapter =null;
                    adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                    lista_asesorias.setAdapter(adapter);
                    webREST(getAse_caURL);
                    i = new Intent(Lista_Asesorias.this,Detalle_Asesoria_Cal.class);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lista_asesorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_asesorias.getItemAtPosition(position).toString());
                String datos_asesor[] =
                        lista_asesorias.getItemAtPosition(position).toString().split(":");
                ASESORIA = datos_asesor[0];
                Log.e("ASESOR",ASESORIA);
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
        String asesor;
        String num_as;
        String dia;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error de datos",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                num_as = jsonObject.getString("num_as");
                asesor = jsonObject.getString("asesor");
                dia = jsonObject.getString("dia");
                adapter.add(num_as + ": " + asesor +" |dia: "+dia);
            }catch (JSONException e){
                Log.e("Error parseo",e.getMessage());
            }
        }
    }

    }

