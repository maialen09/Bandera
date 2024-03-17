package com.example.banderitas;

import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

public class Bandera {


    private HashMap<String, byte[]> banderas;


    public Bandera() {


        // en la constructora construimos el hashmap xon todas las banderas

        this.banderas = new HashMap<>();



        llenarHashMapConImagenes(MainActivity.getMiActividad());


    }

    public void llenarHashMapConImagenes(Context context) {

        String archivoN = "";

        AssetManager assetManager = context.getAssets();

        try {
            // Lista todos los archivos en la carpeta "assets"
            String[] archivos = assetManager.list("");

            for (String archivo : archivos) {
                // Verificar si el archivo es una imagen
                if (archivo.endsWith(".jpg") || archivo.endsWith(".jpeg") || archivo.endsWith(".png")) {
                    // Leer el archivo en un array de bytes
                    byte[] contenidoArchivo = leerArchivoDesdeAssets(context, archivo);
                    if (contenidoArchivo != null) {

                        if (archivo.endsWith(".jpg")){

                            archivoN = archivo.replace(".jpg", "");

                        }
                        if (archivo.endsWith(".png")){

                            archivoN = archivo.replace(".png", "");

                        }

                        banderas.put(archivoN, contenidoArchivo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] leerArchivoDesdeAssets(Context context, String nombreArchivo) {
        try {

            // Abrir el archivo desde "assets" como un InputStream
            InputStream inputStream = context.getAssets().open(nombreArchivo);

            // Leer el contenido del archivo en un array de bytes
            byte[] contenidoArchivo = new byte[inputStream.available()];
            inputStream.read(contenidoArchivo);

            // Cerrar el InputStream
            inputStream.close();

            return contenidoArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, byte []> getBanderas(){

        return this.banderas;
    }

}


