package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sumeyyesahin.olumlamalar.databinding.ActivityAffirmationMainPageBinding
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel


class AffirmationMainPageActivity : AppCompatActivity() {
    private lateinit var olumlamalar: List<Olumlamalarlistmodel>
    private var currentIndex = 0
    private lateinit var binding: ActivityAffirmationMainPageBinding
    val PREFS_NAME = "MyPrefs"
    val LAST_AFFIRMATION_ID = "lastAffirmationId"
    val LAST_CATEGORY = "lastCategory"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAffirmationMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kategori = intent.getStringExtra("kategori")
        binding.textView.text = kategori
        binding.textView.text = Html.fromHtml("<u>$kategori</u>")

        // DBHelper sınıfını kullanarak kategorinin olumlamalarını alıyoruz
        olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori!!)

        // İlk olumlama metnini güncelle
        updateAffirmationText()

        // İleri butonuna tıklandığında bir sonraki olumlamayı göster
        binding.ileri.setOnClickListener {
            currentIndex = (currentIndex + 1) % olumlamalar.size
            updateAffirmationText()
        }

        // Geri butonuna tıklandığında bir önceki olumlamayı göster
        binding.geri.setOnClickListener {
            currentIndex = (currentIndex - 1 + olumlamalar.size) % olumlamalar.size
            updateAffirmationText()
        }

        binding.share.setOnClickListener {
            // Olumlamayı paylaşmak

            val bitmap = takeScreenshot(binding.imageView, binding.olumlamalarTextView)

            // Share intent
            val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Olumlama", null)
            val uri = Uri.parse(path)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))

/*
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, olumlamalar[currentIndex].affirmation)
            startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))*/
        }
    }

    private fun takeScreenshot(imageView: ImageView, textView: TextView) : Bitmap {
        val returnedBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        imageView.draw(canvas)

        val (xDelta, yDelta) = calculateTextViewPosition(imageView, textView)
        canvas.translate(xDelta, yDelta)
        textView.draw(canvas)

        return returnedBitmap
    }


    private fun calculateTextViewPosition(imageView: ImageView, textView: TextView): Pair<Float, Float> {
        val imageViewLocation = IntArray(2)
        imageView.getLocationOnScreen(imageViewLocation)

        val xDelta = (imageView.width - textView.width) / 2f
        val yDelta = (imageView.height - textView.height) / 2f

        return Pair(xDelta, yDelta)
    }

    // take screenshot
 /*   private fun takeScreenshot(view: View) : Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap

    }
*/



    // Olumlamayı güncellemek için özel bir fonksiyon
    private fun updateAffirmationText() {
        if (olumlamalar.isNotEmpty()) {
            binding.olumlamalarTextView.text = olumlamalar[currentIndex].affirmation
        } else {
            binding.olumlamalarTextView.text = "Henüz olumlama bulunmamaktadır."
        }
    }

    // SharedPreferences'a son id ve kategori bilgisini kaydetme
    fun saveLastPosition(context: Context, id: Int, category: String) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt(LAST_AFFIRMATION_ID, id)
        editor.putString(LAST_CATEGORY, category)
        editor.apply()
    }
    // SharedPreferences'dan son id ve kategori bilgisini alma
    fun getLastPosition(context: Context): Pair<Int, String> {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastId = sharedPrefs.getInt(LAST_AFFIRMATION_ID, 0) // Varsayılan olarak 0 döndür
        val lastCategory = sharedPrefs.getString(LAST_CATEGORY, "") ?: ""
        return Pair(lastId, lastCategory)
    }



    // Kategori sayfasına gitmek için onClick fonksiyonu
    fun kategori(view: View){
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }
/*
    // İçeriği paylaşmak için onClick fonksiyonu
    fun share(view: View){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Bu bir örnek metin")
        startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))
    }*/
}
