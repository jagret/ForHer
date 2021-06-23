package com.example.forher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    ArrayList<Problems> list;
    public AdapterClass(ArrayList<Problems> list)
    {
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(list.get(i).getName());
        myViewHolder.place.setText(list.get(i).getPlace());
        myViewHolder.phone.setText(list.get(i).getPhone());
        myViewHolder.date.setText(list.get(i).getDate());
        Picasso.get().load(list.get(i).getImage()).into(myViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,place,date,phone;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            place=itemView.findViewById(R.id.place);
            phone=itemView.findViewById(R.id.phone);
            date=itemView.findViewById(R.id.date);
            image=(ImageView)itemView.findViewById(R.id.imag);
        }
    }
}
