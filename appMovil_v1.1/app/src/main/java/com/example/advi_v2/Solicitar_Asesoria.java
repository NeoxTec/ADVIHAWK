package com.example.advi_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Solicitar_Asesoria extends AppCompatActivity {
    String correo_asesor;
    EditText et_tema;
    EditText et_hora;
    EditText et_dia;//dia=fecha(año,mes,dia)|hora|solicitante|asesor|tema
    private String asesoria_url = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=put";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar__asesoria);
        Intent intent = getIntent();
        correo_asesor = intent.getStringExtra(Detalle_Asesor.Correo_asesro);
        et_hora = findViewById(R.id.et_hora_solicitada);
        et_tema = findViewById(R.id.et_solicitud);
        et_dia = findViewById(R.id.fecha_solicitada);
    }
    public void proceso(View view){
        if (!validar()) return;
        StringBuilder sb = new StringBuilder();
        sb.append(asesoria_url);
        sb.append("&dia="+et_dia.getText());
        sb.append("&hora="+et_hora.getText());
        sb.append("&solicitante="+MainActivity.mail_user);
        sb.append("&asesor="+correo_asesor);
        sb.append("&tema="+et_tema.getText());
        Log.e("URL",sb.toString());
        webPut(sb.toString());
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
            Toast t = Toast.makeText(getApplicationContext(),"Asesoria enviada",Toast.LENGTH_LONG);
            t.show();
            Intent i = new Intent(getApplicationContext(),Lista_Asesores.class);
            startActivity(i);
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
        String sD = et_dia.getText().toString();
        String sH = et_hora.getText().toString();
        String sT = et_tema.getText().toString();

        if (sT.isEmpty() || sT.length() < 7) {
            et_tema.setError("Ingrese el tema de asesoria");
            valid = false;
        } else {
            et_tema.setError(null);
        }

        if (sD.isEmpty() || sD.length() < 8) {
            et_dia.setError("Fecha invalida");
            valid = false;
        } else {
            et_dia.setError(null);
        }

        if (sH.isEmpty() || sH.length() < 4 || sH.length() > 5) {
            et_hora.setError("Ingrese una hora correcta");
            valid = false;
        } else {
            et_hora.setError(null);
        }
        return valid;
    }
}
