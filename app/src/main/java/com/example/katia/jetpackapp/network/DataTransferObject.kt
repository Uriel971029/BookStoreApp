package com.example.katia.jetpackapp.network

import com.example.katia.jetpackapp.database.BookDatabase
import com.example.katia.jetpackapp.domain.Book
import com.example.katia.jetpackapp.domain.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkResponse(
    val code: Int,
    val user: User?,
    val message: String?,
    val isLogged: Boolean
)


/**
 * parse the first network object
 * books : []
 */
@JsonClass(generateAdapter = true)
data class NetworkBooksContainer(val books: List<Book>)

/**
 * Function to parse from container to list of database type model
 */
fun NetworkBooksContainer.asDatabaseModel(): List<BookDatabase> {
    return books.map {
        BookDatabase(
            id = it.id,
            title = it.title,
            description = it.descripcion,
            genr = it.genr,
            url = it.url
        )
    }
}
