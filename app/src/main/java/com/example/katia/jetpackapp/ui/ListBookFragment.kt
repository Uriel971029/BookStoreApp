package com.example.katia.jetpackapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.katia.jetpackapp.R
import com.example.katia.jetpackapp.databinding.ListBookFragmentBinding
import com.example.katia.jetpackapp.domain.Book
import com.example.katia.jetpackapp.viewmodel.ListBookViewModel

class ListBookFragment : Fragment() {

    private var bookAdapter: ListBookAdapter? = null
    private val listViewModel: ListBookViewModel by lazy {

        val activity = requireNotNull(this.activity) {
            "You can only access to viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            ListBookViewModel.Factory(activity.application)
        ).get(ListBookViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bookAdapter = ListBookAdapter(ListBookAdapter.BookClick{
            //click event
            bookAdapter?.deleteItem(it)
        })
        val binding: ListBookFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_book_fragment,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = listViewModel

        binding.root.findViewById<RecyclerView>(R.id.rv_list_book).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel.books.observe(viewLifecycleOwner, { books ->
            books?.apply {
                //update list of books in the adpater
                bookAdapter?.books = books.toMutableList()
            }
        })

        listViewModel.errorNetwork.observe(viewLifecycleOwner, {
            if (it)
                Toast
                    .makeText(context, "Error de red, revise su conexión", Toast.LENGTH_SHORT)
                    .show()
        })
    }
}