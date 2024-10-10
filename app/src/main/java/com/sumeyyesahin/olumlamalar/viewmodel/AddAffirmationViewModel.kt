package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage

class AddAffirmationViewModel ( application: Application) : AndroidViewModel(application) {

    private val _addAffirmationResult = MutableLiveData<Boolean>()
    val addAffirmationResult: LiveData<Boolean> get() = _addAffirmationResult

    fun addNewAffirmation(affirmationText: String) {
        val language = GetSetUserLanguage.getUserLanguage(getApplication())
        if (affirmationText.isNotBlank()) {
            DBHelper(getApplication()).addNewAffirmation(
                affirmationText,
                getApplication<Application>().getString(R.string.add_affirmation_title),
                language
            )
            _addAffirmationResult.value = true
        } else {
            _addAffirmationResult.value = false
        }
    }
}
