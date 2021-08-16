package com.example.katia.jetpackapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Query("select * from book")
    fun getAll(): LiveData<List<BookDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(books: List<BookDatabase>)
}

@Database(entities = [BookDatabase::class], version = 1)
abstract class DatabaseBook : RoomDatabase() {
    abstract val bookDao: BookDao
}


private lateinit var INSTANCE: DatabaseBook

fun getDatabase(context: Context): DatabaseBook {
    synchronized(DatabaseBook::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DatabaseBook::class.java,
                "books"
            ).build()
        }
        return INSTANCE
    }
}