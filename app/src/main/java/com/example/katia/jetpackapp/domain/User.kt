package com.example.katia.jetpackapp.domain

import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier

@JsonClass(generateAdapter = true)
data class User(
    var username : String?,
    var password : String?
)
