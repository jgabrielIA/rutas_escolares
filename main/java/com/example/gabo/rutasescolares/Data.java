// Proyecto 1: Catalogo Juan Gabriel Beltran Vargas
// Repositorio Git:
// Desarrollo_Universal

package com.example.gabo.rutasescolares;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class Data {

    //1. Informacion rutas

    private String url_api;
    private String caso;
    private Context contexto;


    public Data(String url_api, String caso, Context contexto){
        this.url_api = url_api;
        this.caso = caso;
        this.contexto = contexto;
    }


    // Ejecutar sub proceso
    public void ejecutar() {
        new DescargarData().execute(url_api);
    }


    // Sub proceso:
    class DescargarData extends AsyncTask<String, Void, String> {


        // Proceso para descargar informacion:
        @Override
        protected String doInBackground(String... strings) {
            String link = strings[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();   // Resultado del sub proceso (String s)

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        // Despues de ejecutar
        @Override
        protected void onPostExecute(String s) {

            try {
                AnalisisJSon(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // Analisis de informacion segun sea el caso (String s):
    private void AnalisisJSon(String data) throws JSONException {

        if (data == null){ return; }

        else{

            int medida_fil = 0;
            int medida_col = 0;
            int num_paradas = 0;

            JSONObject jsonData = new JSONObject(data);

            if (caso == "lista_rutas"){

                // se extrae informacion correspondiente a la etiqueta school_buses
                JSONArray jsonRutas = jsonData.getJSONArray("school_buses");   // longitud de 4

                // # numero de elementos
                medida_col = jsonRutas.length();

                for (int i = 0; i < medida_col; i++) {
                    JSONObject jsonRuta = jsonRutas.getJSONObject(i);
                    // # caracteristicas o informacion por ruta:
                    if (jsonRuta.length() > medida_fil){ medida_fil = jsonRuta.length(); }   // longitud 5
                }

                // adecuacion de informacion para ir a la otra actividad:
                String[] datos  = new String[medida_fil];

                ArrayList<String> Datos = new ArrayList<>();

                Intent Aprincipal = new Intent(contexto, Principal.class);

                for (int i = 0; i < medida_col; i++) {

                    JSONObject jsonRuta = jsonRutas.getJSONObject(i);

                    datos[0] = jsonRuta.getString("id");
                    datos[1] = jsonRuta.getString("name");
                    datos[2] = jsonRuta.getString("description");
                    datos[3] = jsonRuta.getString("stops_url");
                    datos[4] = jsonRuta.getString("img_url");

                    Collection envio = Arrays.asList(datos);

                    Datos.addAll(envio);
                }

                // con los datos en extras se envia informacion
                Aprincipal.putStringArrayListExtra("Informacion", Datos);
                Aprincipal.putExtra("CantidadRutas", medida_col);
                Aprincipal.putExtra("InfoRuta", medida_fil);

                //bandera limpia/cierra hilo, para el inicio de la actividad principal
                Aprincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                // inicio de actividad donde se ve lista de rutas.
                contexto.startActivity(Aprincipal);

            }
            else if (caso == "Paradas_Ruta"){

                //Traer info de las paradas segun la ruta:

                JSONArray jsonParadas = jsonData.getJSONArray("stops");
                num_paradas = jsonParadas.length();

                String[] Lat  = new String[num_paradas];
                String[] Lng  = new String[num_paradas];

                for (int i = 0; i<num_paradas; i++){

                    JSONObject coord = jsonParadas.getJSONObject(i);
                    Lat[i] = coord.getString("lat");
                    Lng[i] = coord.getString("lng");
                }

                // inicia actividad mapa
                Intent MapaGo = new Intent(contexto, MapsActivity.class);

                // se envia informacion de latitud y longitud
                MapaGo.putExtra("latitud", Lat);
                MapaGo.putExtra("longitud", Lng);

                //  inicia actividad mapa
                contexto.startActivity(MapaGo);
            }
        }
    }
}
