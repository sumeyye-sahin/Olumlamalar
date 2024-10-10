package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import android.content.res.Configuration
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.adapters.CategoryAdapter
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper.setLocale
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage
import java.util.Locale


class CategoryViewModel(application : Application): AndroidViewModel(application) {

    private val categoryList = MutableLiveData<List<String>>()

    private val dbHelper = DBHelper(getApplication())

    fun initialize(language: String) {
        setLocale(language)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(getApplication<Application>().resources.configuration)
        config.setLocale(locale)
        getApplication<Application>().resources.updateConfiguration(config, getApplication<Application>().resources.displayMetrics)
    }

    fun getCategories(language: String): MutableLiveData<List<String>> {
        val kategoriListesi = dbHelper.getAllCategoriesByLanguage(language)
        categoryList.value = kategoriListesi
        return categoryList
    }


}