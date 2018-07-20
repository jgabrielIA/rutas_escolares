// Proyecto 1: Catalogo Juan Gabriel Beltran Vargas
// Repositorio Git:
// Desarrollo_Universal

package com.example.gabo.rutasescolares;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String[] Lat;
    String[] Lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // entran datos desde la clase Data:
        Bundle parametros = this.getIntent().getExtras();

        if(parametros !=null){

            Lat = parametros.getStringArray("latitud");
            Lng = parametros.getStringArray("longitud");
        }


        //Inicio mapa

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // calle 1, carrera 1 :v

        LatLng Parada = new LatLng(4.585160, -74.074626);

        for(int i = 0; i < Lat.length; i++){

            Parada = new LatLng(Double.parseDouble(Lat[i]),Double.parseDouble(Lng[i]));
            mMap.addMarker(new MarkerOptions()
                    .position(Parada)
                    .title("Parada " + String.valueOf(i + 1))
            );
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Parada, 14));

    }
}
