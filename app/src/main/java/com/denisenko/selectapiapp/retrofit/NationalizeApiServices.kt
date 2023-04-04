package com.denisenko.selectapiapp.retrofit

import com.denisenko.selectapiapp.NationalizeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NationalizeApiServices {

    @GET("https://api.nationalize.io/")
    fun getNationalizeName(@Query("name") name: String): Call<NationalizeModel>

    @GET("?")
    fun getNationalizeArrayNames(@Query("name[]")vararg name: String): Call<Array<NationalizeModel>>
}