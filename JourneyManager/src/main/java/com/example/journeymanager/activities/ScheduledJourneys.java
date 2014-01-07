package com.example.journeymanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.example.journeymanager.R;
import com.example.journeymanager.dataBase.JourneysDbAdapter;
import com.example.journeymanager.objects.Journey;
import com.example.journeymanager.objects.JourneyConstantList;
import com.example.journeymanager.objects.JourneysListAdapter;

import java.util.HashMap;

public class ScheduledJourneys extends Activity {
    private ListView journeysList;
    JourneyConstantList savedJourneyConstantList;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDIT_ID = Menu.FIRST + 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduled_journeys);
        savedJourneyConstantList = (JourneyConstantList) getApplicationContext();
        fillData();
    }

    private void fillData() {
        HashMap<Integer, Journey> journeys = savedJourneyConstantList.getJourneys();
        journeysList = (ListView) findViewById(R.id.journeysList);
        journeysList.setAdapter(new JourneysListAdapter(this, journeys));
        journeysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(ScheduledJourneys.this, JourneyInfo.class);
                savedJourneyConstantList.saveJourney(savedJourneyConstantList.getItemById(position));
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_viajes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                createNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(1, EDIT_ID, 1, R.string.menu_edit);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case DELETE_ID:
                AdapterContextMenuInfo infoOnDelete = (AdapterContextMenuInfo) item.getMenuInfo();
                savedJourneyConstantList.deleteJourney(infoOnDelete.position);
                fillData();
                return true;
            case EDIT_ID:
                AdapterContextMenuInfo infoOnEdit = (AdapterContextMenuInfo) item.getMenuInfo();
                Intent i = new Intent(this, JourneyEdit.class);
                i.putExtra(JourneysDbAdapter.KEY_ROWID, infoOnEdit.id);
                startActivityForResult(i, ACTIVITY_EDIT);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this, JourneyEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        onListItemClick(l, v, position, id);
        Intent i = new Intent(this, JourneyInfo.class);
        i.putExtra(JourneysDbAdapter.KEY_ROWID, id);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}
