package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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

import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooder_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Egg_Quality extends RecyclerView.Adapter<RecyclerAdapter_Egg_Quality.RecyclerViewHolder> {


    ArrayList<Egg_Quality> arrayListBrooderFeedingRecords = new ArrayList<>();

    RecyclerAdapter_Egg_Quality(ArrayList<Egg_Quality> arrayListBrooderFeedingRecords  ){
        this.arrayListBrooderFeedingRecords = arrayListBrooderFeedingRecords;
        this.context = context;

    }

    Context context;


    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.egg_qual_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);



        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        final Egg_Quality egg_production = arrayListBrooderFeedingRecords.get(position);

        final Bundle args = new Bundle();
        args.putInt("Breeder Inventory ID", egg_production.getEgg_breeder_inv_id());
        args.putInt("Breeder Hatchery ID", egg_production.getId());
        args.putString("Breeder Tag", egg_production.getTag());


        holder.date.setText(egg_production.getDate());
        holder.week.setText(egg_production.getWeek().toString());
/*        holder.weight.setText(egg_production.getWeight().toString());
        holder.color.setText(egg_production.getColor());
        holder.shape.setText(egg_production.getShape());
        holder.length.setText(egg_production.getLength().toString());
        holder.width.setText(egg_production.getWidth().toString());
        holder.albumen_height.setText(egg_production.getAlbument_height().toString());
        holder.albumen_weight.setText(egg_production.getAlbument_weight().toString());
        holder.yolk_weight.setText(egg_production.getYolk_weight().toString());
        holder.yolk_color.setText(egg_production.getYolk_color());
        holder.shell_weight.setText(egg_production.getShell_weight().toString());
        holder.top_shell.setText(egg_production.getShell_thickness_top().toString());
        holder.middle_shell.setText(egg_production.getShell_thickness_middle().toString());
        holder.bottom_shell.setText(egg_production.getShell_thickness_bottom().toString());*/

        holder.view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ViewBreederEggQualRecordDialog alertDialog = new ViewBreederEggQualRecordDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "ViewBreederEggQualRecordDialog");
                notifyDataSetChanged();
            }
        });
        holder.deleted_at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                DeleteEggQuality alertDialog = new DeleteEggQuality();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "DeleteEggQuality");
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListBrooderFeedingRecords.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView date,week, color, weight, shape, length, width, albumen_height, albumen_weight, yolk_weight,yolk_color, shell_weight, top_shell, middle_shell, bottom_shell,remarks;
        ImageButton deleted_at, view_more;



        RecyclerViewHolder(View view){
            super(view);
            date = view.findViewById(R.id.date);
            week = view.findViewById(R.id.week);

/*            weight = view.findViewById(R.id.weight);
            color = view.findViewById(R.id.color);
            shape = view.findViewById(R.id.shape);
            length = view.findViewById(R.id.length);
            width = view.findViewById(R.id.width);
            albumen_height = view.findViewById(R.id.albumen_height);
            albumen_weight = view.findViewById(R.id.albumen_weight);
            yolk_weight = view.findViewById(R.id.yolk_weight);
            yolk_color = view.findViewById(R.id.yolk_color);
            shell_weight = view.findViewById(R.id.shell_weight);
            top_shell = view.findViewById(R.id.top_shell);
            middle_shell = view.findViewById(R.id.middle_shell);
            bottom_shell = view.findViewById(R.id.bottom_shell);*/
            view_more = view.findViewById(R.id.view_more);
            deleted_at = view.findViewById(R.id.delete);



        }


    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void updateArray(ArrayList<Brooder_FeedingRecords> data)
    {
        data.clear();
        data.addAll(data);
        notifyDataSetChanged();
    }


}
