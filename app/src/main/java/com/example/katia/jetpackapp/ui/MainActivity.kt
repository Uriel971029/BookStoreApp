package com.example.katia.jetpackapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.katia.jetpackapp.R
import com.example.katia.jetpackapp.databinding.MainActivityBinding
import com.example.katia.jetpackapp.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(
            this,
            MainActivityViewModel.Factory(this.application)
        )
            .get(
                MainActivityViewModel::class.java
            )
    }
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isLogged.observe(this, Observer {
            if (it) {
                val homeIntent = Intent(this, HomeActivity::class.java)
                val args = bundleOf("user" to binding.txtUser.text.toString())
                homeIntent.putExtras(args)
                startActivity(homeIntent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }

        })

        setListeners()
    }

    fun setListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                viewModel?.onButtonClicked(txtUser.text.toString(), txtPassword.text.toString())
            }
        }
    }
}