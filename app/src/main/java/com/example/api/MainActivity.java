package com.example.api;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.adaptadores.PersonajeAdaptador;
import com.example.api.clases.Personaje;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcv_personaje;

    List<Personaje> listaPersonaje = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv_personaje = findViewById(R.id.rcv_personaje);

        cargarInformacion();
    }

    public void cargarInformacion(){
        String url = "https://rickandmortyapi.com/api/character";
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuesta(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error en el servidor 1", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el servidor 2", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(myRequest);
    }

    public void recibirRespuesta(JSONObject respuesta){
        try {

            for(int i = 0; i <= respuesta.getJSONArray("results").length(); i++) {

                String nombre = respuesta.getJSONArray("results").getJSONObject(i).getString("name");
                String estado = respuesta.getJSONArray("results").getJSONObject(i).getString("status");
                String especie = respuesta.getJSONArray("results").getJSONObject(i).getString("species");
                String imagen = respuesta.getJSONArray("results").getJSONObject(i).getString("image");

                Personaje personaje = new Personaje(nombre, estado, especie, imagen);

                listaPersonaje.add(personaje);

                rcv_personaje.setLayoutManager(new LinearLayoutManager(this));
                rcv_personaje.setAdapter(new PersonajeAdaptador(listaPersonaje));
            }



        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error en el servidor 3"+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}