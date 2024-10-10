package com.sumeyyesahin.olumlamalar.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.activities.AffirmationMainPageActivity
import com.sumeyyesahin.olumlamalar.activities.CategoryActivity
import com.sumeyyesahin.olumlamalar.activities.FavoritesActivity
import com.sumeyyesahin.olumlamalar.databinding.CategoryItemBinding

class CategoryAdapter(private val kategoriListesi: List<String>, private val userLanguage: String) : RecyclerView.Adapter<CategoryAdapter.KategoriViewHolder>() {

    inner class KategoriViewHolder(public val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return KategoriViewHolder(CategoryItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val currentKategori = kategoriListesi[position]
        val context = holder.itemView.context

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
            else -> currentKategori
        }

        holder.binding.tvCategoryLabel.text = displayedKategori
        val drawableResourceId = getDrawableResourceId(context, currentKategori)
        holder.binding.ivCategoryIcon.setImageResource(drawableResourceId)

        if (currentKategori == context.getString(R.string.favorite_affirmations)) {
            holder.binding.touchcard.setOnClickListener {
                holder.binding.touchcard.alpha = 0.5f
                val intent = Intent(context, FavoritesActivity::class.java)
                intent.putExtra("kategori", currentKategori)
                context.startActivity(intent)
                if (context is CategoryActivity) {
                    context.finish()
                }
            }
        } else {
            holder.binding.touchcard.setOnClickListener {
                holder.binding.touchcard.alpha = 0.5f
                val intent = Intent(context, AffirmationMainPageActivity::class.java)
                intent.putExtra("kategori", currentKategori)
                context.startActivity(intent)
                if (context is CategoryActivity) {
                    context.finish()
                }
            }
        }
    }


    override fun getItemCount() = kategoriListesi.size

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


