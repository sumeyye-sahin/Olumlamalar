package com.sumeyyesahin.olumlamalar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

//  val myDatabase= this.openOrCreateDatabase("Olumlamalar", MODE_PRIVATE,null)
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS olumlamalar (id INTEGER PRIMARY KEY, affirmation VARCHAR, category VARCHAR, favorite BOOLEAN)")


    companion object {
        private val DATABASE_NAME = "Olumlamalar"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "olumlamalar"
        val COLUMN_ID = "id"
        val COLUMN_AFFIRMATION = "affirmation"
        val COLUMN_CATEGORY = "category"
        val COLUMN_FAVORITE = "favorite"
    }



    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_AFFIRMATION + " VARCHAR," +
                COLUMN_CATEGORY + " VARCHAR," +
                COLUMN_FAVORITE + " BOOLEAN)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun deleteAffirmation(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateAffirmationWithLikeStatus(affirmation: Olumlamalarlistmodel, isLiked: Boolean) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation.affirmation)
            put(COLUMN_CATEGORY, affirmation.category)
            put(COLUMN_FAVORITE, isLiked) // Beğenildiyse true, beğenilmediyse false
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
    }

    fun updateAffirmation(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation.affirmation)
            put(COLUMN_CATEGORY, affirmation.category)
            put(COLUMN_FAVORITE, affirmation.favorite)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
    }



    fun addAffirmation(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_AFFIRMATION, affirmation.affirmation)
        values.put(COLUMN_CATEGORY, affirmation.category)
        values.put(COLUMN_FAVORITE, affirmation.favorite)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllCategories(): List<String> {
        val kategoriListesi = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $COLUMN_CATEGORY FROM $TABLE_NAME", null)
        cursor.use {
            while (it.moveToNext()) {
                val kategori = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                kategoriListesi.add(kategori)
            }
        }
        return kategoriListesi
    }


    /*
    // get all affirmations
    fun getAllAffirmations(): ArrayList<Olumlamalarlistmodel> {
        val list = ArrayList<Olumlamalarlistmodel>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val affirmation = Olumlamalarlistmodel()
                affirmation.id = result.getString(result.getColumnIndex(COLUMN_ID)).toInt()
                affirmation.affirmation = result.getString(result.getColumnIndex(COLUMN_AFFIRMATION))
                affirmation.category = result.getString(result.getColumnIndex(COLUMN_CATEGORY))
                affirmation.favorite = result.getString(result.getColumnIndex(COLUMN_FAVORITE)).toBoolean()
                list.add(affirmation)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
*/

}