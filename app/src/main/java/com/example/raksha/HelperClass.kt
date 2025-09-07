package com.example.raksha

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.raksha.data.membersData


class HelperClass(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "members.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allMembers"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NUMBER = "number"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_MSG = "msg"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_NUMBER TEXT,
                $COLUMN_MSG TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    fun insertMember(members : membersData){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, members.name)
            put(COLUMN_NUMBER, members.phoneNumber)
            put(COLUMN_MSG, members.msg
            )
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllMember(): List<membersData>{
        val memberList = mutableListOf<membersData>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER))
            val msg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG))

            val member = membersData(id, number, name, msg)
            memberList.add(member)
        }
        cursor.close()
        db.close()
        return memberList
    }
    // Update member
    fun updateMember(id: Int, name: String, number: String, msg: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_NUMBER, number)
            put(COLUMN_MSG, msg)
        }
        val result = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // Delete member
    fun deleteMember(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}