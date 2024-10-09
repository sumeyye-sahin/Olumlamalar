package com.sumeyyesahin.olumlamalar.model

class OlumlamalarListModel(
    var id: Int,
    var affirmation: String,
    var category: String,
    var favorite: Boolean = false,
    var language: String
)
