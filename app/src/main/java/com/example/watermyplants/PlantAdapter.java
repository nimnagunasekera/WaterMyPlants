package com.example.watermyplants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private final Context mContext;
    public ArrayList<Plants> mPlantsList;

    public PlantAdapter(Context context, ArrayList<Plants> plantsList) {
        mContext = context;
        mPlantsList = plantsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plants plant = mPlantsList.get(position);
        holder.txtCardName.setText(plant.getName());
        holder.txtNextReminder.setText(plant.getWatering());
        holder.imageCard.setImageDrawable(plant.getImageDrawable());

        holder.parent.setOnClickListener(v -> {
            Toast.makeText(mContext, plant.getName() + " Selected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mPlantsList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final CardView parent;
        final TextView txtCardName;
        final ImageView imageCard;
        final TextView txtNextReminder;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtCardName = itemView.findViewById(R.id.txtCardName);
            imageCard = itemView.findViewById(R.id.imageCard);
            txtNextReminder = itemView.findViewById(R.id.txtNextReminder);
        }
    }
}
