package com.example.btth4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {  // same as static in Java
        private const val DATABASE_NAME = "ContactDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Contact"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT,
                $COLUMN_PHONE TEXT
            );
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addContact(username: String, phone: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PHONE, phone)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun updateContact(id: Int, username: String, phone: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PHONE, phone)
        }
        val rowsAffected = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun getAllContacts() : List<Contact> {
        val contactList = mutableListOf<Contact>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                contactList.add(Contact(id, username, phone))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contactList
    }
}