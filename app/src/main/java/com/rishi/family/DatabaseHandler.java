//package com.rishi.family;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseHandler extends SQLiteOpenHelper {
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "DatesManager";
//    private static final String TABLE_DATES = "dates";
//    private static final String KEY_ID = "id";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_DOB = "date_of_birth";
//    private static final String KEY_OCCASSION = "occassion";
//
//
//    public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        //3rd argument to be passed is CursorFactory instance
//    }
//
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DATES + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
//                + KEY_DOB + " TEXT," + KEY_OCCASSION + "TEXT" + ")";
//        db.execSQL(CREATE_CONTACTS_TABLE);
//    }
//
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATES);
//
//        // Create tables again
//        onCreate(db);
//    }
//
//    // code to add the new dates
//    void addContact(Dates dates) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, dates.getName()); // Dates Name
//        values.put(KEY_DOB, dates.getDob()); // Dates DOB
//        values.put(KEY_OCCASSION, dates.getOccassion()); // Dates Occassion
//
//        // Inserting Row
//        db.insert(TABLE_DATES, null, values);
//        //2nd argument is String containing nullColumnHack
//        db.close(); // Closing database connection
//    }
//
//    // code to get the single dates
//    Dates getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_DATES, new String[]{KEY_ID,
//                        KEY_NAME, KEY_DOB, KEY_OCCASSION}, KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Dates dates = new Dates(cursor.getString(0),
//                cursor.getString(1), cursor.getString(2), cursor.getString(3));
//        // return dates
//        return dates;
//    }
//
//    // code to get all contacts in a list view
//    public List<Dates> getAllContacts() {
//        List<Dates> contactList = new ArrayList<Dates>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_DATES;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Dates dates = new Dates();
//                dates.setId(cursor.getString(0));
//                dates.setName(cursor.getString(1));
//                dates.setDob(cursor.getString(2));
////                dates.setOccassion(cursor.getString(3));
//
//                // Adding dates to list
//                contactList.add(dates);
//            } while (cursor.moveToNext());
//        }
//
//        // return dates list
//        return contactList;
//    }
//
//    // code to update the single dates
//    public int updateContact(Dates dates) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, dates.getName());
//        values.put(KEY_DOB, dates.getDob());
//        values.put(KEY_OCCASSION, dates.getOccassion());
//        // updating row
//        return db.update(TABLE_DATES, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(dates.getId())});
//    }
//
//    // Deleting single dates
//    public void deleteContact(Dates dates) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_DATES, KEY_ID + " = ?",
//                new String[]{String.valueOf(dates.getId())});
//        db.close();
//    }
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_DATES;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
//
//
//}
