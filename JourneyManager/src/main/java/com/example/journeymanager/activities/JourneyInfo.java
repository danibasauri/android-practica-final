package com.example.journeymanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.journeymanager.R;
import com.example.journeymanager.objects.JourneyConstantList;

public class JourneyInfo extends Activity {
    TextView location, date, travelmates;
    EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_info);
        final JourneyConstantList journeyConstantList = ((JourneyConstantList) this.getApplication());
        location = (TextView) findViewById(R.id.location);
        location.setText(journeyConstantList.selectedJourney.getLocation());
        date = (TextView) findViewById(R.id.date);
        date.setText(journeyConstantList.selectedJourney.getDate());
        travelmates = (TextView) findViewById(R.id.travelmates);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.city_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            return true;
        } else if (id == R.id.action_map) {
            Intent i = new Intent(this, LocationMap.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
