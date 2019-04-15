package com.example.advi_v2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class Solicitar_Asesoria extends AppCompatActivity implements View.OnClickListener {
    String correo_asesor;
    Button bfecha,bhora;
    EditText et_hora,et_tema,et_dia;//dia=fecha(año,mes,dia)|hora|solicitante|asesor|tema
    private  int dia,mes,ano,hora,minutos;
    private String asesoria_url = "http://advihawk.herokuapp.com/api_asesorias?user_hash=12345&action=put";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar__asesoria);
        Intent intent = getIntent();
        bfecha = (Button) findViewById(R.id.bt_fecha_solicitada);
        bhora = (Button) findViewById(R.id.bt_hora_solicitada);
        correo_asesor = intent.getStringExtra(Detalle_Asesor.Correo_asesro);
        et_hora = findViewById(R.id.et_hora_solicitada);
        et_tema = findViewById(R.id.et_solicitud);
        et_dia = findViewById(R.id.fecha_solicitada);

        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
    }
        @Override
        public void onClick(View v) {
            if(v==bfecha){
                final Calendar c= Calendar.getInstance();
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                ano=c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        et_dia.setText(dayOfMonth+"."+(monthOfYear+1)+"."+year);
                    }
                },ano,mes,dia);
                datePickerDialog.show();
            }
            if (v==bhora){
                final Calendar c= Calendar.getInstance();
                hora=c.get(Calendar.HOUR_OF_DAY);
                minutos=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        et_hora.setText(hourOfDay+":"+minute);
                    }
                },hora,minutos,false);
                timePickerDialog.show();
            }
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
            finish();
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
        String sFecha = et_dia.getText().toString();
        String sH = et_hora.getText().toString();
        String sT = et_tema.getText().toString();

        if (sT.isEmpty() || sT.length() < 7) {
            et_tema.setError("Ingrese el tema de asesoria");
            valid = false;
        } else {
            et_tema.setError(null);
        }

        if (sH.isEmpty()) {
            et_hora.setError("Ingrese una hora para la asesoria");
            valid = false;
        } else {
            et_hora.setError(null);
        }

        if (sFecha.isEmpty()) {
            et_dia.setError("Ingrese una fecha para la asesoria");
            valid = false;
        } else {
            et_dia.setError(null);
        }
        return valid;
    }
}
