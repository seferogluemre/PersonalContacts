package com.emreseferoglu.personalcontacts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PersonelContacts";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuerySql = "CREATE TABLE IF NOT EXISTS contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, person_name text NOT NULL,phone_number text NOT NULL CHECK(length(phone_number) = 11),email text, address text,created_at TEXT DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTableQuerySql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE contacts ADD COLUMN email TEXT");
        onCreate(db);
    }

    public void addPerson(String name, String phone) {
        addPerson(name, phone, null, null);
    }

    public void addPerson(String name, String phone, String email, String address) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String sql = "INSERT INTO contacts (person_name, phone_number, email, address) VALUES (?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, name);
            statement.bindString(2, phone);

            if (email != null) {
                statement.bindString(3, email);
            } else {
                statement.bindNull(3);
            }

            if (address != null) {
                statement.bindString(4, address);
            } else {
                statement.bindNull(4);
            }

            statement.executeInsert();
        } catch (Exception e) {
            Log.e("DB_ERROR", "addPerson error: " + e.getMessage());
        }
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contacts", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("person_name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));

                persons.add(new Person(name, phone));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return persons;
    }

}
