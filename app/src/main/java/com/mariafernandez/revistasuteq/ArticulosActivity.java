package com.mariafernandez.revistasuteq;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mariafernandez.revistasuteq.Datos_Adaptadores.adpArticulos;
import com.mariafernandez.revistasuteq.Datos_Adaptadores.datosArticulos;
import com.mariafernandez.revistasuteq.WebService.Asynchtask;
import com.mariafernandez.revistasuteq.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArticulosActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    ListView listView;
    TextView txtRevista;
    ImageView imageView;

    String articlee;

    ArrayList<datosArticulos> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);

        Bundle bundle = this.getIntent().getExtras();
        txtRevista = (TextView)findViewById(R.id.lbltituloRevista);
        txtRevista.setText(bundle.getString("Revista"));

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://revistas.uteq.edu.ec/ws/getarticles.php?" +
                "volumen="+ bundle.getString("Volum") +
                "&num="+ bundle.getString("Num"),
                datos, this, this);
        ws.execute("");

        listView = (ListView)findViewById(R.id.lstarticulos);
    }

    public boolean isDownloadManagerAvailable()
    {
        if (Build.VERSION.SDK_INT >= 29) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("articles");
        for (int i=0; i<jsonArray.length(); i++)
        {
            JSONObject obj = jsonArray.getJSONObject(i);
            datosArticulos articulos = new datosArticulos(obj);
            data = articulos.JsonObjectsBuild(jsonArray);
        }
        adpArticulos adapArticulos = new adpArticulos(this, data);
        listView.setAdapter(adapArticulos);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        articlee = data.get(position).getTitulo();
        String url = data.get(position).getUrlPdf();
        imageView = (ImageView)view.findViewById(R.id.imgPDF);
        imageView.setTag(data.get(position).getUrlPdf());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloadManagerAvailable())
                {
                    String url = data.get(position).getUrlPdf();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setDescription("PDF Paper");
                    request.setTitle(articlee);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, url);

                    DownloadManager manager = (DownloadManager)getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    try {
                        manager.enqueue(request);

                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
}
