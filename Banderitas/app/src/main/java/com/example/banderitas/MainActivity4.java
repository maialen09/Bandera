package com.example.banderitas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

public class MainActivity4 extends AppCompatActivity {

    private String usuario = "";

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        if (savedInstanceState != null) {
            usuario = savedInstanceState.getString("usuario");
        } else {
            // Si no hay un estado guardado, obtener el nombre de usuario del Intent
            Intent intent = getIntent();
            usuario = intent.getStringExtra("usuario");
        }

        super.findViewById(R.id.botonJugar).setOnClickListener(new MainActivity4.Listener());

        super.findViewById(R.id.botonOpciones).setOnClickListener(new MainActivity4.Listener2());




    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar el estado del nombre de usuario
        outState.putString("usuario", usuario);
    }





    private class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            // HA PULSADO QUE QUIERE JUGAR

            Intent intent = new Intent (MainActivity4.this, MainActivity7.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
            finish();


        }



    }

    private class Listener2 implements View.OnClickListener{
        @Override
        public void onClick(View v){

            // HA PULSADO QUE QUIRE OPCIONES

            Intent intent = new Intent (MainActivity4.this, MainActivity5.class);
            startActivity(intent);
            finish();


        }



    }






}
