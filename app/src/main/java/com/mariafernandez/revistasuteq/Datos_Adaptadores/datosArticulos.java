package com.mariafernandez.revistasuteq.Datos_Adaptadores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class datosArticulos {
    public String Titulo;
    public String Fecha;
    public String Seccion;
    public String UrlPdf;

    public String getTitulo() {
        return Titulo;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getSeccion() {
        return Seccion;
    }

    public String getUrlPdf() {
        return UrlPdf;
    }

    public datosArticulos(JSONObject a) throws JSONException {
        Titulo =  a.getString("title");
        Fecha = a.getString("date_published");
        Seccion =  a.getString("section_title");
        UrlPdf =  a.getString("pdf");
    }

    public static ArrayList<datosArticulos> JsonObjectsBuild(JSONArray datos) throws JSONException
    {
        ArrayList<datosArticulos> articulos = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            articulos.add(new datosArticulos(datos.getJSONObject(i)));
        }
        return articulos;
    }
}
