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

public class adpArticulos extends ArrayAdapter<datosArticulos> {

    ImageView imageView;

    public adpArticulos(Context context, ArrayList<datosArticulos> datos) {
        super(context, R.layout.layout_articulos, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_articulos, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.lblTitulo);
        lblTitulo.setText(getItem(position).getTitulo());

        TextView lblFecha = (TextView)item.findViewById(R.id.lblFecha);
        lblFecha.setText(getItem(position).getFecha());

        TextView lblSeccion = (TextView)item.findViewById(R.id.lblSeccion);
        lblSeccion.setText(getItem(position).getSeccion());

        TextView lblPdf = (TextView)item.findViewById(R.id.lblPdf);
        lblPdf.setText(getItem(position).getUrlPdf());

        ImageView imageView = (ImageView)item.findViewById(R.id.imgPDF);
        Glide.with(this.getContext())
                .load(R.drawable.img_pdf)
                .error(R.drawable.img_pdf)
                .into(imageView);

        return(item);
    }
}

