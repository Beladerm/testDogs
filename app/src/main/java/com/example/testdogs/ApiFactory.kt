package com.example.testdogs

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

        companion object {

            private var apiService: ApiService? = null

            fun getApiService(): ApiService {
                 if (apiService == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(Constants.DOGE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
                     apiService = retrofit.create(ApiService::class.java)
                 }
                return apiService!!
            }
        }
}