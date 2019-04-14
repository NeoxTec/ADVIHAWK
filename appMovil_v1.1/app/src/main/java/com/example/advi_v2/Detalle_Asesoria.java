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
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

public class Detalle_Asesoria extends AppCompatActivity {
    EditText et_fecha;
    EditText et_hora;
    EditText et_tema;
    EditText et_estado;
    EditText et_asesor;
    RatingBar ll_rating;
    Button bt;

    private String webservice_url = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=get&num_as=";
    private String consulta_asesor = "http://advihawk.herokuapp.com/api_asesores?user_hash=12345&action=get&id_as=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__asesoria);

        et_estado = findViewById(R.id.edo_asesoria);
        et_fecha = findViewById(R.id.fecha_asesoria);
        et_hora = findViewById(R.id.hr_asesoria);
        et_tema = findViewById(R.id.tema_asesoria);
        et_asesor = findViewById(R.id.asesor_asesoria);
        ll_rating = (RatingBar) findViewById(R.id.ratb_das);
        bt = (Button) findViewById(R.id.detas_send);

        ll_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating > 1){
                    bt.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"cambiando valor",Toast.LENGTH_LONG);
                    if (et_estado.getText().toString()== "finalizado"){
                        bt.setEnabled(true);}
                        else bt.setEnabled(false);
                }else {bt.setVisibility(View.INVISIBLE);
                bt.setEnabled(false);}
            }
        });

        Intent intent = getIntent();
        String num_ase = intent.getStringExtra(Lista_Asesorias.ASESORIA);
        webservice_url+=num_ase;
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
                et_estado.setText(estado);
                et_fecha.setText(fecha);
                et_tema.setText(tema);
                et_hora.setText(hora);
                et_asesor.setText(asesor);
                detalle();
                consulta_asesor+=asesor;//actualiza url para tener correo de asesor
                webRest_AS(consulta_asesor);//invoca conslta

            }catch (JSONException e){
                Log.e("Error parseo ",e.getMessage());
            }
        }
    }
    //toma datos json
    private void webRest_AS(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webSResult="";
            while ((line = bufferedReader.readLine()) != null){
                webSResult += line;
            }
            bufferedReader.close();
            parIn(webSResult);
        }catch(Exception e){
            Log.e("Error 110 dato asesor",e.getMessage());
        }
    }

    private void parIn(String jsonResult){
        JSONArray jsonArray = null;
        String correo;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 111 dato asesor",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                correo = jsonObject.getString("correo");

                //Se muestran los datos del cliente en su respectivo EditText
                et_asesor.setText(correo);

            }catch (JSONException e){
                Log.e("Error parseo asesor",e.getMessage());
            }
        }
    }
    private void detalle() {
    }
}
