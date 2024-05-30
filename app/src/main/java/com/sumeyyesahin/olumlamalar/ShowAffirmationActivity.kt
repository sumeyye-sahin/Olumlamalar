package com.sumeyyesahin.olumlamalar

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import com.sumeyyesahin.olumlamalar.databinding.ActivityShowAffirmationBinding
import java.io.File
import java.io.FileOutputStream

class ShowAffirmationActivity : Activity() {

    private lateinit var binding: ActivityShowAffirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAffirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val message = intent.getStringExtra("affirmation_message")
        binding.olumlamalarTextView.text = message

        binding.buttonkategori.setOnClickListener {
           intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.quitbutton.setOnClickListener {
            finish()
        }

        binding.share.setOnClickListener {
            // Olumlamayı paylaşmak
            val bitmap = takeScreenshot(binding.affirmationBackground, binding.olumlamalarTextView)

            // Geçici dosya oluşturma
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "shared_image.png")
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()

            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)

            // Share intent
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(shareIntent, "Share Content"))

            // Paylaşımdan sonra dosyayı silme
            shareIntent.resolveActivity(packageManager)?.also {
                file.deleteOnExit()
            }
        }


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



}
