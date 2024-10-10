package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.activities.AffirmationMainPageActivity
import com.sumeyyesahin.olumlamalar.activities.FavoritesActivity

class CategoryViewModel(application : Application): AndroidViewModel(application) {
    private val _navigateToActivity = MutableLiveData<Class<out AppCompatActivity>>()
    val navigateToActivity: LiveData<Class<out AppCompatActivity>> get() = _navigateToActivity

    fun onCategoryClicked(category: String) {
        val context = getApplication<Application>().applicationContext
        val activityClass = when (category) {
            context.getString(R.string.favorite_affirmations) -> FavoritesActivity::class.java
            else -> AffirmationMainPageActivity::class.java
        }
        _navigateToActivity.value = activityClass
    }

    fun clearNavigation() {
        _navigateToActivity.value = null
    }
}