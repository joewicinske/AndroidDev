package com.example.homework3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


/**
Does all the dB transactions 
*/
public class DBHelper extends SQLiteOpenHelper {
    //breakfast, lunch, dinner, fasting, entry date, notes
    public static final String DATABASE_NAME = "GlucoseDb.db";
    public static final String GLUCOSE_COLUMN_BREAKFAST = "breakfast";
    public static final String GLUCOSE_COLUMN_LUNCH = "lunch";
    public static final String GLUCOSE_COLUMN_DINNER = "dinner";
    public static final String GLUCOSE_COLUMN_FASTING = "fasting";
    public static final String GLUCOSE_COLUMN_ENTRY_DATE = "entryDate";
    public static final String GLUCOSE_COLUMN_ID = "id";
    public static final String GLUCOSE_COLUMN_NOTES = "notes";
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table glucose (id integer primary key, breakfast integer, lunch integer, dinner integer, fasting integer, entryDate text, notes text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS glucose");
        onCreate(db);
    }

    public void insertGlucose(GlucoseData glucoseData){

        ContentValues contentValues = new ContentValues();
        contentValues.put(GLUCOSE_COLUMN_BREAKFAST, glucoseData.getBreakfast());
        contentValues.put(GLUCOSE_COLUMN_LUNCH, glucoseData.getLunch());
        contentValues.put(GLUCOSE_COLUMN_NOTES, glucoseData.getNotes());
        contentValues.put(GLUCOSE_COLUMN_DINNER, glucoseData.getDinner());
        contentValues.put(GLUCOSE_COLUMN_FASTING, glucoseData.getFasting());
        contentValues.put(GLUCOSE_COLUMN_ENTRY_DATE, glucoseData.getEntryDate());
        SQLiteDatabase dB = this.getWritableDatabase();
        dB.insert("glucose", null, contentValues);
        dB.close();
    }

    public int getNumRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int rows  =(int) DatabaseUtils.queryNumEntries(db, "glucose");
        return rows;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM glucose");
        //this.getthis.getWritableDatabase();().endTransaction();
        //this.getWritableDatabase();.close();
    }

    public void deleteGlucose(int id ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("glucose", "id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<GlucoseData> getAllData(){
        ArrayList<GlucoseData> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from glucose order by id desc limit 7", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            GlucoseData glucoseData = new GlucoseData();
            glucoseData.setLunch(c.getInt(c.getColumnIndex(GLUCOSE_COLUMN_LUNCH)));
            glucoseData.setBreakfast(c.getInt(c.getColumnIndex(GLUCOSE_COLUMN_BREAKFAST)));
            glucoseData.setDinner(c.getInt(c.getColumnIndex(GLUCOSE_COLUMN_DINNER)));
            glucoseData.setId(c.getInt(c.getColumnIndex(GLUCOSE_COLUMN_ID)));
            glucoseData.setFasting(c.getInt(c.getColumnIndex(GLUCOSE_COLUMN_FASTING)));
            glucoseData.setNotes(c.getString(c.getColumnIndex(GLUCOSE_COLUMN_NOTES)));
            glucoseData.setEntryDate(c.getString(c.getColumnIndex(GLUCOSE_COLUMN_ENTRY_DATE)));
            data.add(glucoseData);
            c.moveToNext();
        }
        return data;
    }


}
