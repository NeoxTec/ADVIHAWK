package com.example.fanny.advihawk;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registro extends AppCompatActivity {
    private EditText campoCorreo,campoPass,campoNombre,campoApe;
    private Spinner campoGrado,campoCarrera;
    private RadioGroup campoTipo;
    private String registro_url = "http://advi01.herokuapp.com/api_users?user_hash=12345&action=put";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        campoNombre = (EditText)findViewById(R.id.nombre);
        campoApe = (EditText)findViewById(R.id.apellidos);
        campoCorreo = (EditText) findViewById(R.id.correo_reg);
        campoTipo = (RadioGroup) findViewById(R.id.tipo);
        campoGrado = (Spinner) findViewById(R.id.spinner_grado);
        campoCarrera = (Spinner) findViewById(R.id.spinner);
    }

    public void registro(View view) {
        String carrera = campoCarrera.getSelectedItem().toString();
        String grado = campoGrado.getSelectedItem().toString();
        int num = 2;
        validar(num);
        StringBuilder sb = new StringBuilder();
        sb.append(registro_url);
        sb.append("&nombre="+campoNombre.getText()+" "+campoApe.getText());
        sb.append("&carera="+carrera);
        sb.append("&tipo="+ num);
        sb.append("&grado="+grado);
        webPut(sb.toString());
        Log.e("Consulta: ",sb.toString());
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
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }
    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }

    private void validar(Integer dato){
        campoTipo.getCheckedRadioButtonId();
        if (campoTipo.getCheckedRadioButtonId()== R.id.rb_ase)
            dato=1;
        else if (campoTipo.getCheckedRadioButtonId()== R.id.rb_est)
            dato = 0;
    }
}
