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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Lista_Peticiones extends AppCompatActivity {
    private String getPedidosPenURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_asesor_estado&estado=pendiente&asesor="+MainActivity.mail_user;
    private String getAceptadosPenURL ="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get_asesor_estado&estado=aceptado&asesor="+MainActivity.mail_user;
    private ListView lista_solicitudes;
    private ArrayAdapter adapter;
    RadioGroup rg;
    Intent i;
    public static String Det_ped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_peticiones);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lista_solicitudes = findViewById(R.id.lista_solicitudes);
        adapter = new ArrayAdapter(this, R.layout.asesor_item);
        lista_solicitudes.setAdapter(adapter);
        rg= (RadioGroup) findViewById(R.id.peticiones_tipo);
        i = new Intent(Lista_Peticiones.this, Detalle_Peticion.class);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_aceptados:
                        adapter = null;
                        adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                        lista_solicitudes.setAdapter(adapter);
                        webREST(getAceptadosPenURL);
                        i = new Intent(Lista_Peticiones.this, Detalle_Peticion_Aceptada.class);
                        break;
                    case R.id.rb_pendientes:
                        adapter = null;
                        adapter = new ArrayAdapter(getApplicationContext(), R.layout.asesor_item);
                        lista_solicitudes.setAdapter(adapter);
                        webREST(getPedidosPenURL);
                        i = new Intent(Lista_Peticiones.this, Detalle_Peticion.class);
                        break;
                }
            }
        });
        Log.e("URL",getPedidosPenURL);
        webREST(getPedidosPenURL);


        lista_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lista_solicitudes.getItemAtPosition(position).toString());
                String datos_pedido[] =
                        lista_solicitudes.getItemAtPosition(position).toString().split(":");
                String id_ped = datos_pedido[0];
                Log.e("PEDIDO", id_ped);
                Det_ped = id_ped;
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