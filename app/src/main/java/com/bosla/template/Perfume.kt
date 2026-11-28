package com.bosla.template

data class Perfume(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val description: String,
    var isFavorite: Boolean = false,
    var isInCart: Boolean = false
)
