package com.sumeyyesahin.olumlamalar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryTimeAdapter(
    private val items: List<Pair<String, Pair<Int, Int>>>
) : RecyclerView.Adapter<CategoryTimeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvCategory.text = item.first
        holder.tvTime.text = "${item.second.first}:${item.second.second}"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
