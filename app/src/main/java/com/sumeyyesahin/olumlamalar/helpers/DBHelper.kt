package com.sumeyyesahin.olumlamalar.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.model.AffirmationsListModel
import org.json.JSONArray

class DBHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Olumlamalar"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "olumlamalar"
        const val COLUMN_ID = "id"
        const val COLUMN_AFFIRMATION = "affirmation"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_FAVORITE = "favorite"
        const val COLUMN_LANGUAGE = "language"
        private const val COLUMN_TEXT = "text"
    }

    override fun onCreate(myDatabase: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_AFFIRMATION VARCHAR," +
                "$COLUMN_CATEGORY VARCHAR," +
                "$COLUMN_FAVORITE BOOLEAN," +
                "$COLUMN_LANGUAGE VARCHAR)"
        myDatabase?.execSQL(createTable)
        loadAffirmationsFromJSON(myDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_LANGUAGE VARCHAR DEFAULT 'en'")
            loadAffirmationsFromJSON(db)
        }
    }

    private fun loadAffirmationsFromJSON(db: SQLiteDatabase?) {
        val jsonString = loadJSONFromAsset(context, "olumlamalar.json")
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            db?.beginTransaction()
            try {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val affirmation = jsonObject.getString("affirmation")
                    val category = jsonObject.getString("category")
                    val favorite = jsonObject.getInt("favorite")
                    val language = jsonObject.getString("language")

                    val insertStatement = "INSERT INTO $TABLE_NAME ($COLUMN_AFFIRMATION, $COLUMN_CATEGORY, $COLUMN_FAVORITE, $COLUMN_LANGUAGE) VALUES (?, ?, ?, ?)"
                    db?.execSQL(insertStatement, arrayOf(affirmation, category, favorite, language))
                }
                db?.setTransactionSuccessful()
            } finally {
                db?.endTransaction()
            }
        }
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).use { inputStream ->
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                String(buffer, Charsets.UTF_8)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun getRandomCategory(language: String): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $COLUMN_CATEGORY FROM $TABLE_NAME WHERE $COLUMN_LANGUAGE = ?", arrayOf(language))

        val categories = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return if (categories.isNotEmpty()) {
            categories.random()
        } else {
            "default_category"
        }
    }

    fun getRandomAffirmationByCategory(category: String, language: String): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_AFFIRMATION FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? AND $COLUMN_LANGUAGE = ? ORDER BY RANDOM() LIMIT 1", arrayOf(category, language))
        var affirmation = "No affirmations available"
        cursor.use {
            if (it.moveToFirst()) {
                affirmation = it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION))
            }
        }
        return affirmation
    }

    fun getOlumlamalarByCategoryAndLanguage(category: String, language: String): List<AffirmationsListModel> {
        val db = this.readableDatabase
        val list = mutableListOf<AffirmationsListModel>()
        db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? AND $COLUMN_LANGUAGE = ?", arrayOf(category, language)).use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val affirmation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AFFIRMATION))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val favorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) > 0
                val language = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                list.add(AffirmationsListModel(id, affirmation, category, favorite, language))
            }
        }

        if (list.isEmpty()) {
            db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ?", arrayOf(category)).use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val affirmation = cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_AFFIRMATION
                    ))
                    val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                    val favorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) > 0
                    val language = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                    list.add(AffirmationsListModel(id, affirmation, category, favorite, language))
                }
            }
        }

        return list
    }

    fun deleteAffirmation(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }


    fun updateAffirmationFavStatus(affirmation: AffirmationsListModel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FAVORITE, affirmation.favorite)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
    }

    fun addAffirmationFav(affirmation: AffirmationsListModel, isFavorite: Boolean, language: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation.affirmation)
            put(COLUMN_CATEGORY, context.getString(R.string.favorite_affirmations))
            put(COLUMN_FAVORITE, isFavorite)
            put(COLUMN_LANGUAGE, language)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteFavoriteAffirmationByCategoryAndAffirmationName(category: String, affirmation: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_CATEGORY = ? AND $COLUMN_AFFIRMATION = ?", arrayOf(category, affirmation))
        db.close()
    }

    fun getFavoriteAffirmationsByLanguage(language: String): List<AffirmationsListModel> {
        val olumlamalar = mutableListOf<AffirmationsListModel>()
        val db = this.readableDatabase
        db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_FAVORITE = 1 AND $COLUMN_LANGUAGE = ?", arrayOf(language)).use { cursor ->
            while (cursor.moveToNext()) {
                val olumlama = AffirmationsListModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AFFIRMATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                )
                olumlamalar.add(olumlama)
            }
        }
        return olumlamalar
    }


    fun addNewAffirmation(affirmation: String, category: String, language: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation)
            put(COLUMN_CATEGORY, category)
            put(COLUMN_FAVORITE, false)
            put(COLUMN_LANGUAGE, language)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }



    fun getAllCategoriesByLanguage(language: String): List<String> {
        val kategoriListesi = mutableListOf<String>()
        val db = readableDatabase
        db.rawQuery("SELECT DISTINCT $COLUMN_CATEGORY FROM $TABLE_NAME WHERE $COLUMN_LANGUAGE = ?", arrayOf(language)).use { cursor ->
            while (cursor.moveToNext()) {
                val kategori = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                kategoriListesi.add(kategori)
            }
        }
        if (!kategoriListesi.contains(context.getString(R.string.add_affirmation_title))) {
            kategoriListesi.add(context.getString(R.string.add_affirmation_title))
        }
        return kategoriListesi
    }

}
