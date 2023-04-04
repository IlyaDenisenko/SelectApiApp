package com.denisenko.selectapiapp.retrofit

import com.denisenko.selectapiapp.DogModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiServices {

    @GET("breeds/image/random")
    fun getRandomDog(): Call<DogModel>

    @GET("breed/{mBreed}/images/random")
    fun getDogByBreed(@Path("mBreed") mBreed: String): Call<DogModel>
}