package com.example.banderitas;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MainActivity7 extends AppCompatActivity  {



    private String nombreBandera = "";

    private int puntuacion = 0; // puntuacion inicial del usuario

    private int vidas = 3; // vidas iniciales del usuario

    private int i = 0; // indice que se va a usar para recorrer todas las banderas

    private HashMap<String, byte []> lasBanderas;

    private ArrayList<String> nombresBanderas;

    private TextView puntu; // textview donde se actualiza la puntuacion del usuario
    private TextView vid; // textview donde se actualizan las vidas que le quedan al usuario

    private String usuario;



    private int progresoActual; // variable para ir actualizando la barra de progreso
    private ProgressBar progressBar;





    public MainActivity7(){
        // Inicializamos las listas que vamos a usar para recorrer las banderas

        Bandera b2 = new Bandera();
        this.lasBanderas = b2.getBanderas();

        ArrayList<String> nombresBanderas = new ArrayList<>(lasBanderas.keySet());

        // Hacemos que sean aleatorias para que cada vez que juege aparezcan diferentes y no siempre en el mismo orden
        Collections.shuffle(nombresBanderas);

        this.nombresBanderas = nombresBanderas;



    }






    @Override
    public void onCreate(Bundle savedInstanceState){



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        if (savedInstanceState != null) {
            puntuacion = savedInstanceState.getInt("puntuacion", 0);
            vidas = savedInstanceState.getInt("vidas", 3);
            i = savedInstanceState.getInt("indice", 0);
            nombreBandera = savedInstanceState.getString("nombreBandera", "");
        }

        Intent intent = getIntent();

        usuario = intent.getStringExtra("usuario");



        super.findViewById(R.id.botonAcertar).setOnClickListener(new MainActivity7.Listener());

        puntu = findViewById(R.id.puntuacion);
        vid = findViewById(R.id.vidas);

        super.findViewById(R.id.botonSaltar).setOnClickListener(new MainActivity7.Listener2());


        puntu.setText("La puntuacion actual es: " + puntuacion);



        vid.setText("Las vidas restantes son: " + vidas);

        // Llamamos al metodo para crear una imagen
        this.crearImagen(nombresBanderas , lasBanderas);
        // Creamos la barra de progreso a raiz de la puntuacion
        progressBar = findViewById(R.id.progressBar);
        progresoActual = (puntuacion * 100) / nombresBanderas.size();
        progressBar.setProgress(progresoActual);

        //Creamos la ActionBar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("puntuacion", puntuacion);
        outState.putInt("vidas", vidas);
        outState.putInt("indice", i);
        outState.putString("nombreBandera", nombreBandera);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Hacemos que si hace click en el icono de ayuda se abra un dialogo
        int id = item.getItemId();
        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Como se juega?");
        builder.setMessage("Al principio comienzas con puntuacion 0 y 3 vidas. A medida que vayas acertando banderas la puntuacion irá aumentando. A la hora de insertar el nombre, hazlo en minusculas y sin tildes.");
        builder.setPositiveButton("Aceptar", null);
        // Si hace click en más información se abrirá una página web donde estan todas las banderas con informacion al respecto
        builder.setNegativeButton("Más información", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Manejar el clic en el botón "Más información"
                abrirPaginaWeb();
            }
        });
        builder.show();
    }
    private void abrirPaginaWeb() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twinkl.es/teaching-wiki/banderas-del-del-mundo"));
        startActivity(browserIntent);
    }




    private class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // HA PULSADO QUE QUIERE ACERTAR SI LA RESPUESTA ES CORRECTA

            //Llamamos al metodo comprobar para saber si ha acertado o no

            boolean acertado =  comprobar();

            if (acertado){

                //Actualizamos : la puntuacion y la barra de progreso

                puntuacion = puntuacion + 1;

                puntu.setText("La puntuacion actual es: " + puntuacion);
                progresoActual = (puntuacion * 100) / nombresBanderas.size();
                progressBar.setProgress(progresoActual);

                // Si hemos llegado al final de todas las banderas, significa que hemos ganado
                if (puntuacion == nombresBanderas.size()){

                    // Guardamos en la base de datos la puntuacion
                    MainActivity.getMiActividad().getMiBD().guardarPuntuacion(usuario,puntuacion);
                    // Llamamos a una nueva ventana que mostrará un mensaje indicando que hemos ganado
                    Intent intent = new Intent (MainActivity7.this, MainActivity10.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }

                // Como ha acertado , necesitamos crear una nueva imagen que el usuario tenga que acertar
                ConstraintLayout layout = findViewById(R.id.layout7);
                ImageView imageView = layout.findViewById(R.id.mi_imagen_id);
                layout.removeView(imageView);
                crearImagen(nombresBanderas, lasBanderas);

            }

            else{
                // Si el usuario no ha acertado:
                Toast.makeText(getApplicationContext(), "El nombre de la bandera introducido es incorrecto.", Toast.LENGTH_SHORT).show();

                vidas = vidas - 1;

                vid.setText("Las vidas restantes son: " + vidas);

                if (vidas == 0) {
                    // Si el usuario no ha acertado:

                    // Guardamos la puntuacion que ha obtenido en la bd
                    MainActivity.getMiActividad().getMiBD().guardarPuntuacion(usuario, puntuacion);
                    // Creamos la siguiente pantalla
                    Intent intent = new Intent(MainActivity7.this, MainActivity8.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("puntuaciones", puntuacion);
                    startActivity(intent);
                    finish();

                }

            }



        }
    }

    private class Listener2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // HA CLICKADO QUE QUIERE SALTARSE ESA BANDERA
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("HAS PULSADO SALTAR ESTA BANDERA");
            builder.setMessage("Estas seguro de que quieres saltarte esta bandera? Si lo haces perderas una vida");

            // Si hace click en que se la quiere saltar, entonces, se perderá una vida y se creará una nueva bandera
            builder.setPositiveButton("SALTAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    vidas = vidas - 1;

                    vid.setText("Las vidas restantes son: " + vidas);
                    ConstraintLayout layout = findViewById(R.id.layout7);
                    ImageView imageView = layout.findViewById(R.id.mi_imagen_id);
                    layout.removeView(imageView);
                    crearImagen(nombresBanderas, lasBanderas);

                    if (vidas == 0) {

                        MainActivity.getMiActividad().getMiBD().guardarPuntuacion(usuario, puntuacion);
                        Intent intent = new Intent(MainActivity7.this, MainActivity8.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("puntuaciones", puntuacion);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            // Si hace click en volver , no pasará nada, se cerrará el dialogo
            builder.setNegativeButton("VOLVER", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();


                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        }
    }


    public  boolean comprobar(){
        //Obtenemos el nombre de bandera que el usuario ha introducido
        EditText campo = MainActivity7.this.findViewById(R.id.nombreBandera);
        String nombreBanderaUsuario = campo.getText().toString();

        if (this.nombreBandera.equals(nombreBanderaUsuario) ){

            campo.setText("");
            return true;


        }

        else{

            return false;
        }


    }


    public void crearImagen(ArrayList<String> nombresBanderas , HashMap<String, byte []> lasBanderas) {


        if (i >= nombresBanderas.size()) {
            i = 0; // Reinicia i si ha alcanzado el tamaño máximo de nombresBanderas
        }
        String nom = nombresBanderas.get(i);

        i = i +1;

        this.nombreBandera = nom;

        byte [] img = lasBanderas.get(nom);
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        mostrarImagen(bitmap);





    }

    private void mostrarImagen(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        imageView.setId(R.id.mi_imagen_id);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        );
        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;

        // Aplica los parámetros de diseño al ImageView
        imageView.setLayoutParams(params);

        // Cambia la escala de la imagen para que ocupe todo el espacio disponible
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        ConstraintLayout layout = findViewById(R.id.layout7);
        layout.addView(imageView);
    }


}


