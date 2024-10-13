package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage

class AddAffirmationViewModel ( application: Application) : AndroidViewModel(application) {

    private val _affirmationAdded = MutableLiveData<Boolean>()
    val affirmationAdded: LiveData<Boolean> get() = _affirmationAdded

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val dbHelper = DBHelper(application)

    fun addNewAffirmation(affirmationText: String, context: Context) {
        val language = GetSetUserLanguage.getUserLanguage(context)

        if (affirmationText.isNotBlank()) {
            dbHelper.addNewAffirmation(affirmationText, context.getString(R.string.add_affirmation_title), language)
            _affirmationAdded.value = true
        } else {
            _error.value = context.getString(R.string.toast_empty_affirmation)
        }
    }
}
