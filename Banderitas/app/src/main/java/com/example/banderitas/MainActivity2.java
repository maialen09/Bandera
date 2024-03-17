package com.example.banderitas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import java.io.Serializable;

public class MainActivity2 extends AppCompatActivity {

    private EditText usuarioEditText;
    private EditText contraseñaEditText;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        super.findViewById(R.id.registrar).setOnClickListener(new Listener());

        usuarioEditText = findViewById(R.id.usuario);
        contraseñaEditText = findViewById(R.id.contraseña);

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









    private class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            EditText campo = MainActivity2.this.findViewById(R.id.usuario);
            String usuario = campo.getText().toString();

            EditText campo2 = MainActivity2.this.findViewById(R.id.contraseña);
            String contraseña = campo2.getText().toString();

            // primero comprobamos si el usuario ya existe en la base de datos
            if (com.example.banderitas.MainActivity.getMiActividad().getMiBD().existeUsuario(usuario)) {
                // Si el usuario existe , mostrar mensaje de error
                Toast.makeText(getApplicationContext(), "El usuario "+usuario+ " ya existe en la BD.", Toast.LENGTH_SHORT).show();

            } else {
                // El usuario no existe, se le registra en la base de datos


                BD miBD = com.example.banderitas.MainActivity.getMiActividad().getMiBD();
                miBD.registrarUsuario(usuario, contraseña);


                // se abre la pantalla de jugar/opciones despues de registrar al usuario
                Intent intent = new Intent (MainActivity2.this, MainActivity4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }


        }

    }
}
