package com.example.katia.jetpackapp.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.katia.jetpackapp.R

class MainFragment : Fragment() {

    private lateinit var loader: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val safeArgs: MainFragmentArgs by navArgs()
        val user = safeArgs.user
        view.findViewById<TextView>(R.id.message).text = "Bienvenido $user"
        loader = view.findViewById<TextView>(R.id.loading)
        setTimer()
    }

    private fun setTimer() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                var loading = loader.text.toString()
                loading += " . "
                loader.text = loading
            }

            override fun onFinish() {
                findNavController().navigate(R.id.next_action)
            }

        }.start()
    }
}