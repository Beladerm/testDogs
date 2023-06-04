package com.example.testdogs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testdogs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding
            .inflate(layoutInflater)
            .also { setContentView(it.root) }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel?.loadImage()
        viewModel?.getDogImage()?.observe(this) {
            Glide.with(this)
                .load(it.message)
                .into(binding.imageView)

        }
        viewModel?.getIsLoading()?.observe(this) {
            binding.progressBar.visibility = if (it) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
        }
        binding.button.setOnClickListener { viewModel?.loadImage() }

    }

}

object Constants {
    const val DOGE_URL = "https://dog.ceo/api/breeds/image/random"
    const val KEY_MESSAGE = "message"
    const val KEY_STATUS = "status"
    const val TAG = "MainActivity"
}

