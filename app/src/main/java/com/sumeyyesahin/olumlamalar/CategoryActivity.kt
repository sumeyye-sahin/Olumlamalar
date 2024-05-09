package com.sumeyyesahin.olumlamalar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyesahin.olumlamalar.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val db = DBHelper(this)
        val kategoriListesi = db.getAllCategories()
        val adapter = KategoriAdapter(kategoriListesi)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        //recyclerView.layoutManager = LinearLayoutManager(this) // this, Activity içinde olmalı
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

    }


        /*        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.categories)
                // binding.deneme.background = BitmapDrawable(resources, originalBitmap)

                val screenHight = resources.displayMetrics.heightPixels.toInt()
                val screenWidth = resources.displayMetrics.widthPixels.toInt()

                if(originalBitmap.height>screenHight){

                    val height= ((originalBitmap.height.toInt()-screenHight.toInt())/4)
                    val croppedimage1= cropBottomOfImage(originalBitmap, height)
                    val croppedimage2= croppedimage1?.let { cropTopOfImage(it, height) }
                    binding.deneme.background = BitmapDrawable(resources, croppedimage2)
                }
                else if(originalBitmap.width>screenWidth){
                    val width= ((originalBitmap.width.toInt()-screenWidth.toInt())/4)
                    val croppedimage1= cropRightOfImage(originalBitmap, width)
                    val croppedimage2= croppedimage1?.let { cropLeftOfImage(it, width) }
                    binding.deneme.background = BitmapDrawable(resources, croppedimage2)
                }
                else{
                    binding.deneme.background = BitmapDrawable(resources, originalBitmap)
                }


            }

            fun cropTopOfImage(bitmap: Bitmap, cropHeight: Int): Bitmap? {


                // Resmin üst kısmını kırp
                val croppedBitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    cropHeight,
                    bitmap.width,
                    bitmap.height - cropHeight
                )

                return croppedBitmap
            }

            fun cropBottomOfImage(bitmap: Bitmap, cropHeight: Int): Bitmap? {


                // Resmin alt kısmını kırp
                val croppedBitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height - cropHeight
                )

                return croppedBitmap
            }

            fun cropRightOfImage(bitmap: Bitmap, cropWidth: Int): Bitmap? {
                // Resmin sağ tarafını kırp
                val croppedBitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width - cropWidth,
                    bitmap.height
                )

                return croppedBitmap
            }


            fun cropLeftOfImage(bitmap: Bitmap, cropWidth: Int): Bitmap? {

                // Resmin sol tarafını kırp
                val croppedBitmap = Bitmap.createBitmap(
                    bitmap,
                    cropWidth,
                    0,
                    bitmap.width - cropWidth,
                    bitmap.height
                )

                return croppedBitmap
            }*/


}