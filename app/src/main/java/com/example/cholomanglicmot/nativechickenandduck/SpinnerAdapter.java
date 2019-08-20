package com.example.cholomanglicmot.nativechickenandduck;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter {
    Context context;
    String[] names;
    int[] images;

    public SpinnerAdapter (Context context, String[] names, int[] images){
        super(context,R.layout.spinner_item,names);
        this.context = context;
        this.names = names;
        this.images = images;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.spinner_item, null);
            TextView t1 = (TextView)row.findViewById(R.id.textViewChoice);
            ImageView i1 = (ImageView)row.findViewById(R.id.imageViewChoice);

            t1.setText(names[position]);
            i1.setImageResource(images[position]);


        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item, null);
        TextView t1 = (TextView)row.findViewById(R.id.textViewChoice);
        ImageView i1 = (ImageView)row.findViewById(R.id.imageViewChoice);

        t1.setText(names[position]);
        i1.setImageResource(images[position]);


        return row;
    }
}
