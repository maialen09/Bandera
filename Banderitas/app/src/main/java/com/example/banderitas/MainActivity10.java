package com.example.banderitas;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class MainActivity10 extends AppCompatActivity {

    private String usuario;

    @Override
    public void onCreate(Bundle bundle){

        super.onCreate(bundle);
        setContentView(R.layout.activity_main10);

        Intent intent = getIntent();

        usuario = intent.getStringExtra("usuario");

        TextView felicitaciones = findViewById(R.id.textFelicidades);
        felicitaciones.setText("Felicidades " + usuario + " " + "has conseguido la puntuacion máxima!!");




    }




}
