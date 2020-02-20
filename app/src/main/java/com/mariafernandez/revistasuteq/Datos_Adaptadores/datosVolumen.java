package com.mariafernandez.revistasuteq.Datos_Adaptadores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class datosVolumen {
    private String titulo;
    private String VolNUm;
    private String Vol;
    private String Num;
    private String url;
    private String fecha;


    public datosVolumen(JSONObject a) throws JSONException {
        titulo =  a.getString("title");
        VolNUm =  "Vol: "+ a.getString("volume")
           + " N°: " + a.getString("number");
        url =  a.getString("portada");
        fecha =  a.getString("date_published");
        Vol =  a.getString("volume");
        Num = a.getString("number");
    }

    public String getVol() {
        return Vol;
    }

    public String getNum() {
        return Num;
    }

    public String getVolNUm() {
        return VolNUm;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getURL(){
        return url;
    }

    public static ArrayList<datosVolumen> JsonObjectsBuild(JSONArray datos) throws JSONException
    {
        ArrayList<datosVolumen> volumenes = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            volumenes.add(new datosVolumen(datos.getJSONObject(i)));
        }
        return volumenes;
    }
}
