package com.example.advi_v2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String getAllAsesoresURL ="http://advihawk.herokuapp.com/api_users?user_hash=12345&action=get";
    EditText et_correo;
    public static String mail_user;
    public int tipo_user;
    Button bt_ins, bt_reg;
    ArrayList lista;
    public static final String Correo = "1";
    public static final String Tipo="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
        } else {
            // No hay conexión a Internet en este momento
            Toast t =Toast.makeText(getApplicationContext(),"No hay acceso a Internet",Toast.LENGTH_LONG);
            t.show();
        }

        et_correo = findViewById(R.id.et_correo);
        bt_ins = findViewById(R.id.bt_is);
        bt_reg = findViewById(R.id.bt_reg);

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(); }});}

    private void webREST(String respuesta){
        try{
            URL url = new URL(respuesta);
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
            Toast toast=Toast.makeText(getApplicationContext(),"Correo no registrado", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void parseInformation(String jsonResult){
        String datos;
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error de datos",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mail_user = jsonObject.getString("user");
                tipo_user = jsonObject.getInt("tipo");
                datos = mail_user+ " "+tipo_user;
                Log.e("datos tenidos",datos);
            }catch (JSONException e){
                Log.e("Error parseo",e.getMessage());
            }
        }iniciar(mail_user, tipo_user);
    }

    public void registrar() {
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }

    public void iniciar(String dato,int usuario) {
        if (usuario == 0){
            Intent intent = new Intent(getApplicationContext(), Acceso_Asesor.class);
            intent.putExtra(Correo,dato);
            intent.putExtra(Tipo,usuario);
            startActivity(intent);
            finish();
        }
        else if(usuario == 1){
            Intent intent = new Intent(getApplicationContext(), Acceso_Estandar.class);
            intent.putExtra(Correo,dato);
            intent.putExtra(Tipo,usuario);
            startActivity(intent);
            finish();
        }
    }

    public void ingresar(View view) {
        if (!validar()) return;
        mail_user = et_correo.getText().toString();
        getAllAsesoresURL = getAllAsesoresURL + "&user=" + mail_user;
        webREST(getAllAsesoresURL);
    }
    private boolean validar() {
        boolean valid = true;
        String sEmail = et_correo.getText().toString();
        if (sEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            et_correo.setError("Dirección de correo electrónico no válida");
            valid = false;
        }
        return valid;
    }
}
