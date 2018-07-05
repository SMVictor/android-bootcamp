package com.practice.project.android_bootcamp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;

public final class VenuesContract {

    private VenuesContract() {}

    public static class CategoryEntry implements BaseColumns{
        public static String TABLE_NAME = "categories";
        public static String _ID = "id";
        public static String NAME = "name";
    }

    public static class VenuesEntry implements BaseColumns{

        public static String TABLE_NAME = "venues";
        public static String _ID = "id";
        public static String NAME = "name";
        public static String ADDRESS = "address";
        public static String LATITUDE = "latitude";
        public static String LONGITUDE = "longitude";
        public static String CATEGORY_ID = "category_id";
    }

    private static final String SQL_CREATE_TABLE_VENUES =  "CREATE TABLE " + VenuesEntry.TABLE_NAME + " (" +
            VenuesEntry._ID + " TEXT PRIMARY KEY," +
            VenuesEntry.NAME + " TEXT NOT NULL," +
            VenuesEntry.ADDRESS + " TEXT NOT NULL," +
            VenuesEntry.LATITUDE + " REAL NOT NULL," +
            VenuesEntry.LONGITUDE + " REAL NOT NULL," +
            VenuesEntry.CATEGORY_ID + " TEXT NOT NULL)";

    private static final String SQL_CREATE_TABLE_CATEGORIES =  "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
            CategoryEntry._ID + " TEXT PRIMARY KEY," +
            CategoryEntry.NAME + " REAL NOT NULL)";

    private static final String SQL_DELETE_TABLE_VENUES =
            "DROP TABLE IF EXISTS " + VenuesEntry.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_CATEGORIES =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;


    public static class VenuesDbHelper extends SQLiteOpenHelper {

        public VenuesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATA_VERSION);
        }

        public static final String TAG = "DatabaseHelper";
        public static final String DATABASE_NAME = "venues.db";
        public static final int DATA_VERSION = 1;

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CATEGORIES);
            sqLiteDatabase.execSQL(SQL_CREATE_TABLE_VENUES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_TABLE_CATEGORIES);
            sqLiteDatabase.execSQL(SQL_DELETE_TABLE_VENUES);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        }
    }
}
