package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.model.OlumlamalarListModel
import java.util.Locale

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DBHelper(application)
    private val _favoriteAffirmations = MutableLiveData<List<OlumlamalarListModel>>()
    val favoriteAffirmations: LiveData<List<OlumlamalarListModel>> get() = _favoriteAffirmations

    init {
        loadFavoriteAffirmations()
    }
    private fun loadFavoriteAffirmations() {
        val language = getUserLanguage(getApplication())
        _favoriteAffirmations.value = dbHelper.getFavoriteAffirmationsByLanguage(language).distinctBy { it.affirmation }
    }

    fun getUserLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", Locale.getDefault().language) ?: "en"
    }

    fun deleteAffirmation(affirmation: OlumlamalarListModel) {
        affirmation.favorite = false
        dbHelper.updateAffirmationFavStatus(affirmation)
        _favoriteAffirmations.value = _favoriteAffirmations.value?.filterNot { it.affirmation == affirmation.affirmation && it.language == affirmation.language }
    }
}