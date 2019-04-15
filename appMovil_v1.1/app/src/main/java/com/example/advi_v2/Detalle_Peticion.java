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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detalle_Peticion extends AppCompatActivity {
    TextView et_fecha, et_hora, et_tema,et_estado, et_solicitante;
    Button ace,rec;

    private String websurl = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__peticion);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        et_estado = findViewById(R.id.edo_asesoria);
        et_fecha = findViewById(R.id.fecha_asesoria);
        et_hora = findViewById(R.id.hr_asesoria);
        et_tema = findViewById(R.id.tema_asesoria);
        et_solicitante = findViewById(R.id.solicitante);

        websurl+=Lista_Peticiones.Det_ped;
        Log.e("URL_PEDIDO",websurl);
        webServiceRest(websurl);
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
        String solicitante;
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
                solicitante = jsonObject.getString("solicitante");//toma dato json y lo hace texto

                //Se muestran los datos del cliente en su respectivo EditText
                et_estado.setText(estado);
                et_fecha.setText(fecha);
                et_tema.setText(tema);
                et_hora.setText(hora);
                et_solicitante.setText(solicitante);
            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
            }
        }
    }

    private void enviar(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
                Log.e("Resultado=",webServiceResult);
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }
    public  void aceptar(View v){
        String URL = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=update&num_as="+Lista_Peticiones.Det_ped+"&estado=aceptado";
        enviar(URL);
        Log.e("ENVIO",URL);
        Toast te = Toast.makeText(getApplicationContext(),"Solicitud Aceptada",Toast.LENGTH_SHORT);
        te.show();
        finish();
    }

    public void rechazar(View v){
        String URL1 = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=update&num_as="+Lista_Peticiones.Det_ped+"&estado=rechazado";
        Log.e("ENVIO",URL1);
        enviar(URL1);
        Toast to = Toast.makeText(getApplicationContext(),"Asesoria Cancelada",Toast.LENGTH_SHORT);
        to.show();
        finish();
    }

}
