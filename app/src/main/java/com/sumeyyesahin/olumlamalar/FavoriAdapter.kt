package com.sumeyyesahin.olumlamalar

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel

class FavoriAdapter (private var favoriteAffirmations: List<Olumlamalarlistmodel>) :
    RecyclerView.Adapter<FavoriAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Burada itemView'daki view elemanlarını tanımlayabilirsiniz
        val textViewAffirmation: TextView = itemView.findViewById(R.id.textViewAffirmation)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
        init {
            buttonDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedAffirmation = favoriteAffirmations[position]
                    showDeleteConfirmationDialog(clickedAffirmation)
                }
            }
        }

        private fun showDeleteConfirmationDialog(clickedAffirmation: Olumlamalarlistmodel) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.apply {
                setTitle("Olumlamayı Favorilerden Sil")
                setMessage("Bu olumlamayı favorilerden silmek istediğinizden emin misiniz?")
                setPositiveButton("Evet") { dialog, _ ->
                    // Kullanıcı "Evet"e tıkladığında silme işlemini gerçekleştir
                    deleteAffirmations(clickedAffirmation)
                    dialog.dismiss()
                }
                setNegativeButton("Hayır") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.show()
        }

        private fun deleteAffirmations(clickedAffirmation: Olumlamalarlistmodel) {
            // Favori değeri false olarak ayarla
            clickedAffirmation.favorite = false
            // Veritabanında favori olmayan yap
            DBHelper(itemView.context).updateAffirmationFavStatus(clickedAffirmation)
            // Listeden kaldır
            favoriteAffirmations = favoriteAffirmations.filterNot { it.affirmation == clickedAffirmation.affirmation }
            // Adapterı güncelle
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favori_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = favoriteAffirmations[position]
        // Favori olumlamaların gösterileceği view elemanlarını burada güncelleyin
        holder.textViewAffirmation.text = currentItem.affirmation

    }

    override fun getItemCount() = favoriteAffirmations.size
}