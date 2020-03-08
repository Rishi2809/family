package com.rishi.family;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
   private List<Dates> plist;

    // RecyclerView recyclerView;


    public MyListAdapter(List<Dates> plist) {
        this.plist = plist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.content_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Dates Dates = plist.get(position);
        holder.name.setText(Dates.getName());
        holder.date.setText(Dates.getDob());
        holder.age.setText(Dates.getFrequency());
        holder.occassion.setText(Dates.getOccassion());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+Dates.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return plist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,age,occassion;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.age = (TextView) itemView.findViewById(R.id.age);
            this.occassion = (TextView) itemView.findViewById(R.id.occassion);
            this.relativeLayout = (RelativeLayout)itemView.findViewById(R.id.rl1);
        }
    }
}
