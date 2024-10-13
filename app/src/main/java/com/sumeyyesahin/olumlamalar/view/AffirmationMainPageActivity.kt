package com.sumeyyesahin.olumlamalar.view


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityAffirmationMainPageBinding
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper.clickedButton
import com.sumeyyesahin.olumlamalar.model.AffirmationsListModel
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage.setUserLanguage
import com.sumeyyesahin.olumlamalar.viewmodel.AffirmationMainPageViewModel
import java.io.File
import java.io.FileOutputStream

class AffirmationMainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAffirmationMainPageBinding
    private val viewModel: AffirmationMainPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAffirmationMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val category = intent.getStringExtra("kategori") ?: ""
        viewModel.initialize(category, this)

        observeLiveData()

        binding.ileri.setOnClickListener {
            clickedButton(binding.ileri)
            val newIndex = (viewModel.currentIndex.value?.plus(1) ?: 0) % (viewModel.olumlamalar.value?.size ?: 1)
            viewModel.updateCurrentIndex(this, newIndex, category)
        }

        binding.geri.setOnClickListener {
            clickedButton(binding.geri)
            val newIndex = (viewModel.currentIndex.value?.minus(1)?.takeIf { it >= 0 } ?: (viewModel.olumlamalar.value?.size ?: 1) - 1)
            viewModel.updateCurrentIndex(this, newIndex, category)
        }

        binding.delete.setOnClickListener {
            clickedButton(binding.delete)
            // Affirmasyon listesi boş değilse silme işlemini gerçekleştir
            viewModel.olumlamalar.value?.let { affirmations ->
                if (affirmations.isNotEmpty()) {
                    affirmations[viewModel.currentIndex.value ?: 0].let { affirmation ->
                        viewModel.deleteAffirmation(affirmation)
                        viewModel.updateCurrentIndex(this, 0, category)

                        // Liste boşaldıysa, butonları güncelle
                        if (affirmations.size == 1) {
                            updateUI(emptyList(), 0)
                        }
                    }
                } else {

                }
            }
        }


        binding.addbutton.setOnClickListener {
            clickedButton(binding.addbutton)
            val intent = Intent(this, AddAffirmationActivity::class.java)
            startActivity(intent)
        }

        binding.like.setOnClickListener {
            viewModel.olumlamalar.value?.get(viewModel.currentIndex.value ?: 0)?.let { affirmation ->
                viewModel.toggleFavorite(affirmation)
                updateLikeButtonIcon(affirmation)
            }
        }

        binding.share.setOnClickListener {
            clickedButton(binding.share)
            val bitmap = takeScreenshot(binding.affirmationBackground, binding.olumlamalarTextView)
            shareBitmap(bitmap)
        }
    }

    fun observeLiveData() {
        viewModel.currentIndex.observe(this) { index ->
            viewModel.olumlamalar.value?.let { affirmations ->
                updateUI(affirmations, index)
            }
        }

        viewModel.category.observe(this) { category ->
            binding.textView.text = Html.fromHtml("<u>$category</u>")
        }

        viewModel.language.observe(this) { language ->
            setUserLanguage(this, language)
        }

        viewModel.isMyAffirmationCategory.observe(this) { isMyCategory ->
            if (isMyCategory) {
                binding.delete.visibility = View.VISIBLE
                if (viewModel.olumlamalar.value?.isEmpty() == true) {
                    binding.olumlamalarTextView.text = getString(R.string.add_buton)
                    binding.delete.visibility = View.GONE
                }


            } else {
                binding.delete.visibility = View.GONE
            }


        }
    }


    override fun onResume() {
        super.onResume()
        val category = intent.getStringExtra("kategori") ?: ""
        val language = viewModel.language.value ?: "en"
        viewModel.loadAffirmations(category, language)
        viewModel.olumlamalar.value?.let { affirmations ->
            updateUI(affirmations, viewModel.currentIndex.value ?: 0)
        }


    }

    private fun updateUI(affirmations: List<AffirmationsListModel>, index: Int) {
        val category = intent.getStringExtra("kategori") ?: ""
        val isMyAffirmationsCategory = category == getString(R.string.add_affirmation_title)

        if (affirmations.isNotEmpty()) {

            if(category==getString(R.string.add_buton)){
                binding.delete.visibility = View.VISIBLE
            }
            else{
                binding.delete.visibility = View.VISIBLE
            }
            binding.olumlamalarTextView.text = affirmations[index].affirmation
            updateLikeButtonIcon(affirmations[index])

            binding.ileri.visibility = View.VISIBLE
            binding.geri.visibility = View.VISIBLE
            binding.like.visibility = View.VISIBLE
            binding.share.visibility = View.VISIBLE
        } else {
            binding.ileri.visibility = View.INVISIBLE
            binding.geri.visibility = View.INVISIBLE
            binding.like.visibility = View.INVISIBLE
            binding.share.visibility = View.INVISIBLE
            binding.olumlamalarTextView.text = getString(R.string.add_buton)
            binding.delete.visibility = View.INVISIBLE

        }

        binding.addbutton.visibility = View.VISIBLE
        binding.buttonkategori.visibility = View.VISIBLE
    }

    private fun updateLikeButtonIcon(affirmation: AffirmationsListModel) {
        val iconRes = if (affirmation.favorite) R.drawable.baseline_favorite else R.drawable.baseline_favorite_border_24
        binding.like.setBackgroundResource(iconRes)
    }

    private fun enableButtons(enable: Boolean) {
        if (enable) {
            binding.ileri.visibility = View.VISIBLE
            binding.geri.visibility = View.VISIBLE
            binding.share.visibility = View.VISIBLE
            binding.like.visibility = View.VISIBLE
        } else {
            binding.ileri.visibility = View.INVISIBLE
            binding.geri.visibility = View.INVISIBLE
            binding.share.visibility = View.INVISIBLE
            binding.like.visibility = View.INVISIBLE
        }
    }

    private fun shareBitmap(bitmap: Bitmap) {
        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Content"))
    }

    private fun takeScreenshot(imageView: ImageView, textView: TextView): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        imageView.draw(canvas)

        val (xDelta, yDelta) = calculateTextViewPosition(imageView, textView)
        canvas.translate(xDelta, yDelta)
        textView.draw(canvas)

        return returnedBitmap
    }

    private fun calculateTextViewPosition(imageView: ImageView, textView: TextView): Pair<Float, Float> {
        val xDelta = (imageView.width - textView.width) / 2f
        val yDelta = (imageView.height - textView.height) / 2f

        return Pair(xDelta, yDelta)
    }
    fun category(view: View) {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onBackPressed() {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}


