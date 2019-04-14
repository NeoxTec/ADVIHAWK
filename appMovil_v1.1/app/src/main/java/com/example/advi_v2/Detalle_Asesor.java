package com.example.advi_v2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detalle_Asesor extends AppCompatActivity {
    EditText et_num_as;
    EditText et_correo;
    EditText et_validado;
    EditText et_habilidades;
    EditText et_nombre;
    EditText et_grado;
    EditText et_carrera;

    public static final String Correo_asesro= "7";
    private String email;
    private String weburl = "http://advihawk.herokuapp.com/api_asesor?user_hash=12345&action=get&id_as=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__asesor);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        et_num_as = findViewById(R.id.id_asesor);
        et_correo = findViewById(R.id.correo_asesor);
        et_validado = findViewById(R.id.validado_asesor);
        et_habilidades = findViewById(R.id.habilidades_asesor);
        et_carrera = findViewById(R.id.carrera_asesor);
        et_grado = findViewById(R.id.grado_asesor);
        et_nombre = findViewById(R.id.name_as);
        Button solicitar = (Button) findViewById(R.id.bt_solicitar_asesoria);

        solicitar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Solicitar_Asesoria.class);
                intent.putExtra(Correo_asesro,email);
                startActivityForResult(intent,0);
            }
        });

        Intent intent = getIntent();
        String num_ase = intent.getStringExtra(Lista_Asesores.ASESOR);
        weburl+=num_ase;
        webServiceRest(weburl);
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
        String grado;
        String carrera;
        String nombre;
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
                validado = jsonObject.getString("validado");
                habilidades = jsonObject.getString("habilidades");
                nombre = jsonObject.getString("nombre");
                grado = jsonObject.getString("grado");
                carrera = jsonObject.getString("carrera");

                //Se muestran los datos del cliente en su respectivo EditText
                et_num_as.setText(num_as);
                et_correo.setText(correo);
                et_validado.setText(validado);
                et_habilidades.setText(habilidades);
                et_nombre.setText(nombre);
                et_grado.setText(grado);
                et_carrera.setText(carrera);
                email = correo;

            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
            }
        }
    }
}
