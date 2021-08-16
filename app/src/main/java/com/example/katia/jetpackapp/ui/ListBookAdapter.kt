package com.example.katia.jetpackapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.katia.jetpackapp.R
import com.example.katia.jetpackapp.databinding.ListBookItemBinding
import com.example.katia.jetpackapp.domain.Book

class ListBookAdapter(val callback: BookClick) :
    RecyclerView.Adapter<ListBookAdapter.ListBookHolder>() {

     var books: MutableList<Book> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    fun deleteItem(book: Book) {
        if (books.contains(book)) {
            books.remove(book)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBookHolder {

        val withBinging: ListBookItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ListBookHolder.LAYOUT,
            parent,
            false
        )

        return ListBookHolder(withBinging)
    }

    override fun onBindViewHolder(holder: ListBookHolder, position: Int) {
        holder.binding.also {
            it.book = books[position]
            it.bookCallback = callback
        }
    }

    override fun getItemCount(): Int = books.size


    class ListBookHolder(val binding: ListBookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_book_item
        }
    }

    class BookClick(val block: (Book) -> Unit) {

        fun onClick(book: Book) = block(book)

    }

}