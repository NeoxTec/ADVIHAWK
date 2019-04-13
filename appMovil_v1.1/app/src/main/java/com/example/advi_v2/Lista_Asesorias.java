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

public class Lista_Asesorias extends AppCompatActivity {
    private String getAllAsesoriasURL ="http://advi01.herokuapp.com/api_asesorias?user_hash=12345&action=get";
    private ListView lista_asesorias;
    private ArrayAdapter adapter;
    private Spinner sp_as;
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
                i.putExtra(ASESORIA,id_asesoria);
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
