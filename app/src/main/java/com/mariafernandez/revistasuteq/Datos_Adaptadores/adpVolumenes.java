package com.mariafernandez.revistasuteq.Datos_Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mariafernandez.revistasuteq.R;

import java.util.ArrayList;


public class adpVolumenes extends ArrayAdapter<datosVolumen> {

    public adpVolumenes(Context context, ArrayList<datosVolumen> datos) {
        super(context, R.layout.layout_item, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_item, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.lblTitulo);
        lblTitulo.setText(getItem(position).getTitulo());

        TextView lblVolNum = (TextView)item.findViewById(R.id.LblVolNum);
        lblVolNum.setText(getItem(position).getVolNUm());

        TextView lblfecha = (TextView)item.findViewById(R.id.lblFecha);
        lblfecha.setText(getItem(position).getFecha());

        ImageView imageView = (ImageView)item.findViewById(R.id.imgPortada);
        Glide.with(this.getContext())
                .load(getItem(position).getURL())
                .error(R.drawable.img_not_found)
                .into(imageView);


        return(item);
    }
}

