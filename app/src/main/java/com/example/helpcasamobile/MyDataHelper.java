package com.example.helpcasamobile;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "HousesDB.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "house";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NBRCHAMBRE = "nbrchambre";
    private static final String COLUMN_VILLE = "ville";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_VENDRE_TYPE = "vendretype";







    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_NBRCHAMBRE + " TEXT, " +
                COLUMN_VILLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_PRICE + " INTEGER, "+
                COLUMN_VENDRE_TYPE + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    void addHouse(String title, String nbrchambre, String ville, String description, int price, byte[] image, String vendreType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_NBRCHAMBRE, nbrchambre);
        cv.put(COLUMN_VILLE, ville);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_VENDRE_TYPE, vendreType);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readAllDatanabeul(){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_VILLE + " = 'nabeul'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readAllDatatunis(){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_VILLE + " = 'tunis'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAlldatas1(){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(" + COLUMN_NBRCHAMBRE + ") = 'S+1' OR UPPER(" + COLUMN_NBRCHAMBRE + ") = 'S1'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void updateData(String row_id, String title, String nbrchambre, String ville, String description, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_NBRCHAMBRE, nbrchambre);
        cv.put(COLUMN_VILLE, ville);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_PRICE, price);


        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}

