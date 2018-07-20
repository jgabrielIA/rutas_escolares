// Proyecto 1: Catalogo Juan Gabriel Beltran Vargas
// Repositorio Git:
// Desarrollo_Universal

package com.example.gabo.rutasescolares;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Adaptador extends BaseAdapter{

    private static LayoutInflater inflater = null;

    Context contexto;
    String [][] datos;
    Bitmap [] Imagenes;
    String [] Url_Stops;


    public  Adaptador (Context contexto, String [][] datos, String[] Url_Stops, Bitmap[] Imagenes){

        // Referencias - inicializar:
        this.contexto = contexto;
        this.datos = datos;
        this.Url_Stops = Url_Stops;
        this.Imagenes = Imagenes;

        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }


    // Lista rutas
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View vista = inflater.inflate(R.layout.elemento_lista, null);

        TextView identificaion = vista.findViewById(R.id.Lid);
        TextView nombre = vista.findViewById(R.id.Lname);
        TextView descripcion = vista.findViewById(R.id.Ldescrip);

        ImageView imagen = vista.findViewById(R.id.Limagen);

        identificaion.setText("Id: " + datos[i][0]);
        nombre.setText("Nombre: " + datos[i][1]);
        descripcion.setText("Descripción: " + datos[i][2]);

        // imagenes png para cada ruta:
        imagen.setImageBitmap(Imagenes[i]);

        return vista;
    }


    @Override
    public int getCount() {
        return Imagenes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
