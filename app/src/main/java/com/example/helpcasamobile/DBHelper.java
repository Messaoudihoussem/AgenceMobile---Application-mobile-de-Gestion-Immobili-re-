package com.example.helpcasamobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Login.db";
    private static final int DATABASE_VERSION = 4;

    // Users table
    private static final String TABLE_NAME_USERS = "users";
    private static final String COLUMN_ID_USERS = "id";
    private static final String COLUMN_USERNAME_USERS = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ADMIN = "adminsection";

    // Clients table
    private static final String TABLE_NAME_CLIENTS = "clients";
    private static final String COLUMN_ID_CLIENTS = "id";
    private static final String COLUMN_CIVILITE = "civilite";
    private static final String COLUMN_USERNAME_CLIENTS = "username";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_PRENOM = "prenom";
    private static final String COLUMN_ADRESSE = "adresse";
    private static final String COLUMN_VILLE = "ville";
    private static final String COLUMN_CODE_POSTAL = "codePostal";
    private static final String COLUMN_TEL_MOBILE = "telMobile";
    private static final String COLUMN_TEL_FIXE = "telFixe";
    private static final String COLUMN_EMAIL_CLIENT = "emailClient";

    DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        String createUserTableQuery = "CREATE TABLE " + TABLE_NAME_USERS +
                " (" +
                COLUMN_ID_USERS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME_USERS + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ADMIN + " TEXT);";
        db.execSQL(createUserTableQuery);

        String createClientTableQuery = "CREATE TABLE " + TABLE_NAME_CLIENTS +
                " (" +
                COLUMN_ID_CLIENTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CIVILITE + " TEXT, " +
                COLUMN_USERNAME_CLIENTS + " TEXT REFERENCES " + TABLE_NAME_USERS + "(" + COLUMN_USERNAME_USERS + "), " +
                COLUMN_NOM + " TEXT, " +
                COLUMN_PRENOM + " TEXT, " +
                COLUMN_ADRESSE + " TEXT, " +
                COLUMN_VILLE + " TEXT, " +
                COLUMN_CODE_POSTAL + " TEXT, " +
                COLUMN_TEL_MOBILE + " TEXT, " +
                COLUMN_TEL_FIXE + " TEXT, " +
                COLUMN_EMAIL_CLIENT + " TEXT);";
        db.execSQL(createClientTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CLIENTS);

        onCreate(db);
    }

    void addClient(String username, String civilite, String nom, String prenom, String adresse,
                   String ville, String codePostal, String telMobile,
                   String telFixe, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (clientExists(username)) {
            Toast.makeText(context, "Client with the same username already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CIVILITE, civilite);
        cv.put(COLUMN_USERNAME_CLIENTS, username);
        cv.put(COLUMN_NOM, nom);
        cv.put(COLUMN_PRENOM, prenom);
        cv.put(COLUMN_ADRESSE, adresse);
        cv.put(COLUMN_VILLE, ville);
        cv.put(COLUMN_CODE_POSTAL, codePostal);
        cv.put(COLUMN_TEL_MOBILE, telMobile);
        cv.put(COLUMN_TEL_FIXE, telFixe);
        cv.put(COLUMN_EMAIL_CLIENT, email);

        long result = db.insert(TABLE_NAME_CLIENTS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add client", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Client added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean clientExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CLIENTS, new String[]{COLUMN_USERNAME_CLIENTS},
                COLUMN_USERNAME_CLIENTS + "=?", new String[]{username},
                null, null, null);

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }




    void adduser(String username,String email, String password, String adminsection){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME_USERS, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_ADMIN, adminsection);
        long result = db.insert(TABLE_NAME_USERS,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    boolean loginChecker(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_USERS +
                " WHERE " + COLUMN_USERNAME_USERS + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    String getAdminStatus(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ADMIN + " FROM " + TABLE_NAME_USERS +
                " WHERE " + COLUMN_USERNAME_USERS + " = ? AND " + COLUMN_PASSWORD + " = ? AND " + COLUMN_ADMIN + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password, "12345678"});

        String adminStatus = "";
        if (cursor.moveToFirst()) {
            adminStatus = cursor.getString(cursor.getColumnIndex(COLUMN_ADMIN));
        }

        cursor.close();
        return adminStatus;
    }



    void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        int result = db.update(TABLE_NAME_USERS, values, COLUMN_USERNAME_USERS + " = ?", new String[]{username});

        if (result > 0) {
            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update password", Toast.LENGTH_SHORT).show();
        }
    }
}

