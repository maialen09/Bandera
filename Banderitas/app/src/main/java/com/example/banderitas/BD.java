package com.example.banderitas;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//################################################################################################################




//################################################################################################################
public class BD extends SQLiteOpenHelper {

    private static final String TABLE_USUARIOS = "USUARIOS";
    private static final String COLUMN_USUARIO = "usuario";
    private static final String COLUMN_CONTRASENA = "contraseña";



    public BD(@Nullable Context context, @Nullable String name,
              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE USUARIOS ('usuario' VARCHAR(255) PRIMARY KEY, 'contraseña' VARCHAR(255));");
        db.execSQL("CREATE TABLE PUNTUACIONES('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'usuario' VARCHAR(255), 'puntuacion' INTEGER, FOREIGN KEY ('usuario') REFERENCES USUARIOS('usuario'));");


    }
    public boolean existeUsuario(String palabra) {

        // Mirar si el usuario existe en BD


        // Crear conexión a BD
        SQLiteDatabase miBD = super.getReadableDatabase();

        // Crear array para parametros
        String[] listaPalabra = new String[1];
        listaPalabra[0] = palabra;

        // Crear la consulta SQL y obtener su output
        Cursor c = miBD.rawQuery("SELECT * FROM USUARIOS WHERE usuario = ?", listaPalabra);

        System.out.println("EXECUTED");
        // Pasar el iterador a la primera instancia. Si habia una primera instancia esto nos devulve True
        boolean existe = c.moveToNext();

        // Cerrar las conexiones
        c.close();
        miBD.close();


        return existe;

    }


    public void registrarUsuario(String usuario, String contraseña) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO, usuario);
        values.put(COLUMN_CONTRASENA, contraseña);

        // Inserta la nueva fila en la tabla
        db.insert(TABLE_USUARIOS, null, values);

        // Cierra la conexión de la base de datos
        db.close();
    }

    public boolean coincideContraseña(String usuario, String contraseña){

        SQLiteDatabase db = this.getReadableDatabase();


        String[] columnas = {COLUMN_USUARIO, COLUMN_CONTRASENA};


        String seleccion = COLUMN_USUARIO + " = ? AND " + COLUMN_CONTRASENA + " = ?";
        String[] seleccionArgs = {usuario, contraseña};

        // Realiza la consulta
        Cursor cursor = db.query(TABLE_USUARIOS, columnas, seleccion, seleccionArgs, null, null, null);

        // Comprueba si se encontró alguna fila que coincida
        boolean credencialesCorrectas = cursor.moveToFirst();

        // Cierra el cursor y la conexión de la base de datos
        cursor.close();
        db.close();

        return credencialesCorrectas;


    }

    public void guardarPuntuacion(String usuario, int puntuacion){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("usuario", usuario);
        values.put("puntuacion", puntuacion);

        long newRowId = db.insert("PUNTUACIONES", null, values);



    }



    public int obtenerMejorPuntuacion(String usuario) {

        int mejorPuntuacion = -1;

        SQLiteDatabase db = this.getReadableDatabase();

// Construye la consulta SQL para obtener la máxima puntuación del usuario dado
        String[] projection = {"MAX(puntuacion) AS mejor_puntuacion"};
        String selection = "usuario = ?";
        String[] selectionArgs = {usuario};

// Ejecuta la consulta SQL y procesa el resultado
        Cursor cursor = db.query("PUNTUACIONES", projection, selection, selectionArgs, null, null, null);

// Comprueba si hay algún resultado
        if (cursor != null && cursor.moveToFirst()) {
            // Obtén el valor de la mejor puntuación
            mejorPuntuacion = cursor.getInt(cursor.getColumnIndex("mejor_puntuacion"));
            cursor.close();

        }


        db.close();
        return mejorPuntuacion;




    }

    public List<Puntuacion> obtenerMejoresResultadosPorUsuario() {

        List<Puntuacion> mejoresResultados = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta SQL para obtener el mejor resultado de cada usuario
        String query = "SELECT id, usuario, MAX(puntuacion) AS max_puntuacion " +
                "FROM PUNTUACIONES " +
                "GROUP BY usuario " +
                "ORDER BY max_puntuacion DESC";

        Cursor cursor = db.rawQuery(query, null);

        // Recorrer el cursor y agregar los resultados a la lista
        if (cursor.moveToFirst()) {
            do {
                String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
                int puntuacion = cursor.getInt(cursor.getColumnIndex("max_puntuacion"));

                Puntuacion puntuacionUsuario = new Puntuacion(usuario, puntuacion);
                mejoresResultados.add(puntuacionUsuario);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return mejoresResultados;
    }







    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){


    }






}







