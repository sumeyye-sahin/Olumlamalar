package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.model.AffirmationsListModel
import java.util.Locale

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoriteList = MutableLiveData<List<AffirmationsListModel>>()
    val favoriteList: LiveData<List<AffirmationsListModel>> get() = _favoriteList

    private val dbHelper = DBHelper(application)

    fun loadFavoriteList(language: String) {
        val favorites = dbHelper.getFavoriteAffirmationsByLanguage(language).distinctBy { it.affirmation }
        _favoriteList.value = favorites
    }
    fun deleteFavorite(affirmation: AffirmationsListModel) {
        dbHelper.updateAffirmationFavStatus(affirmation.apply { favorite = false })
        loadFavoriteList(affirmation.language)
    }


}