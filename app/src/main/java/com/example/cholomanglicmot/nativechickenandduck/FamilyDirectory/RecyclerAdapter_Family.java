package com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Family extends RecyclerView.Adapter<RecyclerAdapter_Family.RecyclerViewHolder> {

    ArrayList<Family> arrayList = new ArrayList<>();

    RecyclerAdapter_Family(ArrayList<Family> arrayList){
        this.arrayList = arrayList;
       // this.line_dictionary = line_dictionary;

    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.family_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {


/* Pen pen = arrayList.get(position);
        holder.pen_number.setText(pen.getPen_number());
        holder.pen_type.setText(pen.getPen_type());
        holder.pen_inventory.setText(pen.getPen_inventory());
        holder.pen_capacity.setText(pen.getPen_capacity());*/

        Family family = arrayList.get(position);
        holder.family_number.setText(family.getFamily_number());
        holder.family_line_number.setText(family.getLine_number());
        holder.family_generation_number.setText(family.getGeneration_number());
       // holder.family_status.setText(family.ge());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView family_number;
        TextView family_line_number;
        TextView family_generation_number;
        TextView family_status;

        RecyclerViewHolder(View view){
            super(view);
            family_number = view.findViewById(R.id.brooder_feeding_offered);
            family_line_number = view.findViewById(R.id.family_line_number);
            family_generation_number = view.findViewById(R.id.family_generation_number);
            family_status =  (TextView)view.findViewById(R.id.family_status);

        }


    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
