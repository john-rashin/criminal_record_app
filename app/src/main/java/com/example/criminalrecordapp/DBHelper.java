package com.example.criminalrecordapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "criminal_records.db";

    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String CRIMINAL_TABLE = "criminal_records";
    private static final String CRIME_ID = "id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String DATE = "date";
    private static final String OFFENSE_ID = "offense_id";
    private static final String LOCATION_ID = "location_id";
    private static final String POLICE_ID = "police_id";


    private static final String OFFENSES_TABLE = "offenses";
    private static final String OFFENSES_ID = "id";
    private static final String OFFENSE_TYPE = "offense_type";
    private static final String OFFENSE_DESCRIPTION = "offense_description";

    private static final String LOCATION_TABLE = "locations";
    private static final String LOCATION_CITY = "municipality";
    private static final String LOCATION_STATE = "province";

    private static final String DEPARTMENT_TABLE = "police_departments";
    private static final String STATION_NAME = "station_name";
    private static final String STATION_ADDRESS = "station_address";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT UNIQUE NOT NULL, password TEXT NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE criminal_records (id INTEGER PRIMARY KEY, name TEXT NOT NULL, age INTEGER NOT NULL, date TEXT NOT NULL, offense_id INTEGER NOT NULL, location_id INTEGER NOT NULL, police_department_id INTEGER NOT NULL, FOREIGN KEY (offense_id) REFERENCES offenses(id), FOREIGN KEY (location_id) REFERENCES locations(id), FOREIGN KEY (police_department_id) REFERENCES police_departments(id));";
        db.execSQL(sql);

        sql = "CREATE TABLE offenses (id INTEGER PRIMARY KEY, offense_type TEXT NOT NULL,offense_description TEXT NOT NULL );";
        db.execSQL(sql);

        sql = "CREATE TABLE locations (id INTEGER PRIMARY KEY, municipality TEXT NOT NULL, province TEXT NOT NULL );";
        db.execSQL(sql);

        sql = "CREATE TABLE police_departments (id INTEGER PRIMARY KEY, station_name TEXT NOT NULL, station_address TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS users;";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS criminal_records;";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS offenses;";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS locations;";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS police_departments;";
        db.execSQL(sql);

        onCreate(db);

    }

    //THIS IS LOGIN CREDENTIALS
    public boolean addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", email);
        values.put("password", password);
        long result = db.insert("users", null, values);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkUser(String username) {
        String[] columns = {COLUMN_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?" + " and " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Boolean getUserData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM users WHERE username = ? and password = ?", new String[]{email, password});
        if (res.getCount() > 0)
            return true;
        else
            return false;
    }//end of user function

    public boolean validatePassword(String email, String currentPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM users WHERE username = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            // Get the password from the cursor
            String storedPass = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            // Compare the stored password with the current password provided by the user
            return currentPass.equals(storedPass);
        }
        cursor.close();
        // If cursor is empty return false
        return false;
    }
    public void resetPassword(String email, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update("users", values, "username= ?", new String[]{email});
    }



    public boolean insertCriminalRecord(String name, int age, String date, int offense_id, int location_id, int police_department_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("date", date);
        values.put("offense_id", offense_id);
        values.put("location_id", location_id);
        values.put("police_department_id", police_department_id);
        db.insert("criminal_records", null, values);
        return true;
    }


    public class CriminalRecord {
        int id;
        String name;
        int age;
        String date;
        String offenseType;
        String offenseDescription;
        String municipality;
        String province;
        String stationName;
        String stationAddress;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getDate() {
            return date;
        }

        public String getOffenseType() {
            return offenseType;
        }

        public String getMunicipality() {
            return municipality;
        }

        public String getProvince() {
            return province;
        }

        public String getOffenseDescription() {
            return offenseDescription;
        }

        public String getStationName() {
            return stationName;
        }

        public String getStationAddress() {
            return stationAddress;
        }



    }

    String sql = "SELECT criminal_records.id, criminal_records.name, criminal_records.age, " +
            "criminal_records.date, offenses.offense_type, offenses.offense_description, " +
            "locations.municipality, locations.province, " +
            "police_departments.station_name, police_departments.station_address " +
            "FROM criminal_records JOIN offenses ON criminal_records.offense_id = offenses.id " +
            "JOIN locations ON criminal_records.location_id = locations.id " +
            "JOIN police_departments ON criminal_records.police_department_id = police_departments.id";

    public ArrayList<CriminalRecord> getAllCriminalRecords() {
        ArrayList<CriminalRecord> criminalRecords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            CriminalRecord criminalRecord = new CriminalRecord();
            criminalRecord.id = cursor.getInt(0);
            criminalRecord.name = cursor.getString(1);
            criminalRecord.age = cursor.getInt(2);
            criminalRecord.date = cursor.getString(3);
            criminalRecord.offenseType = cursor.getString(4);
            criminalRecord.offenseDescription = cursor.getString(5);
            criminalRecord.municipality = cursor.getString(6);
            criminalRecord.province = cursor.getString(7);
            criminalRecord.stationName = cursor.getString(8);
            criminalRecord.stationAddress = cursor.getString(9);
            criminalRecords.add(criminalRecord);
            cursor.moveToNext();
        }
        cursor.close();
        return criminalRecords;
    }


    public boolean insertOffense(String offense_type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OFFENSE_TYPE, offense_type);
        values.put(OFFENSE_DESCRIPTION, description);
        db.insert("offenses", null, values);
        return true;
    }

    public boolean insertLocation(String municipality, String province) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("municipality", municipality);
        values.put("province", province);
        db.insert("locations", null, values);
        return true;
    }

    public boolean insertDepartment(String station_name, String station_address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATION_NAME, station_name);
        values.put(STATION_ADDRESS, station_address);
        db.insert("police_departments", null, values);
        return true;
    }

    public boolean saveUpdates(String newName, String newAge, String newDate,
                               String newOffenseType, String newOffenseDescription,
                               String newMunicipality, String newProvince,
                               String newStationName, String newStationAddress,int targetId) {

        // Update fields in criminal_records table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("age", newAge);
        values.put("date", newDate);
        db.update("criminal_records", values, "id = ?", new String[]{String.valueOf(targetId)});

        // Update fields in offenses table
        ContentValues values1 = new ContentValues();
        values1.put("offense_type", newOffenseType);
        values1.put("offense_description", newOffenseDescription);
        db.update("offenses", values1, "id = ?", new String[]{String.valueOf(targetId)});

        // Update fields in locations table
        ContentValues values2 = new ContentValues();
        values2.put("municipality", newMunicipality);
        values2.put("province", newProvince);
        db.update("locations", values2, "id = ?", new String[]{String.valueOf(targetId)});

        // Update fields in police_departments table
        ContentValues values3 = new ContentValues();
        values3.put("station_name", newStationName);
        values3.put("station_address", newStationAddress);
        db.update("police_departments", values3, "id = ?", new String[]{String.valueOf(targetId)});
return true;
    }
    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("criminal_records", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}



