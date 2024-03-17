package com.example.banderitas;


public class Puntuacion {

    private String usuario;
    private int puntuacion;

    public Puntuacion(String pUsuario, int pPuntuacion){

        this.usuario = pUsuario;
        this.puntuacion = pPuntuacion;

    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getUsuario() {
        return usuario;
    }
}
