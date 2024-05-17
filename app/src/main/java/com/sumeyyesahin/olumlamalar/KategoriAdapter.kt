package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class KategoriAdapter(private val kategoriListesi: List<String>) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    inner class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewKategoriAdi: TextView = itemView.findViewById(R.id.tvCategoryLabel)
        val imageViewKategoriFoto: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        val cardView: View = itemView.findViewById(R.id.touchcard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return KategoriViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val currentKategori = kategoriListesi[position]
        // Kategori adını değiştirme
        val displayedKategori = when (currentKategori) {
            "Genel Olumlamalar" -> "Genel"
            "Beden Olumlamaları" -> "Beden"
            "İnanç Olumlamaları" -> "İnanç"
            "Zor Günler Olumlamaları" -> "Zor Günler"
            "Sevgi ve Aşk Olumlamaları" -> "Sevgi ve Aşk"
            "Öz Değer Olumlamaları" -> "Öz Değer"
            "Stres ve Kaygı Olumlamaları" -> "Stres ve Kaygı"
            "Pozitif Düşünce Olumlamaları" -> "Pozitif Düşünce"
            "Başarı Olumlamaları" -> "Başarı"
            "Kişisel Gelişim Olumlamaları" -> "Kişisel Gelişim"
            "Zaman Yönetimi Olumlamaları" -> "Zaman Yönetimi"
            "İlişki Olumlamaları" -> "İlişki"
            "Dua ve İstek Olumlamaları" -> "Dua ve İstek"
            "Favori Olumlamalarım" -> "Favorilerim"
            "Kendi Olumlamalarım" -> "Kendi Olumlamalarım"
            else -> currentKategori // Varsayılan değer, eğer kategori bulunamazsa
        }

        holder.textViewKategoriAdi.text = displayedKategori
        // Kategoriye göre drawable kaynağını atan
        val drawableResourceId = getDrawableResourceId(holder.itemView.context, currentKategori)
        holder.imageViewKategoriFoto.setImageResource(drawableResourceId)


        // intent


        if (currentKategori == "Favori Olumlamalarım") {
            holder.cardView.setOnClickListener {
                val context = holder.textViewKategoriAdi.context
                val intent = Intent(context, FavoriesActivity::class.java)
                intent.putExtra("kategori", currentKategori)
                context.startActivity(intent)
            }
        } else {
            holder.cardView.setOnClickListener {
                val context = holder.textViewKategoriAdi.context
                val intent = Intent(context, AffirmationMainPageActivity::class.java)
                intent.putExtra("kategori", currentKategori)
                context.startActivity(intent)
            }
        }

    }
    private fun isCategoryEmpty(context: Context, kategori: String): Boolean {
        // Burada DBHelper'ınızdan kategorinin içeriğini kontrol eden bir sorgu yapılabilir
        val dbHelper = DBHelper(context)
        val count = dbHelper.getAffirmationCountByCategory(kategori)
        return count == 0
    }
    override fun getItemCount() = kategoriListesi.size

    // Kategoriye göre drawable kaynağını bulan yardımcı fonksiyon
    private fun getDrawableResourceId(context: Context, kategori: String): Int {
        return when (kategori) {
            "Genel Olumlamalar" -> R.drawable.genel_icon
            "Beden Olumlamaları" -> R.drawable.beden_icon
            "İnanç Olumlamaları" -> R.drawable.dua_icon
            "Zor Günler Olumlamaları" -> R.drawable.bad_day_icon
            "Sevgi ve Aşk Olumlamaları" -> R.drawable.love_icon
            "Öz Değer Olumlamaları" -> R.drawable.ozdeger_icon
            "Stres ve Kaygı Olumlamaları" -> R.drawable.stres2_icon
            "Pozitif Düşünce Olumlamaları" -> R.drawable.pozitif2_icon
            "Başarı Olumlamaları" -> R.drawable.basari_icon
            "Kişisel Gelişim Olumlamaları" -> R.drawable.kisiselgelisim_icon
            "Zaman Yönetimi Olumlamaları" -> R.drawable.zaman_icon
            "İlişki Olumlamaları" -> R.drawable.iliski2_icon
            "Dua ve İstek Olumlamaları" -> R.drawable.dua2_icon
            "Favori Olumlamalarım" -> R.drawable.favori_icon
            else -> R.drawable.kendi2_icon// Varsayılan drawable, eğer kategori bulunamazsa
        }
    }
}
