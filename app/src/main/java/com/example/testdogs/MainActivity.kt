package com.example.testdogs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testdogs.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL

const val DOGE_URL = "https://dog.ceo/api/breeds/image/random"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding
            .inflate(layoutInflater)
            .also { setContentView(it.root) }
        loadImage()

    }

    private fun loadImage() {
        Thread {
            try {
                val url = DOGE_URL    // адрес запроса
                val httpURLConnection =
                    URL(url).openConnection() as HttpURLConnection  // установка соединения и приведение к HttpURLConnection
                val inputStream =
                    httpURLConnection.inputStream     // создание потока ввода из соединения с сервером ( считывание побайтовое)
                val inputStreamReader =
                    inputStream.reader()        // создание потока ввода из потока ввода ( считывание посимвольное)
                val bufferedReader =
                    inputStreamReader.buffered()   // создание буферизированного потока ввода из потока ввода ( считывание построчное)
                // или вместо создания 4 переменных можно сделать так:  val bufferedReader = URL(url).openStream().bufferedReader() // создание буферизированного потока ввода в одной цепочке вызовов
                val result =
                    bufferedReader.readLine()             // чтение строки из буферизированного потока ввода
                Log.d("MainActivity", "Result: $result")
                val dogImage = DogImage(result, "success")

            } catch (e: Exception) {
                Log.d("MainActivity", "Error: $e")
            }
        }.start()

    }

}

