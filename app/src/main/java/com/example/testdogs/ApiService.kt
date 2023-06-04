package com.example.testdogs

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {


    @GET("breeds/image/random")
    fun loadImage(): Single<DogImage>
}