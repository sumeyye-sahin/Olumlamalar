package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyesahin.olumlamalar.databinding.ActivityAffirmationMainPageBinding
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel

class AffirmationMainPageActivity : AppCompatActivity() {
    private lateinit var olumlamalar: List<Olumlamalarlistmodel>
    private lateinit var binding: ActivityAffirmationMainPageBinding
    private val PREFS_NAME = "MyPrefs"
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAffirmationMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kategori adını al
        val kategori2 = intent.getStringExtra("kategori")
        binding.textView.text = Html.fromHtml("<u>$kategori2</u>")

        val kategori = intent.getStringExtra("kategori")

        // Kullanıcının son görüntülenen olumlamadan ID'sini kontrol et
        currentIndex = getLastPosition(this, kategori!!)

        // DBHelper sınıfını kullanarak kategorinin olumlamalarını alıyoruz
        olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori)

        // currentIndex değeriyle olumlamaları güncelle

        updateUI()
       // updateAffirmationText(currentIndex)
       // updateLikeButtonIcon(currentIndex)
        if(kategori == "Kendi Olumlamalarım"){
            binding.delete.visibility = View.VISIBLE


        }else{
            binding.delete.visibility = View.GONE
        }

        // İleri butonuna tıklandığında bir sonraki olumlamayı göster
        binding.ileri.setOnClickListener {
            currentIndex = (currentIndex + 1) % olumlamalar.size
            updateAffirmationText(currentIndex)
            // Son görüntülenen olumlamadan ID'sini kaydet
            saveLastPosition(this, currentIndex, kategori)
           // updateLikeButtonIcon(currentIndex)
            updateUI()
        }

        // Geri butonuna tıklandığında bir önceki olumlamayı göster
        binding.geri.setOnClickListener {
            currentIndex = (currentIndex - 1 + olumlamalar.size) % olumlamalar.size
            updateAffirmationText(currentIndex)
            // Son görüntülenen olumlamadan ID'sini kaydet
            saveLastPosition(this, currentIndex, kategori)
            updateUI()
           // updateLikeButtonIcon(currentIndex)
        }

/*
        // Sil butonuna tıklandığında olumlamayı sil
        binding.delete.setOnClickListener {
            val clickedAffirmation = olumlamalar[currentIndex]
            DBHelper(this).deleteAffirmation(clickedAffirmation.id)
            olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori)

            if (olumlamalar.isNotEmpty()) {
                currentIndex = 0
                updateAffirmationText(currentIndex)
                updateLikeButtonIcon(currentIndex)
            } else {
                binding.olumlamalarTextView.text = "Henüz olumlama bulunmamaktadır."
                // Diğer UI elemanlarını da uygun şekilde gizleyin veya disable edin.
                binding.delete.visibility = View.GONE
            }}
*/
        binding.delete.setOnClickListener {
            if (olumlamalar.isNotEmpty()) {
                DBHelper(this).deleteAffirmation(olumlamalar[currentIndex].id, kategori!!)
                olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori)
                currentIndex = 0
                updateUI()
            }
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
        }

        binding.textView.setOnClickListener {
            // Kategori değiştiğinde currentIndex değerini güncelle
            currentIndex = getLastPosition(this, kategori)
            updateAffirmationText(currentIndex)
            updateUI()
        }

        // Beğen butonuna tıklama işlevselliğini ekle
        binding.like.setOnClickListener {
            // Kullanıcının tıkladığı olumlama
            val clickedAffirmation = olumlamalar[currentIndex]
            // Favori durumunu tersine çevir
            clickedAffirmation.favorite = !clickedAffirmation.favorite
            // Favori durumunu güncelle

            if( clickedAffirmation.favorite){
                DBHelper(this).addAffirmationFav(clickedAffirmation,false)
                DBHelper(this).updateAffirmationFavStatus(clickedAffirmation)
            }else{
                DBHelper(this).deleteFavoriteAffirmationByCategoryAndAffirmationName("Favori Olumlamalarım",clickedAffirmation.affirmation)
                DBHelper(this).updateAffirmationFavStatus(clickedAffirmation)
            }

            // Favori butonunun ikonunu güncelle
            updateLikeButtonIcon(currentIndex)
            // Favori durumunu güncelle
        }

        binding.addbutton.setOnClickListener {
            val intent = Intent(this, AddAffirmationActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_AFFIRMATION)
        }
    }

    override fun onResume() {
        super.onResume()
        // Kategori değiştiğinde currentIndex değerini güncelle
        val kategori = intent.getStringExtra("kategori")
       // currentIndex = getLastPosition(this, kategori!!)
        // DBHelper kullanarak kategorinin güncel olumlamalarını al
        olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori!!)

        // Listeyi güncelleme ve currentIndex'i sıfırlama
        if (olumlamalar.isNotEmpty()) {
            currentIndex = getLastPosition(this, kategori).coerceAtMost(olumlamalar.size - 1)
        } else {
            currentIndex = 0 // Eğer liste boşsa currentIndex'i sıfırla
        }

        updateUI()
       // updateAffirmationText(currentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_AFFIRMATION && resultCode == RESULT_OK) {
            // DBHelper kullanarak kategorinin güncel olumlamalarını al
            val kategori = intent.getStringExtra("kategori")!!
            olumlamalar = DBHelper(this).getOlumlamalarByCategory(kategori)
            updateUI() // UI'yi güncelle
        }
    }

    private fun updateUI() {
        if (olumlamalar.isNotEmpty()) {
            updateAffirmationText(currentIndex)
            updateLikeButtonIcon(currentIndex)
            enableButtons(true) // Butonları etkinleştir
        } else {
            binding.olumlamalarTextView.text = "Henüz olumlama bulunmamaktadır. + butonuna tıklayarak olumlama ekleyebilirsiniz."
            binding.like.background = getDrawable(R.drawable.baseline_favorite_border_24)
            binding.like.visibility = View.INVISIBLE
            binding.delete.visibility = View.INVISIBLE
            binding.ileri.visibility = View.INVISIBLE
            binding.geri.visibility = View.INVISIBLE
            binding.share.visibility = View.INVISIBLE

            binding.like.isClickable = false
            binding.delete.isClickable = false
            binding.ileri.isClickable = false
            binding.geri.isClickable = false
            binding.share.isClickable = false

        }
    }
    private fun enableButtons(enable: Boolean) {
        binding.ileri.isClickable = enable
        binding.geri.isClickable = enable
        binding.share.isClickable = enable
        binding.like.isClickable = enable
        binding.delete.isClickable = enable

        binding.like.visibility = View.VISIBLE
        binding.delete.visibility = View.VISIBLE
        binding.ileri.visibility = View.VISIBLE
        binding.geri.visibility = View.VISIBLE
        binding.share.visibility = View.VISIBLE
        // Diğer butonları da bu şekilde ayarlayabilirsiniz.
    }



    private fun updateLikeButtonIcon(index: Int) {
        val likeButtonIcon = if (olumlamalar[index].favorite) {
            R.drawable.baseline_favorite
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.like.background = getDrawable(likeButtonIcon)
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

    private fun updateAffirmationText(currentIndex: Int) {
        if (olumlamalar.isNotEmpty()) {
            binding.olumlamalarTextView.text = olumlamalar[currentIndex].affirmation
          //  updateUI()
        } else {
            binding.olumlamalarTextView.text = "Henüz olumlama bulunmamaktadır. + butonuna tıklayarak olumlama ekleyebilirsiniz."
          //  updateUI()
        }
    }

    // SharedPreferences'a kategoriye özgü currentIndex değerini kaydetme
    private fun saveLastPosition(context: Context, currentIndex: Int, kategori: String) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt(kategori, currentIndex)
        editor.apply()
    }

    // SharedPreferences'dan kategoriye özgü currentIndex değerini alma
    private fun getLastPosition(context: Context, kategori: String): Int {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getInt(kategori, 0)
    }


    // Kategori sayfasına gitmek için onClick fonksiyonu
    fun kategori(view: View){
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    // Request kod tanımı
    companion object {
        private const val REQUEST_CODE_ADD_AFFIRMATION = 1
    }
}
