package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KategoriAdapter(private val kategoriListesi: List<String>) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    inner class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewKategoriAdi: TextView = itemView.findViewById(R.id.textViewcategory)
        val imageViewKategoriFoto: ImageView = itemView.findViewById(R.id.imageViewcategory)
        val cardView: View = itemView.findViewById(R.id.touchcard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return KategoriViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val currentKategori = kategoriListesi[position]
        holder.textViewKategoriAdi.text = currentKategori
       // println(currentKategori)

        // Kategoriye göre drawable kaynağını atan
        val drawableResourceId = getDrawableResourceId(holder.itemView.context, currentKategori)
        holder.imageViewKategoriFoto.setImageResource(drawableResourceId)


        // intent
        holder.cardView.setOnClickListener {
            val context = holder.textViewKategoriAdi.context
            val intent = Intent(context, AffirmationMainPageActivity::class.java)
            intent.putExtra("kategori", currentKategori)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = kategoriListesi.size

    // Kategoriye göre drawable kaynağını bulan yardımcı fonksiyon
    private fun getDrawableResourceId(context: Context, kategori: String): Int {
        return when (kategori) {
            "Genel Olumlamalar" -> R.drawable.genel1
            "Beden Olumlamaları" -> R.drawable.beden2
            "İnanç Olumlamaları" -> R.drawable.inanc3
            "Zor Günler Olumlamaları" -> R.drawable.zor4
            "Sevgi ve Aşk Olumlamaları" -> R.drawable.askvesevgi5
            "Öz Değer Olumlamaları" -> R.drawable.ozdeger6
            "Stres ve Kaygı Olumlamaları" -> R.drawable.stres7
            "Pozitif Düşünce Olumlamaları" -> R.drawable.pozitif8
            "Başarı Olumlamaları" -> R.drawable.basari9
            "Kişisel Gelişim Olumlamaları" -> R.drawable.kisiselgelisim10
            "Zaman Yönetimi Olumlamaları" -> R.drawable.zaman11
            "İlişki Olumlamaları" -> R.drawable.iliskiler12
            "Dua ve İstek Olumlamaları" -> R.drawable.duaveistek13
            else -> R.drawable.kendi // Varsayılan drawable, eğer kategori bulunamazsa
        }
    }
}
