package com.example.androidapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ItemAdapter extends FirebaseRecyclerAdapter<Items,ItemAdapter.ItemHolder> {

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Items> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull Items model) {
        holder.viewitembarcode.setText(model.getItembarcode());
        holder.viewitemname.setText(model.getItemname());
        holder.viewitemprice.setText(model.getItemprice());
        holder.viewitemcategory.setText(model.getItemcategory());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        return new ItemHolder(view);
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView viewitembarcode,viewitemname,viewitemprice,viewitemcategory;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            viewitembarcode=itemView.findViewById(R.id.viewitembarcode);
            viewitemname=itemView.findViewById(R.id.viewitemname);
            viewitemprice=itemView.findViewById(R.id.viewitemprice);
            viewitemcategory=itemView.findViewById(R.id.viewitemcategory);
        }
    }

}
