package com.example.fanny.advihawk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityDetalles_Asesor extends AppCompatActivity {
        EditText et_num_as;
        EditText et_correo;
        EditText et_grado;
        EditText et_validado;
        EditText et_habilidades;

    private String webservice_url = "http://advi01.herokuapp.com/api_asesor?user_hash=12345&action=get&id_as=";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        setContentView(R.layout.activity_detalles_asesor);

        et_num_as = findViewById(R.id.id_asesor);
        et_correo = findViewById(R.id.correo_asesor);
        et_grado = findViewById(R.id.grado_asesor);
        et_validado = findViewById(R.id.validado_asesor);
        et_habilidades = findViewById(R.id.habilidades_asesor);

        Intent intent = getIntent();
        String num_ase = intent.getStringExtra(Lista_asesores.ASESOR);
        webservice_url= webservice_url+num_ase;
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
        String num_as;
        String validado;
        String correo;
        String habilidades;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                num_as = jsonObject.getString("id_as");
                correo = jsonObject.getString("correo");
                validado = jsonObject.getString("validadoo");
                habilidades = jsonObject.getString("habilidades");

                //Se muestran los datos del cliente en su respectivo EditText
                et_num_as.setText(num_as);
                et_correo.setText(correo);
                et_validado.setText(validado);
                et_habilidades.setText(habilidades);

            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
        }
    }
}

    public void Solicitar(View view) {
        Intent intent = new Intent(getApplicationContext(),Solicitar_Asesoria.class);
        startActivity(intent);
    }
}