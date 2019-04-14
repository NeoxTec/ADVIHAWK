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
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.advi_v2.Lista_Asesorias.ASESORIA;

public class Lista_Valoraciones extends AppCompatActivity {
    MainActivity maa;
    private String getAllValoracionURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_asesor_estado&estado=finalizado&asesor=";
    private ListView lista_valoraciones;
    private ArrayAdapter adapter;
    public static final String VALORACION = "1";
    private String asesor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_valoraciones);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_valoraciones = findViewById(R.id.lista_valoraciones);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_valoraciones.setAdapter(adapter);

        getAllValoracionURL=getAllValoracionURL+maa.mail_user;
        Log.e("URL",getAllValoracionURL);
        webREST(getAllValoracionURL);

        lista_valoraciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_valoraciones.getItemAtPosition(position).toString());
                String datos_valoracion[] =
                        lista_valoraciones.getItemAtPosition(position).toString().split(":");
                String id_valoracion = datos_valoracion[0];
                Log.e("ASESOR",id_valoracion);
                Intent i = new Intent(Lista_Valoraciones.this,Detalle_Valoracion.class);
                i.putExtra(VALORACION,id_valoracion);
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
        String tema;
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
                dia = jsonObject.getString("dia");
                tema = jsonObject.getString("tema");
                adapter.add(num_as + ":" + "dia: "+dia+"|"+tema);
            }catch (JSONException e){
                Log.e("Error parseo",e.getMessage());
            }
        }
    }
}
