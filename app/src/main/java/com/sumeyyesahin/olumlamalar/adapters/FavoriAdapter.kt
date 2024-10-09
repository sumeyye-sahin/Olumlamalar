package com.sumeyyesahin.olumlamalar.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.FavoriItemBinding
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.model.OlumlamalarListModel

class FavoriAdapter(private var favoriteAffirmations: List<OlumlamalarListModel>, private val userLanguage: String) :
    RecyclerView.Adapter<FavoriAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: FavoriItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedAffirmation = favoriteAffirmations[position]
                    showDeleteConfirmationDialog(clickedAffirmation)
                }
            }
        }

        private fun showDeleteConfirmationDialog(clickedAffirmation: OlumlamalarListModel) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.apply {
                setTitle(R.string.favories_not_title)
                setMessage(R.string.favories_not)
                setPositiveButton(R.string.yes) { dialog, _ ->
                    deleteAffirmations(clickedAffirmation)
                    dialog.dismiss()
                }
                setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.show()
        }

        private fun deleteAffirmations(clickedAffirmation: OlumlamalarListModel) {
            clickedAffirmation.favorite = false
            DBHelper(itemView.context).updateAffirmationFavStatus(clickedAffirmation)
            favoriteAffirmations = favoriteAffirmations.filterNot { it.affirmation == clickedAffirmation.affirmation && it.language == clickedAffirmation.language }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favori_item, parent, false)
        return FavoriteViewHolder(FavoriItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = favoriteAffirmations[position]
        holder.binding.textViewAffirmation.text = currentItem.affirmation
    }

    override fun getItemCount() = favoriteAffirmations.size
}
