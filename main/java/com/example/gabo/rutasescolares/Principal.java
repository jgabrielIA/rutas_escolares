// Proyecto 1: Catalogo Juan Gabriel Beltran Vargas
// Repositorio Git:
// Desarrollo_Universal

package com.example.gabo.rutasescolares;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.InputStream;
import java.util.ArrayList;


public class Principal extends AppCompatActivity {

    ListView Lista_Rutas;
    ArrayList<String> Datos;
    int Cantidad_Rutas;
    int Info_ruta;
    int carga_img = 0;
    String [][] Info;
    String [] DatosStops;
    String [] DatosImg;
    Bitmap [] DataImg;
    ProgressBar Bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // elementos interfaz
        Lista_Rutas = findViewById(R.id.Lista);
        Bar = findViewById(R.id.Bar3);


        // entran datos desde la clase Data:
        Bundle parametros = this.getIntent().getExtras();

        if(parametros !=null){

            Datos = parametros.getStringArrayList("Informacion");
            Cantidad_Rutas = parametros.getInt("CantidadRutas");
            Info_ruta = parametros.getInt("InfoRuta");

            Info = new String[Info_ruta][Cantidad_Rutas];
            DatosStops = new String[Cantidad_Rutas];
            DatosImg = new String[Cantidad_Rutas];
            DataImg = new Bitmap[Cantidad_Rutas];

            //  adecuacion de informacion
            for (int i = 0; i < Cantidad_Rutas ; i++) {

                for (int j = 0; j < Info_ruta - 2 ; j++) {

                    Info [i][j] = Datos.get(0);
                    Datos.remove(0);
                }

                DatosStops [i] = Datos.get(0);
                Datos.remove(0);
                DatosImg [i] = Datos.get(0);
                Datos.remove(0);
            }


            // proceso para descargar imagenes de las rutas, depende de la clase ImgTask
            for (int i = 0; i < Cantidad_Rutas ; i++) {

                // las imagenes primero se cargan y luego se muestran en la lista
                ImgTask imagenes = new ImgTask(i, this.getApplicationContext());
                imagenes.execute(DatosImg[i]);
            }
        }
    }


    // cuando se ejecuta orden: regresar
    @Override
    public void onBackPressed() {

        // Menu: ¿Desea salir de la aplicación?

        // constructor del cuadro de dialogo
        AlertDialog.Builder cuadro = new AlertDialog.Builder(this);

        // configuracion de caracteristicas del cuadro de dialogo
        cuadro.setMessage("¿Desea salir de la aplicación?");
        cuadro.setTitle("Rutas Escolares");
        cuadro.setIcon(R.drawable.school_bus);

        // boton si
        cuadro.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // termina la app
                finish();
            }
        });

        // boton no
        cuadro.setNegativeButton("Nooo!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // cancela el cuadro de dialogo
                dialog.cancel();
            }
        });

        AlertDialog dialog = cuadro.create();
        dialog.show();
    }


    // Clase ImgTask, permite descargar las imagebes de las rutas;
    private class ImgTask extends AsyncTask<String, Void, Bitmap> {

        int img;
        Context contexto;

        public ImgTask(int img, Context contexto) {
            this.img = img;
            this.contexto = contexto;
        }

        // bitmap:
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        // al terminar cada uno de los AsyncTask se comprueba el total y si toda la informacion se guardo correctamente:

        // onPostExecute regresa al hilo principal
        protected void onPostExecute(Bitmap result) {

            DataImg[img] = result;
            carga_img = img;
            if((Info != null)&&(DatosImg != null)&&(DataImg != null)&&(carga_img == Cantidad_Rutas - 1)){


                // si esta bien, procede al constructor de lista y muestra informacion en pantalla
                Lista_Rutas.setAdapter(new Adaptador(contexto, Info, DatosStops, DataImg));


                // desaparece progresbar
                Bar.setVisibility(View.INVISIBLE);

                //  click items de lista para desplegar informacion en mapa
                Lista_Rutas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        // Objeto clase Data:
                        String Caso = "Paradas_Ruta";
                        Data dato_url = new Data(DatosStops[i], Caso, contexto);
                        dato_url.ejecutar();
                    }
                });
            }
        }
    }
}
