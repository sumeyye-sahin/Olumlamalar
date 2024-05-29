package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class CategoryTimeAdapter(

    private val context: Context,
    private val items: MutableList<Pair<String, Pair<Int, Int>>>,
    private val language: String,
    private var sharedPreferences: SharedPreferences,

) : RecyclerView.Adapter<CategoryTimeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val currentLanguage = sharedPreferences.getString("language", "en") ?: "en"
        holder.tvCategory.text = getLocalizedCategoryName(item.first, currentLanguage)
        holder.tvTime.text = String.format("%02d:%02d", item.second.first, item.second.second)

        holder.btnDelete.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }


    private fun getLocalizedCategoryName(category: String, language: String): String {
        return when (language) {
            "tr" -> when (category) {
                "General Affirmations" -> context.getString(R.string.general_affirmations)
                "Body Affirmations" -> context.getString(R.string.body_affirmations)
                "Faith Affirmations" -> context.getString(R.string.faith_affirmations)
                "Tough Days Affirmations" -> context.getString(R.string.bad_days_affirmations)
                "Love Affirmations" -> context.getString(R.string.love_affirmations)
                "Self-Worth Affirmations" -> context.getString(R.string.self_value_affirmations)
                "Stress and Anxiety Affirmations" -> context.getString(R.string.stress_affirmations)
                "Positive Thought Affirmations" -> context.getString(R.string.positive_thought_affirmations)
                "Success Affirmations" -> context.getString(R.string.success_affirmations)
                "Personal Development Affirmations" -> context.getString(R.string.personal_development_affirmations)
                "Time Management Affirmations" -> context.getString(R.string.time_management_affirmations)
                "Relationship Affirmations" -> context.getString(R.string.relationship_affirmations)
                "Prayer and Request" -> context.getString(R.string.prayer_affirmations)
                else -> category
            }
            "en" -> when (category) {
                "Genel Olumlamalar" -> context.getString(R.string.general_affirmations)
                "Beden Olumlamaları" -> context.getString(R.string.body_affirmations)
                "İnanç Olumlamaları" -> context.getString(R.string.faith_affirmations)
                "Zor Günler Olumlamaları" -> context.getString(R.string.bad_days_affirmations)
                "Sevgi ve Aşk Olumlamaları" -> context.getString(R.string.love_affirmations)
                "Öz Değer Olumlamaları" -> context.getString(R.string.self_value_affirmations)
                "Stres ve Kaygı Olumlamaları" -> context.getString(R.string.stress_affirmations)
                "Pozitif Düşünce Olumlamaları" -> context.getString(R.string.positive_thought_affirmations)
                "Başarı Olumlamaları" -> context.getString(R.string.success_affirmations)
                "Kişisel Gelişim Olumlamaları" -> context.getString(R.string.personal_development_affirmations)
                "Zaman Yönetimi Olumlamaları" -> context.getString(R.string.time_management_affirmations)
                "İlişki Olumlamaları" ->context.getString(R.string.relationship_affirmations)
                "Dua ve İstek" -> context.getString(R.string.prayer_affirmations)
                else -> category
            }
            else -> category
        }
    }

}
