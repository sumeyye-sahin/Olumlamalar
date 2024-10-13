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
    val language = GetSetUserLanguage.getUserLanguage(getApplication())

    fun initialize() {
        setLocale()
    }

    private fun setLocale() {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(getApplication<Application>().resources.configuration)
        config.setLocale(locale)
        getApplication<Application>().resources.updateConfiguration(config, getApplication<Application>().resources.displayMetrics)
    }

    fun getCategories(): MutableLiveData<List<String>> {
        val kategoriListesi = dbHelper.getAllCategoriesByLanguage(language)
        categoryList.value = kategoriListesi
        return categoryList
    }


}