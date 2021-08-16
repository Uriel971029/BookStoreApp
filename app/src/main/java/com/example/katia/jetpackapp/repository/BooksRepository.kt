package com.example.katia.jetpackapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.katia.jetpackapp.database.DatabaseBook
import com.example.katia.jetpackapp.database.asDomainModel
import com.example.katia.jetpackapp.domain.Book
import com.example.katia.jetpackapp.network.Service.RetrofitSingleton
import com.example.katia.jetpackapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository(val database: DatabaseBook) {

    val books: LiveData<List<Book>> = Transformations.map(database.bookDao.getAll()) {
        it.asDomainModel()
    }

    suspend fun refreshBooks() {
        withContext(Dispatchers.IO) {
            val collectionBooks = RetrofitSingleton.getMethods.getBookList(2)
            database.bookDao.insertAll(collectionBooks.asDatabaseModel())
        }
    }

}