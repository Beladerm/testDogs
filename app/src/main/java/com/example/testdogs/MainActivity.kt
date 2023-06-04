package com.example.testdogs

import android.os.Bundle
import android.widget.Toast
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
        viewModel?.getIsNetworkError()?.observe(this) {
             if (it) {
                Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        }
        binding.button.setOnClickListener { viewModel?.loadImage() }

    }

}

object Constants {
    const val DOGE_URL = "https://dog.ceo/api/"
//    const val KEY_MESSAGE = "message"
  //  const val KEY_STATUS = "status"
    const val TAG = "MainActivity"
}

