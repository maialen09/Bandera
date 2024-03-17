package com.example.banderitas;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class MainActivity8 extends AppCompatActivity {

    private String usuario;

    private int puntuaciones;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        if (savedInstanceState != null) {
            usuario = savedInstanceState.getString("usuario");
            puntuaciones = savedInstanceState.getInt("puntuaciones", -1);
        } else {
            // Si no hay un estado guardado, obtenemos los datos de la Intent
            Intent intent = getIntent();
            usuario = intent.getStringExtra("usuario");
            puntuaciones = intent.getIntExtra("puntuaciones", -1);
        }



        TextView textView = findViewById(R.id.textView);
        textView.setText("La ultima puntuacion para el usuario " + usuario + " " + " es:  " + puntuaciones );

        // buscamos la mejor puntuacion de este usuario en la base de datos
        int mejorPuntuacion = MainActivity.getMiActividad().getMiBD().obtenerMejorPuntuacion(usuario);

        TextView textView1 = findViewById(R.id.textView4);
        textView1.setText("La mejor puntuacion para el usuario " + usuario + " " + "es:"+ " " + mejorPuntuacion );

        super.findViewById(R.id.botonvolverajugar).setOnClickListener(new MainActivity8.Listener());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario", usuario);
        outState.putInt("puntuaciones", puntuaciones);
    }






    private class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            //Volver a jugar
            Intent intent = new Intent (MainActivity8.this, MainActivity7.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
            finish();

        }



    }



}
