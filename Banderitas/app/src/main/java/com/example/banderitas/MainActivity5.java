package com.example.banderitas;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.HashMap;

public class MainActivity5 extends AppCompatActivity  {

    @Override

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_main5);

        super.findViewById(R.id.botonPuntuaciones).setOnClickListener(new MainActivity5.Listener());

        super.findViewById(R.id.botonBanderas).setOnClickListener(new MainActivity5.Listener2());


    }





    private class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // HA PULSADO QUE QUIERE VER LAS PUNTUACIONES

            Intent intent = new Intent (MainActivity5.this, MainActivity9.class);
            startActivity(intent);
            finish();


        }


    }

    private class Listener2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // HA PULSADO QUE QUIERE VER LAS BANDERAS

            Bandera bandera = new Bandera();

            HashMap<String, byte []> misBanderas = bandera.getBanderas();


            Intent intent = new Intent (MainActivity5.this, MainActivity6.class);
            intent.putExtra("misBanderas", misBanderas);
            startActivity(intent);
            finish();



        }


    }



}
