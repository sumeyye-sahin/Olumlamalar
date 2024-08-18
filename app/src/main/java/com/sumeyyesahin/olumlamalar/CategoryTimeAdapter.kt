package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryTimeAdapter(
    private val context: Context,
    private var items: MutableList<Pair<String, Pair<Int, Int>>>,
    private val language: String,
    private val sharedPreferences: SharedPreferences,
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
        val category = item.first
        val time = item.second

        val currentLanguage = sharedPreferences.getString("language", "en") ?: "en"
        holder.tvCategory.text = getLocalizedCategoryName(category, currentLanguage)
        holder.tvTime.text = String.format("%02d:%02d", time.first, time.second)

        holder.btnDelete.setOnClickListener {
            val itemToRemove = items[position]
            items.removeAt(position)
            notifyItemRemoved(position)
            removeItemFromSharedPreferences(itemToRemove)

            // Bildirimi iptal et
            NotificationReceiver.cancelNotification(context, itemToRemove.first, itemToRemove.second.first, itemToRemove.second.second)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<Pair<String, Pair<Int, Int>>>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun removeItemFromSharedPreferences(item: Pair<String, Pair<Int, Int>>) {
        val serializedData = sharedPreferences.getString("categories_and_times", "")
        Log.d("CategoryTimeAdapter", "Current serialized data: $serializedData")
        if (!serializedData.isNullOrEmpty()) {
            val items = serializedData.split(";").toMutableList()
            val itemToRemove = "${item.first},${item.second.first},${item.second.second}"
            items.remove(itemToRemove)
            val updatedData = items.joinToString(";")
            Log.d("CategoryTimeAdapter", "Updated data: $updatedData")
            val editor = sharedPreferences.edit()
            editor.putString("categories_and_times", updatedData)
            editor.apply()
            Log.d("CategoryTimeAdapter", "SharedPreferences updated")
        }
    }

    private fun getLocalizedCategoryName(category: String, language: String): String {
        return LocaleHelper.getLocalizedCategoryName(context, category, language)
    }
}
