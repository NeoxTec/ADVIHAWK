package com.example.advi_v2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detalle_Asesoria_Cal extends AppCompatActivity {
    TextView t_asesor,t_estado,t_fecha,t_hora,t_desc;
    Spinner s_spin;
    private String webservice_url = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as="+Lista_Asesorias.ASESORIA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__asesoria__cal);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        t_asesor = findViewById(R.id.dac_as);
        t_estado = findViewById(R.id.dac_edo);
        t_desc = findViewById(R.id.dac_desc);
        t_hora = findViewById(R.id.dac_time);
        t_fecha = findViewById(R.id.dac_date);
        s_spin = (Spinner)findViewById(R.id.dac_sp_val);
        Log.e("URL=", webservice_url);
        webServiceRest(webservice_url);
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
        String asesor;
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
                asesor = jsonObject.getString("asesor");//toma dato json y lo hace texto

                //Se muestran los datos del cliente en su respectivo EditText
                t_estado.setText(estado);
                t_fecha.setText(fecha);
                t_desc.setText(tema);
                t_hora.setText(hora);
                t_asesor.setText(asesor);
            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
            }
        }
    }

    public void detalle(View view) {
        String URL_UPDATE="http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=update&num_as="+Lista_Asesorias.ASESORIA+"&estado=calificado";
        update(URL_UPDATE);
        String dato= s_spin.getSelectedItem().toString();
        String URL_CAL= "http://advihawk.herokuapp.com/api_valoracion?user_hash=12345&action=put&asesoria="+Lista_Asesorias.ASESORIA;
        URL_CAL+="&valor="+dato;
        webPut(URL_CAL);
        Toast to = Toast.makeText(getApplicationContext(),"Valoración finalizada",Toast.LENGTH_LONG);
        to.show();
        finish();
    }
    private void webPut(String requestURL){
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

    private void update(String requestURL){
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
    // "http://advihawk.herokuapp.com/api_valoracion?user_hash=12345&action=put&num_as=";//valor
}

