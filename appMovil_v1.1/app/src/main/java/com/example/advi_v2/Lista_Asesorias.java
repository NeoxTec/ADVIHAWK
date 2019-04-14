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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Lista_Asesorias extends AppCompatActivity {
    MainActivity maa;
    private String getAllAsesoriasURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_solicitante&solicitante=";
    private ListView lista_asesorias;
    private ArrayAdapter adapter;
    private Spinner sp_as;
    private String solicitante;
    public static final String ASESORIA = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asesorias);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_asesorias = findViewById(R.id.lista_solicitudes);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_asesorias.setAdapter(adapter);
        sp_as = findViewById(R.id.tipo_asesoria);

        solicitante = maa.mail_user;
        Log.e("solicitante",solicitante);
        getAllAsesoriasURL=getAllAsesoriasURL+solicitante;
        ObtenerURL();

        lista_asesorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_asesorias.getItemAtPosition(position).toString());
                String datos_asesor[] =
                        lista_asesorias.getItemAtPosition(position).toString().split(":");
                String id_asesoria = datos_asesor[0];
                Log.e("ASESOR",id_asesoria);
                Intent i = new Intent(Lista_Asesorias.this,Detalle_Asesoria.class);
                i.putExtra(solicitante,id_asesoria);
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
    private void ObtenerURL(){
        String res = sp_as.getSelectedItem().toString();
        if (res != "Todo")
            getAllAsesoriasURL=getAllAsesoriasURL+"&tipo="+sp_as.getSelectedItem().toString();
        webREST(getAllAsesoriasURL);
    }

    public void consultar(View view) {
        adapter= null;
        lista_asesorias.setAdapter(adapter);
        ObtenerURL();
        lista_asesorias.setAdapter(adapter);
    }
}
