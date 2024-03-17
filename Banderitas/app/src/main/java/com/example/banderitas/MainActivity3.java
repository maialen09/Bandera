package com.example.banderitas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

public class MainActivity3 extends AppCompatActivity  {

    private EditText usuarioEditText;
    private EditText contraseñaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        super.findViewById(R.id.iniciarSesion).setOnClickListener(new MainActivity3.Listener());

        usuarioEditText = findViewById(R.id.usuario2);
        contraseñaEditText = findViewById(R.id.contraseña2);

        // Restaurar el contenido de los campos de texto si hay un estado guardado
        if (savedInstanceState != null) {
            String usuarioGuardado = savedInstanceState.getString("usuario");
            String contraseñaGuardada = savedInstanceState.getString("contraseña");
            usuarioEditText.setText(usuarioGuardado);
            contraseñaEditText.setText(contraseñaGuardada);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar el contenido de los campos de texto
        outState.putString("usuario", usuarioEditText.getText().toString());
        outState.putString("contraseña", contraseñaEditText.getText().toString());
    }







    private class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            EditText campo = MainActivity3.this.findViewById(R.id.usuario2);
            String usuario = campo.getText().toString();

            EditText campo2 = MainActivity3.this.findViewById(R.id.contraseña2);
            String contraseña = campo2.getText().toString();


            // primero comprobamos si el usuario existe en la base de datos
            if (com.example.banderitas.MainActivity.getMiActividad().getMiBD().existeUsuario(usuario) == false) {
                // si el usuario no existe que salga un pop que diga que no exista un usuario con ese nombre
                Toast.makeText(getApplicationContext(), "El usuario "+usuario+ " no existe en la BD.", Toast.LENGTH_SHORT).show();

            } else {
                // si el usuario existe hay que comprobar que la contraseña coincida con el usuario dado

                if (com.example.banderitas.MainActivity.getMiActividad().getMiBD().coincideContraseña(usuario, contraseña)){

                    // si coinciden , se abre la ventana de jugar/opciones
                    Intent intent = new Intent (MainActivity3.this, MainActivity4.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }

                else{

                    // si la contraseña no es correcta, sale un pop que avise de que no es correcta
                    Toast.makeText(getApplicationContext(), "La contraseña es incorrecta.", Toast.LENGTH_SHORT).show();


                }

            }




        }

    }



}








