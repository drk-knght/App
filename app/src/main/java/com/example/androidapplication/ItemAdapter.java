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
        holder.viewitemname.setText(model.getItemname());
        holder.viewItemQty.setText(String.valueOf(model.getItemqty()));
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
        TextView viewitemname,viewItemQty,viewitemcategory;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            viewitemname=itemView.findViewById(R.id.viewitemname);
            viewItemQty=itemView.findViewById(R.id.viewitemqty_new);
//            viewItemQty=itemView.findViewById(R.id.viewitemqtyname);
            viewitemcategory=itemView.findViewById(R.id.viewitemcategory);
        }
    }

}
