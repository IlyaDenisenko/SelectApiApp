package com.denisenko.selectapiapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    companion object{
        fun getDogApi(): DogApiServices{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(DogApiServices::class.java)
        }

        fun getNationalizeApi(): NationalizeApiServices{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nationalize.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NationalizeApiServices::class.java)
        }
    }
}
