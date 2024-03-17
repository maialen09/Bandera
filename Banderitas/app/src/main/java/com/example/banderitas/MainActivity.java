package com.example.banderitas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;




import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

//Esta es la pantalla principal donde se le pregunta al usuario si se quiere registrar o iniciar sesion

public class MainActivity extends AppCompatActivity implements Serializable {

    private BD miBD;  // instancia para mi base de datos

    private static MainActivity miActividad; // la actividad para poder acceder a ella desde otras clases

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MainActivity.miActividad = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.miBD = new BD(this, "MiBaseDeDatos", null, 1); // creamos la base de datos

        if (savedInstanceState != null) {
            String nombreBaseDatos = savedInstanceState.getString("nombre_base_datos");
            miBD = new BD(this, nombreBaseDatos, null, 1);
        } else {
            // Si no hay un estado guardado, crear una nueva instancia de la base de datos
            miBD = new BD(this, "MiBaseDeDatos", null, 1);
        }

        super.findViewById(R.id.boton).setOnClickListener(new Listener()); // boton ---> registrar
        super.findViewById(R.id.boton2).setOnClickListener(new Listener2()); // boton --> iniciar sesion

        createNotificationChannel();  // metodo para permitir notificaciones locales


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar el estado de la base de datos
        outState.putString("nombre_base_datos", "MiBaseDeDatos");
    }


    public static MainActivity getMiActividad() {
        return MainActivity.miActividad;
    }

    public BD getMiBD() {
        return this.miBD;
    }


    private class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);

            startActivity(intent);
            //finish();

        }


    }

    private class Listener2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, MainActivity3.class);

            startActivity(intent);
            //finish();

        }


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}