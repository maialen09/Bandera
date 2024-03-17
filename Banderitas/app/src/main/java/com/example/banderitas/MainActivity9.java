package com.example.banderitas;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class MainActivity9 extends AppCompatActivity {

    // Este recyclerview + cardview , va a mostrar las mejores puntuaciones de cada usuario, ordenado de mejor a peor puntuacion
    private RecyclerView recyclerView2;

    @Override

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_main9);

        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        // Llamamos al metodo de la bd que nos va a devolver las puntuaciones y usuarios
        List<Puntuacion> puntuaciones = MainActivity.getMiActividad().getMiBD().obtenerMejoresResultadosPorUsuario();

        // Creamos el adaptador para este recyclerview y lo llamamos
        PuntuacionAdapter adapter = new PuntuacionAdapter(puntuaciones);
        recyclerView2.setAdapter(adapter);

    }






}
