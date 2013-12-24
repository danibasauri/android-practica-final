/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.journeymanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.journeymanager.R;
import com.example.journeymanager.dataBase.JourneysDbAdapter;


public class JourneyEdit extends Activity {

    private EditText mCityText;
    private Button mDateText;
    private Long mRowId;
    private JourneysDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new JourneysDbAdapter(this);
        mDbHelper.open();

        setContentView(R.layout.note_edit);
        setTitle(R.string.edit_note);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mCityText = (EditText) findViewById(R.id.city);

        mRowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(JourneysDbAdapter.KEY_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null ? extras.getLong(JourneysDbAdapter.KEY_ROWID)
                    : null;
        }
        populateFields();
    }


    private void populateFields() {
        if (mRowId != null) {
            Cursor journey = mDbHelper.fetchJourney(mRowId);
            startManagingCursor(journey);
            mCityText.setText(journey.getString(
                    journey.getColumnIndexOrThrow(JourneysDbAdapter.KEY_CITY)));
            mDateText.setText(journey.getString(
                    journey.getColumnIndexOrThrow(JourneysDbAdapter.KEY_DATE)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(JourneysDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String city = mCityText.getText().toString();
        String date = mDateText.getText().toString();

        if (mRowId == null) {
            long id = mDbHelper.createJourney(city, date);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateJourney(mRowId, city, date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_accept) {
            saveState();
            startActivity(new Intent(this, JourneyList.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
