package com.example.banderitas;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout;

// Clase que hace que se vea la recycler view de todas las banderas con sus nombres
public class MainActivity6 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;

    //private List<String> nombres = new ArrayList<>();

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int spacingPixels = getResources().getDimensionPixelSize(R.dimen.spacing_between_images); // creamos espacio para que no se vea pegado a los margenes
        recyclerView.addItemDecoration(new ItemDecorationStyle(this, spacingPixels)); // enviamos esta informacion a la clase que le da estilo a la recyclerview
        //List<Bitmap> images = obtenerListaDeBitmaps(); // llamamos al metodo que nos va a devolver los Bitmap para poder crear las imagenes
        Pair<List<Bitmap>, List<String>> bitmapNombresPair = obtenerListaDeBitmaps();
        List<Bitmap> images = bitmapNombresPair.first;
        List<String> nombres = bitmapNombresPair.second;

        adapter = new ImageAdapter(images, nombres); // enviamos los nombres y las imagenes al recyclerview
        recyclerView.setAdapter(adapter);
    }



    private Pair<List<Bitmap>, List<String>> obtenerListaDeBitmaps(){
        List<Bitmap> listaBitmaps = new ArrayList<>();
        List<String> listaNombres = new ArrayList<>();
        Bandera b = new Bandera();
        HashMap<String, byte []> banderitas = b.getBanderas(); // obtenemos a partir del hashmap donde estan los bytes de las imagenes y los nombres , el arraylist de los bytes
        // Por cada entrada de este array almacenamos su nombre y creamos su Bitmap
        for (Map.Entry<String, byte[]> entry : banderitas.entrySet()) {
            String nombreImagen = entry.getKey();
            listaNombres.add(nombreImagen);
            byte[] bytesImagen = entry.getValue();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImagen, 0, bytesImagen.length);
            listaBitmaps.add(bitmap);
        }


        return new Pair<>(listaBitmaps, listaNombres);

    }



}
