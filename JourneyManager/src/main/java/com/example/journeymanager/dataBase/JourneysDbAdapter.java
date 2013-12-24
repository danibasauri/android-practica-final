package com.example.journeymanager.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

public class JourneysDbAdapter {
    public static final String KEY_CITY = "city";
    public static final String KEY_DATE = "journey_date";
    //public static final String KEY_COMPANIONS = "companions";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "JourneysDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_CREATE =
            "create table journeys (_id integer primary key autoincrement, "
                    + "city text not null, journey_date date);";
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "journeys";
    private static final int DATABASE_VERSION = 3;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public JourneysDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public JourneysDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createJourney(String city, String date) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CITY, city);
        initialValues.put(KEY_DATE, Calendar.getInstance().getTimeInMillis());
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteJourney(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllJourneys() {
        return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_CITY,
                KEY_DATE}, null, null, null, null, null);
    }

    public Cursor fetchJourney(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,
                        KEY_CITY, KEY_DATE}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateJourney(long rowId, String city, String date) {
        ContentValues args = new ContentValues();
        args.put(KEY_CITY, city);
        args.put(KEY_DATE, date);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
