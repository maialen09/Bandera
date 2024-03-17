package com.example.banderitas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PuntuacionAdapter extends RecyclerView.Adapter<PuntuacionAdapter.PuntuacionViewHolder> {

    private List<Puntuacion> puntuaciones;

    public PuntuacionAdapter(List<Puntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    @NonNull
    @Override
    public PuntuacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_puntuacion, parent, false);
        return new PuntuacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PuntuacionViewHolder holder, int position) {
        Puntuacion puntuacion = puntuaciones.get(position);
        holder.textViewUsuario.setText(puntuacion.getUsuario());
        holder.textViewPuntuacion.setText(String.valueOf(puntuacion.getPuntuacion()));
    }

    @Override
    public int getItemCount() {
        return puntuaciones.size();
    }

    static class PuntuacionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsuario;
        TextView textViewPuntuacion;
        CardView cardView;

        PuntuacionViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textViewUsuario = itemView.findViewById(R.id.textViewUsuario);
            textViewPuntuacion = itemView.findViewById(R.id.textViewPuntuacion);
        }
    }
}
