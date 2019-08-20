package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter_Generation extends RecyclerView.Adapter<RecyclerAdapter_Generation.RecyclerViewHolder> {

    ArrayList<Generation> arrayList = new ArrayList<>();
    Map<String, ArrayList<String>> line_dictionary = new HashMap<String, ArrayList<String>>();

    RecyclerAdapter_Generation(ArrayList<Generation> arrayList,  Map<String, ArrayList<String>> line_dictionary){
        this.arrayList = new ArrayList<>(arrayList);
        this.line_dictionary = line_dictionary;

    }

    Context context;



    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generation_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {



        final Generation generation = arrayList.get(position);

        final Bundle args = new Bundle();
        args.putString("Generation Number", generation.getGeneration_number());
        args.putInt("Generation ID", generation.getId());


        holder.generation_number.setText(generation.getGeneration_number());


        holder.generation_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer buffer = new StringBuffer();

                    try{
                        for(int i =0; i< line_dictionary.get(generation.getGeneration_number()).size();i++){
                        buffer.append( "Line "+line_dictionary.get(generation.getGeneration_number()).get(i)+"\n");
                        }
                    }catch (Exception e){
                        buffer.append("No lines yet.");
                    }


                showMessage("Generation "+generation.getGeneration_number(), buffer.toString());
            }
        });
        if(arrayList.get(position).getGeneration_status()==0){
            holder.generation_cull.setImageResource(R.drawable.ic_remove_black_24dp);
            holder.generation_cull.setClickable(false);
            holder.generation_cull.setEnabled(false);
            holder.generation_status.setText("Inactive");
        }else{
            holder.generation_status.setText("Active");
            holder.generation_cull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentActivity activity = (FragmentActivity)(context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    CullGenerationDialog alertDialog = new CullGenerationDialog();
                    alertDialog.setArguments(args);
                    alertDialog.show(fm, "CullGenerationDialog");
                    notifyDataSetChanged();
                }
            });
        }




    //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView generation_number;
        TextView generation_status;
        ImageButton generation_details;
        ImageButton generation_cull;
        RecyclerViewHolder(View view){
            super(view);
            generation_details = view.findViewById(R.id.generation_details);
            generation_status = view.findViewById(R.id.generation_status);
            generation_cull = view.findViewById(R.id.generation_cull);
            generation_number = (TextView)view.findViewById(R.id.generation_number);

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
