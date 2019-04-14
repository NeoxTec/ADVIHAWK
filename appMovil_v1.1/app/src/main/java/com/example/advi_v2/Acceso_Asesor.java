package com.example.advi_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Acceso_Asesor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        public static final String Correo_A = "3";
        private String asesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso__asesor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        TextView correo = (TextView) header.findViewById(R.id.tv_nh_asesor);
        asesor = MainActivity.mail_user;
        correo.setText(asesor);

        TextView tipo = (TextView) header.findViewById(R.id.tv_nh_asesor_tipo);
        tipo.setText("Usuario Asesor");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onPause() {
      super.onPause();
    //La app esta minimizada!.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acceso__asesor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.asesorias_edo) {
            Intent intent = new Intent(getApplicationContext(),Lista_Asesorias.class);
            intent.putExtra(Correo_A,asesor);
            startActivity(intent);
        } else if (id == R.id.valoraciones) {
            Intent intent = new Intent(getApplicationContext(), Lista_Valoraciones.class);
            intent.putExtra(Correo_A,asesor);
            startActivity(intent);
        } else if (id == R.id.lista_asesores) {
            Intent intent = new Intent(getApplicationContext(),Lista_Asesores.class);
            startActivity(intent);
        } else if (id == R.id.peticiones) {
            Intent intent = new Intent(getApplicationContext(), Lista_Peticiones.class);
            intent.putExtra(Correo_A,asesor);
            startActivity(intent);
        }else if(id == R.id.session_end){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
