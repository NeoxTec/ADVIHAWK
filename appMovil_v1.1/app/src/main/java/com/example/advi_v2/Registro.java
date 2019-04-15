package com.example.advi_v2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Toast toast2;
    private String registro_url = "http://advihawk.herokuapp.com/api_users?user_hash=12345&action=put";

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
        if (!validar()) return;
        int dato = 0;
        String carrera = campoCarrera.getSelectedItem().toString();
        String grado = campoGrado.getSelectedItem().toString();
        Toast toast = Toast.makeText(getApplicationContext(),"Formulario completo",Toast.LENGTH_SHORT);
        toast.show();
        campoTipo.getCheckedRadioButtonId();
        if (campoTipo.getCheckedRadioButtonId()== R.id.rb_ase)
            dato=0;
        else if (campoTipo.getCheckedRadioButtonId()== R.id.rb_est)
            dato=1;
        StringBuilder sb = new StringBuilder();
        sb.append(registro_url);
        sb.append("&nombre="+campoNombre.getText()+" "+campoApe.getText());
        sb.append("&carrera="+carrera);
        sb.append("&tipo="+ dato);
        sb.append("&grado="+grado);
        sb.append("&user="+campoCorreo.getText());
        webPut(sb.toString());
        Log.e("Consulta: ",sb.toString());
        Toast to = Toast.makeText(getApplicationContext(),"Registro realizado, ingrese correo en aplicacion",Toast.LENGTH_SHORT);
        to.show();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
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
    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                description = jsonObject.getString("description");
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }

    private boolean validar() {
        boolean valid = true;
        String sNombre = campoNombre.getText().toString();
        String sApe = campoApe.getText().toString();
        String sEmail = campoCorreo.getText().toString();

        if (sNombre.isEmpty() || sNombre.length() < 3) {
            campoNombre.setError("Ingrese al menos 3 caracteres");
            valid = false;
        } else {
            campoNombre.setError(null);
        }

        if (sEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            campoCorreo.setError("Dirección de correo electrónico no válida");
            valid = false;
        } else {
            campoCorreo.setError(null);
        }

        if (sApe.isEmpty() || sApe.length() < 3 || sApe.length() > 10) {
            campoApe.setError("Ingrese entre 4 a 10 caracteres alfanuméricos");
            valid = false;
        } else {
            campoApe.setError(null);
        }
        return valid;
    }

    public void volver(View view) {
        Toast toast2 = Toast.makeText(getApplicationContext(),"Cancelando solicitud",Toast.LENGTH_SHORT);
        toast2.show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
