package com.example.katia.jetpackapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.katia.jetpackapp.domain.Book


@Entity(tableName = "book")
data class BookDatabase(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val genr: Int,
    val url: String
)


/**
 * Function invoke to parse the list of database books object to the domain model object
 */
fun List<BookDatabase>.asDomainModel(): List<Book> {
    return map {
        Book(
            id = it.id,
            title = it.title,
            descripcion = it.description,
            genr = it.genr,
            url = it.url
        )
    }
}


enum class Genr {
    TERROR, SUSPENSO, AMOR, ACCION
}