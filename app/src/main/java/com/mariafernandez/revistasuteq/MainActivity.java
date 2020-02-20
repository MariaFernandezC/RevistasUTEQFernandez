package com.mariafernandez.revistasuteq;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mariafernandez.revistasuteq.Datos_Adaptadores.adpVolumenes;
import com.mariafernandez.revistasuteq.Datos_Adaptadores.datosVolumen;
import com.mariafernandez.revistasuteq.WebService.Asynchtask;
import com.mariafernandez.revistasuteq.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    ListView listView;

    ArrayList<datosVolumen> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.lstvolumenes);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://revistas.uteq.edu.ec/ws/getrevistas.php",
                datos, this, this);
        ws.execute("");


    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("issues");
        for (int i=0; i<jsonArray.length(); i++)
        {
            JSONObject obj = jsonArray.getJSONObject(i);
            datosVolumen datosVolumen = new datosVolumen(obj);
            data = datosVolumen.JsonObjectsBuild(jsonArray);
        }
        adpVolumenes adpVolumenes = new adpVolumenes(this, data);
        listView.setAdapter(adpVolumenes);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ArticulosActivity.class);
        Bundle b = new Bundle();
        b.putString("Volum", data.get(position).getVol());
        b.putString("Num", data.get(position).getNum());
        b.putString("Revista",data.get(position).getTitulo());
        intent.putExtras(b);
        startActivity(intent);
    }
}
