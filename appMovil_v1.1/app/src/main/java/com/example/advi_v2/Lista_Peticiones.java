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

public class Lista_Peticiones extends AppCompatActivity {
    private String getAllPedidosURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get";
    private ListView lista_solicitudes;
    private ArrayAdapter adapter;
    private Spinner sp_as;
    MainActivity maa;
    public static final String PEDIDO = "1";
    private String asesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_peticiones);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_solicitudes = findViewById(R.id.lista_solicitudes);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_solicitudes.setAdapter(adapter);
        sp_as = findViewById(R.id.tipo_asesoria);

        Intent in = getIntent();

        getAllPedidosURL=getAllPedidosURL+"_asesor&asesor="+maa.mail_user;
        Log.e("URL",getAllPedidosURL);
        //webREST(getAllPedidosURL);


        lista_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_solicitudes.getItemAtPosition(position).toString());
                String datos_pedido[] =
                        lista_solicitudes.getItemAtPosition(position).toString().split(":");
                String id_ped = datos_pedido[0];
                Log.e("PEDIDO", id_ped);
                Intent i = new Intent(Lista_Peticiones.this, Detalle_Peticion.class);
                i.putExtra(PEDIDO, id_ped);
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
            String solicitante;
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
                    solicitante = jsonObject.getString("solicitante");
                    adapter.add(num_as + ": " + solicitante +" |dia: "+dia);
                }catch (JSONException e){
                    Log.e("Error parseo",e.getMessage());
                }
            }
        }
}