package com.example.journeymanager.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeymanager.R;
import com.example.journeymanager.objects.JourneyConstantList;
import com.example.journeymanager.objects.NetInfo;
import com.example.journeymanager.tasks.ParseWikiArticleTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationMap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        final NetInfo netInfo = new NetInfo(LocationMap.this);
        final JourneyConstantList journeyConstantList = ((JourneyConstantList) this.getApplication());
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
                ciudad.setText(journeyConstantList.selectedJourney.getLocation());
                url.setText(journeyConstantList.selectedJourney.getDate());
                return v;
            }
        });

        mMap.clear();


        List<Marker> mMarkers = new ArrayList<Marker>();

        if (Geocoder.isPresent()) {
            try {
                String location = journeyConstantList.selectedJourney.getLocation();
                Geocoder gc = new Geocoder(this);
                List<Address> addresses = gc.getFromLocationName(location, 3);

                List<LatLng> ll = new ArrayList<LatLng>(addresses.size());
                for (Address a : addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        //ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        mMarkers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title("Barcelona")
                                .snippet("Barcelona1")));
                    }
                }
            } catch (IOException e) {
                // handle the exception
            }
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(getApplicationContext(), LocationInfo.class));

                if (netInfo.isConnected()) {
                    new ParseWikiArticleTask(LocationMap.this).execute(journeyConstantList.selectedJourney.getLocation());
                } else {
                    Toast.makeText(getApplicationContext(), "Comprueba tu conexión a internet", 1000).show();
                }
            }
        });
        //marker.showInfoWindow();
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
