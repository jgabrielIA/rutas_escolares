// Proyecto 1: Catalogo Juan Gabriel Beltran Vargas
// Repositorio Git:
// Desarrollo_Universal

package com.example.gabo.rutasescolares;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CargaInfo extends AppCompatActivity {

    // Url principal:
    String URL_RUTAS = "https://api.myjson.com/bins/10yg1t";
    String CASO = "lista_rutas";

    // animacion logo:
    ImageView ivEjeY;
    ObjectAnimator animatorY;

    long animationDuration = 1000;

    AnimatorSet animatorSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_info);

        // animacion
        animatorSet = new AnimatorSet();

        ivEjeY = findViewById(R.id.imageView);

        animacion();


        // Objeto clase Data:
        Data dato_url = new Data(URL_RUTAS, CASO, this.getApplicationContext());
        dato_url.ejecutar();

    }

    // animacion rotacion logo:
    private void animacion(){

        animatorY = ObjectAnimator.ofFloat(ivEjeY, "rotation",180f, 360f);
        animatorY.setDuration(animationDuration);
        AnimatorSet animatorSetY = new AnimatorSet();
        animatorSetY.playTogether(animatorY);
        animatorSetY.start();
    }
}
