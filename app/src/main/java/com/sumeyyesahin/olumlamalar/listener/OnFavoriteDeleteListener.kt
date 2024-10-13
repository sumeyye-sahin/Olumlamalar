package com.sumeyyesahin.olumlamalar.listener

import com.sumeyyesahin.olumlamalar.model.AffirmationsListModel

interface OnFavoriteDeleteListener {
    fun onDeleteFavorite(affirmation: AffirmationsListModel)
}
