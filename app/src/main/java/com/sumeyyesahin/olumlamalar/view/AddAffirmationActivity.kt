package com.sumeyyesahin.olumlamalar.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityAddAffirmationBinding
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper.clickedButton
import com.sumeyyesahin.olumlamalar.utils.Constants
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage
import com.sumeyyesahin.olumlamalar.viewmodel.AddAffirmationViewModel

class AddAffirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAffirmationBinding
    private val viewModel: AddAffirmationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAffirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.geri.setOnClickListener {
            clickedButton(binding.geri)
            finish()
        }

        binding.ekle.setOnClickListener {
            clickedButton(binding.ekle)
            val olumlamaMetni = binding.editText.text.toString()
            viewModel.addNewAffirmation(olumlamaMetni, this)
        }

        observeViewModel()
    }
    private fun observeViewModel() {
        viewModel.affirmationAdded.observe(this) { added ->
            if (added) {
                setResult(RESULT_OK)
                finish()
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
