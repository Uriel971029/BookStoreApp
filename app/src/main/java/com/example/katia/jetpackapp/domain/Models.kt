package com.example.katia.jetpackapp.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    val id: Int,
    val title: String,
    val descripcion: String,
    val genr: Int,
    val url: String
)
