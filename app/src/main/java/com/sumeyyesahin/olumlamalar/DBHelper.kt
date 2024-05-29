package com.sumeyyesahin.olumlamalar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel
import org.json.JSONArray
import java.util.Locale

class DBHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Olumlamalar"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "olumlamalar"
        const val COLUMN_ID = "id"
        const val COLUMN_AFFIRMATION = "affirmation"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_FAVORITE = "favorite"
        const val COLUMN_LANGUAGE = "language" // Yeni dil sütunu
        private const val COLUMN_TEXT = "text"

    }

    override fun onCreate(myDatabase: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_AFFIRMATION + " VARCHAR," +
                COLUMN_CATEGORY + " VARCHAR," +
                COLUMN_FAVORITE + " BOOLEAN," +
                COLUMN_LANGUAGE + " VARCHAR)"
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
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun getRandomAffirmationByCategory(category: String, language: String): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_AFFIRMATION FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? AND $COLUMN_LANGUAGE = ? ORDER BY RANDOM() LIMIT 1", arrayOf(category, language))
        var affirmation = "No affirmations available"
        if (cursor.moveToFirst()) {
            affirmation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AFFIRMATION))
        }
        cursor.close()
        return affirmation
    }

    fun getOlumlamalarByCategoryAndLanguage(category: String, language: String): List<Olumlamalarlistmodel> {
        val db = this.readableDatabase
        val list = mutableListOf<Olumlamalarlistmodel>()
        var cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? AND $COLUMN_LANGUAGE = ?", arrayOf(category, language))
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val affirmation = it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION))
                val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val favorite = it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) > 0
                val language = it.getString(it.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                list.add(Olumlamalarlistmodel(id, affirmation, category, favorite, language))
            }
        }

        // Eğer liste boşsa, dil filtresi olmadan tekrar sorgula
        if (list.isEmpty()) {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ?", arrayOf(category))
            cursor.use {
                while (it.moveToNext()) {
                    val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                    val affirmation = it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION))
                    val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                    val favorite = it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) > 0
                    val language = it.getString(it.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                    list.add(Olumlamalarlistmodel(id, affirmation, category, favorite, language))
                }
            }
        }

        cursor.close()
        return list
    }

    fun deleteAffirmation(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getAffirmationCountByCategoryAndLanguage(category: String, language: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? AND $COLUMN_LANGUAGE = ?", arrayOf(category, language))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    fun updateAffirmationFavStatus(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FAVORITE, affirmation.favorite)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
    }

    fun addAffirmationFav(affirmation: Olumlamalarlistmodel, isFavorite: Boolean, language: String) {
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

    fun getFavoriteAffirmationsByLanguage(language: String): List<Olumlamalarlistmodel> {
        val olumlamalar = mutableListOf<Olumlamalarlistmodel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_FAVORITE = 1 AND $COLUMN_LANGUAGE = ?", arrayOf(language))
        cursor.use {
            while (it.moveToNext()) {
                val olumlama = Olumlamalarlistmodel(
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1,
                    it.getString(it.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                )
                olumlamalar.add(olumlama)
            }
        }
        return olumlamalar
    }

    fun updateAffirmation(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation.affirmation)
            put(COLUMN_CATEGORY, affirmation.category)
            put(COLUMN_FAVORITE, affirmation.favorite)
            put(COLUMN_LANGUAGE, affirmation.language) // Gerekirse dili güncelle
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
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

    fun getOnlyAffirmationByCategoryAndLanguage(category: String, language: String): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_AFFIRMATION FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? AND $COLUMN_LANGUAGE = ?", arrayOf(category, language))
        var affirmation = ""
        if (cursor.moveToFirst()) {
            affirmation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AFFIRMATION))
        }
        cursor.close()
        return affirmation
    }

    fun getAllCategoriesByLanguage(language: String): List<String> {
        val kategoriListesi = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $COLUMN_CATEGORY FROM $TABLE_NAME WHERE $COLUMN_LANGUAGE = ?", arrayOf(language))
        cursor.use {
            while (it.moveToNext()) {
                val kategori = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                kategoriListesi.add(kategori)
            }
        }
        if (!kategoriListesi.contains(context.getString(R.string.add_affirmation_title))) {
            kategoriListesi.add(context.getString(R.string.add_affirmation_title))
        }
        return kategoriListesi
    }

    private fun getLocalizedCategoryName(context: Context, category: String, language: String): String {
        val resources = when (language) {
            "tr" -> LocaleHelper.setLocale(context, "tr").resources
            "en" -> LocaleHelper.setLocale(context, "en").resources
            else -> LocaleHelper.setLocale(context, "en").resources
        }
        return when (language) {
            "tr" -> when (category) {
                "General Affirmations" -> resources.getString(R.string.general_affirmations)
                "Body Affirmations" -> resources.getString(R.string.body_affirmations)
                "Faith Affirmations" -> resources.getString(R.string.faith_affirmations)
                "Tough Days Affirmations" -> resources.getString(R.string.bad_days_affirmations)
                "Love Affirmations" -> resources.getString(R.string.love_affirmations)
                "Self-Worth Affirmations" -> resources.getString(R.string.self_value_affirmations)
                "Stress and Anxiety Affirmations" -> resources.getString(R.string.stress_affirmations)
                "Positive Thought Affirmations" -> resources.getString(R.string.positive_thought_affirmations)
                "Success Affirmations" -> resources.getString(R.string.success_affirmations)
                "Personal Development Affirmations" -> resources.getString(R.string.personal_development_affirmations)
                "Time Management Affirmations" -> resources.getString(R.string.time_management_affirmations)
                "Relationship Affirmations" -> resources.getString(R.string.relationship_affirmations)
                "Prayer and Request" -> resources.getString(R.string.prayer_affirmations)
                else -> category
            }
            "en" -> when (category) {
                "Genel Olumlamalar" -> resources.getString(R.string.general_affirmations)
                "Beden Olumlamaları" -> resources.getString(R.string.body_affirmations)
                "İnanç Olumlamaları" -> resources.getString(R.string.faith_affirmations)
                "Zor Günler Olumlamaları" -> resources.getString(R.string.bad_days_affirmations)
                "Sevgi ve Aşk Olumlamaları" -> resources.getString(R.string.love_affirmations)
                "Öz Değer Olumlamaları" -> resources.getString(R.string.self_value_affirmations)
                "Stres ve Kaygı Olumlamaları" -> resources.getString(R.string.stress_affirmations)
                "Pozitif Düşünce Olumlamaları" -> resources.getString(R.string.positive_thought_affirmations)
                "Başarı Olumlamaları" -> resources.getString(R.string.success_affirmations)
                "Kişisel Gelişim Olumlamaları" -> resources.getString(R.string.personal_development_affirmations)
                "Zaman Yönetimi Olumlamaları" -> resources.getString(R.string.time_management_affirmations)
                "İlişki Olumlamaları" -> resources.getString(R.string.relationship_affirmations)
                "Dua ve İstek" -> resources.getString(R.string.prayer_affirmations)
                else -> category
            }
            else -> category
        }
    }
}
