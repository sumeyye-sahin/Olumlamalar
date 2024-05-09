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

        // Kullanıcının son görüntülenen olumlamadan ID'sini kontrol et
        val lastId = getLastPosition(this)
        currentIndex = lastId

        // DBHelper sınıfını kullanarak kategorinin olumlamalarını alıyoruz
        olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori!!)

        // İlk olumlama metnini güncelle
        updateAffirmationText()

        // İleri butonuna tıklandığında bir sonraki olumlamayı göster
        binding.ileri.setOnClickListener {
            currentIndex = (currentIndex + 1) % olumlamalar.size
            updateAffirmationText()
            // Son görüntülenen olumlamadan ID'sini kaydet
            saveLastPosition(this, currentIndex)
        }

        // Geri butonuna tıklandığında bir önceki olumlamayı göster
        binding.geri.setOnClickListener {
            currentIndex = (currentIndex - 1 + olumlamalar.size) % olumlamalar.size
            updateAffirmationText()
            // Son görüntülenen olumlamadan ID'sini kaydet
            saveLastPosition(this, currentIndex)
        }

        binding.share.setOnClickListener {
            // Olumlamayı paylaşmak

            val bitmap = takeScreenshot(binding.imageView, binding.olumlamalarTextView)

            // Share intent
            val path =
                MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Olumlama", null)
            val uri = Uri.parse(path)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))

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





    // Olumlamayı güncellemek için özel bir fonksiyon
    private fun updateAffirmationText() {
        if (olumlamalar.isNotEmpty()) {
            binding.olumlamalarTextView.text = olumlamalar[currentIndex].affirmation
        } else {
            binding.olumlamalarTextView.text = "Henüz olumlama bulunmamaktadır."
        }
    }

    // SharedPreferences'a son görüntülenen olumlamadan ID bilgisini kaydetme
    fun saveLastPosition(context: Context, id: Int) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt(LAST_AFFIRMATION_ID, id)
        editor.apply()
    }

    // SharedPreferences'dan son görüntülenen olumlamadan ID bilgisini alma
    fun getLastPosition(context: Context): Int {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getInt(LAST_AFFIRMATION_ID, 0) // Varsayılan olarak 0 döndür
    }



    // Kategori sayfasına gitmek için onClick fonksiyonu
    fun kategori(view: View){
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }
}
