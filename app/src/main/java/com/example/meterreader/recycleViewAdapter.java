package com.example.meterreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.MyViewHolder> {

    private Context context ;
    private ArrayList cust_id , cust_name , cust_meter ,cust_read ,readDate;

    recycleViewAdapter(Context context ,ArrayList cust_id,ArrayList cust_name ,ArrayList cust_meter,ArrayList cust_read ,ArrayList readDate)
    {
        this.context=context;
        this.cust_id=cust_id;
        this.cust_name=cust_name;
        this.cust_meter=cust_meter;
        this.cust_read=cust_read;
        this.readDate=readDate;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
       View view=   inflater.inflate(R.layout.layout_recycler,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.cust_id.setText(String.valueOf(cust_id.get(position)));
        holder.cust_name.setText(String.valueOf(cust_name.get(position)));
        holder.cust_meter.setText(String.valueOf(cust_meter.get(position)));
        holder.cust_read.setText(String.valueOf(cust_read.get(position)));
        holder.read_date.setText(String.valueOf(readDate.get(position)));
    }

    @Override
    public int getItemCount() {
        return cust_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cust_id , cust_name , cust_meter , cust_read , read_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cust_id=itemView.findViewById(R.id.cust_id_view);
            cust_name=itemView.findViewById(R.id.cust_name_view);
            cust_meter=itemView.findViewById(R.id.cust_meter_view);
            cust_read=itemView.findViewById(R.id.cust_read_view);
            read_date=itemView.findViewById(R.id.read_Date_view);

        }
    }
}
