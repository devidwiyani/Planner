package com.example.planner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    ArrayList<DailyPlaner> dailyArrayList;
    private Context context;

    //konstruktor
    RecyclerViewAdapter(ArrayList<DailyPlaner> dailyPlanerArrayList, Context context){
        this.dailyArrayList = dailyPlanerArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //mengambil data assignmentArrayList berdsarkan posisi
        DailyPlaner list = dailyArrayList.get(position);
        //setText sesuai data pada list
        holder.dailyPlan.setText(list.getTambahdailyplaner());
        holder.startTime.setText(list.getTambahstarttime());
        holder.endTime.setText(list.getTambahendtime());


//        ke update
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, CreateDailyActivity.class);

                i.putExtra("id", String.valueOf(list.getId()));
                i.putExtra("dailyPlan", list.getTambahdailyplaner());
                i.putExtra("startTime", list.getTambahstarttime());
                i.putExtra("endTime", list.getTambahendtime());

                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dailyArrayList.size();
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView dailyPlan, startTime, endTime;

         ViewHolder(@NonNull View itemView) {

             //menginisialisasi view untuk diguanakn pada recycler view
            super(itemView);
            dailyPlan = itemView.findViewById(R.id.daily_plan_name);
            startTime = itemView.findViewById(R.id.daily_plan_time);
            endTime = itemView.findViewById(R.id.daily_end_time);

        }


    }


}
