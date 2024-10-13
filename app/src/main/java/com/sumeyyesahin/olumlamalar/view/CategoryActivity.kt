package com.sumeyyesahin.olumlamalar.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyesahin.olumlamalar.adapters.CategoryAdapter
import com.sumeyyesahin.olumlamalar.databinding.ActivityCategoryBinding
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage.getUserLanguage
import com.sumeyyesahin.olumlamalar.viewmodel.CategoryViewModel
import androidx.activity.viewModels
import com.sumeyyesahin.olumlamalar.R

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private  val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.categories)



        categoryViewModel.initialize()

        observeLiveData()

    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }
    override fun onResume() {
        super.onResume()
        categoryViewModel.getCategories().observe(this, Observer { categories ->
            val adapter = CategoryAdapter(categories, categoryViewModel.language)
            binding.recyclerView.adapter = adapter
        })
    }

    fun observeLiveData() {
        categoryViewModel.getCategories().observe(this, Observer { categories ->
            val adapter = CategoryAdapter(categories, categoryViewModel.language)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        })
    }
}
