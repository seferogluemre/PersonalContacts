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
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuerySql = "CREATE TABLE IF NOT EXISTS contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, person_name TEXT NOT NULL, phone_number TEXT NOT NULL CHECK(length(phone_number) = 11), email TEXT, address TEXT, created_at TEXT DEFAULT CURRENT_TIMESTAMP)";
        String createEmergencyTableQuery = "CREATE TABLE IF NOT EXISTS emergency_contacts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "person_id INTEGER NOT NULL, " +
                "added_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (person_id) REFERENCES contacts(id)" +
                ")";

        db.execSQL(createTableQuerySql);
        db.execSQL(createEmergencyTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (!isColumnExists(db, "contacts", "email")) {
            db.execSQL("ALTER TABLE contacts ADD COLUMN email TEXT");
        }
    }

    private boolean isColumnExists(SQLiteDatabase db, String tableName, String columnName) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
            while (cursor.moveToNext()) {
                String currentColumn = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                if (currentColumn.equals(columnName)) {
                    return true;
                }
            }
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
        Cursor cursor = db.rawQuery("SELECT * FROM contacts ORDER BY created_at DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("person_name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));

                // Eğer email ve address'i de göstermek istersen Person sınıfına ekle
                // String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                // String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));

                persons.add(new Person(name, phone, id));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return persons;
    }

    public void addToEmergencyContacts(int personId) {
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "insert into emergency_contacts (person_id) values (?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindLong(1, personId);
        statement.executeInsert();
        database.close();
    }


}
