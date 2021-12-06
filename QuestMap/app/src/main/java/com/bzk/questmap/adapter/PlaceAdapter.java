package com.bzk.questmap.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bzk.questmap.view.MapsActivity;
import com.bzk.questmap.databinding.RecyclerRowBinding;
import com.bzk.questmap.model.Place;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {
    List<Place> placeList;
    public PlaceAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PlaceHolder(recyclerRowBinding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.PlaceHolder holder, int position) {
        holder.recyclerRowBinding.recyclerViewTextView.setText(placeList.get(position).name);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
            intent.putExtra("place",placeList.get(position));
            intent.putExtra("info","old");
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class PlaceHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;
        public PlaceHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}