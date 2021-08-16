package com.example.katia.jetpackapp

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.bindVisivility(visible : Boolean?){
    visibility = if(visible == true) View.VISIBLE else View.GONE
}