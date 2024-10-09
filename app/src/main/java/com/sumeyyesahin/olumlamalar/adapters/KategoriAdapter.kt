package com.sumeyyesahin.olumlamalar.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.activities.AffirmationMainPageActivity
import com.sumeyyesahin.olumlamalar.activities.CategoryActivity
import com.sumeyyesahin.olumlamalar.activities.FavoriesActivity
import com.sumeyyesahin.olumlamalar.helpers.DBHelper

class KategoriAdapter(private val kategoriListesi: List<String>, private val userLanguage: String) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

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
        val context = holder.itemView.context

        // Kategori adını değiştirme
        val displayedKategori = when (currentKategori) {
            context.getString(R.string.general_affirmations) -> context.getString(R.string.general)
            context.getString(R.string.body_affirmations) -> context.getString(R.string.body)
            context.getString(R.string.faith_affirmations) -> context.getString(R.string.faith)
            context.getString(R.string.bad_days_affirmations) -> context.getString(R.string.bad_days)
            context.getString(R.string.love_affirmations) -> context.getString(R.string.love)
            context.getString(R.string.self_value_affirmations) -> context.getString(R.string.self_value)
            context.getString(R.string.stress_affirmations) -> context.getString(R.string.stress)
            context.getString(R.string.positive_thought_affirmations) -> context.getString(R.string.positive_thought)
            context.getString(R.string.success_affirmations) -> context.getString(R.string.success)
            context.getString(R.string.personal_development_affirmations) -> context.getString(R.string.personal_development)
            context.getString(R.string.time_management_affirmations) -> context.getString(R.string.time_management)
            context.getString(R.string.relationship_affirmations) -> context.getString(R.string.relationship)
            context.getString(R.string.prayer_affirmations) -> context.getString(R.string.prayer)
            context.getString(R.string.favorite_affirmations) -> context.getString(R.string.favorites)
            context.getString(R.string.own_affirmations) -> context.getString(R.string.own_affirmations)
            else -> currentKategori // Varsayılan değer, eğer kategori bulunamazsa
        }

        holder.textViewKategoriAdi.text = displayedKategori
        // Kategoriye göre drawable kaynağını atan
        val drawableResourceId = getDrawableResourceId(context, currentKategori)
        holder.imageViewKategoriFoto.setImageResource(drawableResourceId)

        if (currentKategori == context.getString(R.string.favorite_affirmations)) {
            holder.cardView.setOnClickListener {
                holder.cardView.alpha = 0.5f
                val intent = Intent(context, FavoriesActivity::class.java)
                intent.putExtra("kategori", currentKategori)
                context.startActivity(intent)
                if (context is CategoryActivity) {
                    context.finish()
                }
            }
        } else {
            holder.cardView.setOnClickListener {
                holder.cardView.alpha = 0.5f
                val intent = Intent(context, AffirmationMainPageActivity::class.java)
                intent.putExtra("kategori", currentKategori)
                context.startActivity(intent)
                if (context is CategoryActivity) {
                    context.finish()
                }
            }
        }
    }

    private fun isCategoryEmpty(context: Context, kategori: String): Boolean {
        // Burada DBHelper'ınızdan kategorinin içeriğini kontrol eden bir sorgu yapılabilir
        val dbHelper = DBHelper(context)
        val count = dbHelper.getAffirmationCountByCategoryAndLanguage(kategori, userLanguage)
        return count == 0
    }

    override fun getItemCount() = kategoriListesi.size

    // Kategoriye göre drawable kaynağını bulan yardımcı fonksiyon
    private fun getDrawableResourceId(context: Context, kategori: String): Int {
        return when (kategori) {
            context.getString(R.string.general_affirmations) -> R.drawable.genel_icon
            context.getString(R.string.body_affirmations) -> R.drawable.beden_icon
            context.getString(R.string.faith_affirmations) -> R.drawable.dua_icon
            context.getString(R.string.bad_days_affirmations) -> R.drawable.bad_day_icon
            context.getString(R.string.love_affirmations) -> R.drawable.love_icon
            context.getString(R.string.self_value_affirmations) -> R.drawable.ozdeger_icon
            context.getString(R.string.stress_affirmations) -> R.drawable.stres2_icon
            context.getString(R.string.positive_thought_affirmations) -> R.drawable.pozitif2_icon
            context.getString(R.string.success_affirmations) -> R.drawable.basari_icon
            context.getString(R.string.personal_development_affirmations) -> R.drawable.kisiselgelisim_icon
            context.getString(R.string.time_management_affirmations) -> R.drawable.zaman_icon
            context.getString(R.string.relationship_affirmations) -> R.drawable.iliski2_icon
            context.getString(R.string.prayer_affirmations) -> R.drawable.dua2_icon
            context.getString(R.string.favorite_affirmations) -> R.drawable.favori_icon
            else -> R.drawable.kendi2_icon // Varsayılan drawable, eğer kategori bulunamazsa
        }
    }
}


