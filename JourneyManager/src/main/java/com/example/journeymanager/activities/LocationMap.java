package com.example.journeymanager.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.journeymanager.R;
import com.example.journeymanager.objects.NetInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        final NetInfo netInfo = new NetInfo(LocationMap.this);

        final GoogleMap mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker arg0) {

                View v = getLayoutInflater().inflate(R.layout.map_pin_info, null);

                TextView ciudad = (TextView) v.findViewById(R.id.firstLine);
                TextView url = (TextView) v.findViewById(R.id.secondLine);

                ciudad.setText("Prueba");
                url.setText("Hola");

                return v;
            }
        });

        mMap.clear();

        MarkerOptions markerOptions = new MarkerOptions();

        /*LatLng latLong = new LatLng(Double.parseDouble(intent.getStringExtra("lat")),
                Double.parseDouble(intent.getStringExtra("lon")));*/
        LatLng latLong = new LatLng(0.5, 0.5);
        markerOptions.position(latLong);
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.server));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 13.0f));
        Marker marker = mMap.addMarker(markerOptions);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(getApplicationContext(), LocationInfo.class));
               /* if (netInfo.isConnected()) {
                    if (!intent.getStringExtra("url").startsWith("https://") && !intent.getStringExtra("url").startsWith("http://"))
                        browserUrl = "http://" + intent.getStringExtra("url");
                    Uri uri = Uri.parse(browserUrl);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    Toast.makeText(getApplicationContext(), "Comprueba tu conexión a internet", 1000).show();
                }*/
            }
        });
        marker.showInfoWindow();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.location_map, container, false);
            return rootView;
        }
    }

}
