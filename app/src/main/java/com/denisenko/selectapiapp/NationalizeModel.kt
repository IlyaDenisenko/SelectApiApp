package com.denisenko.selectapiapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NationalizeModel(val country: Array<Country>, val name: String) : Parcelable


@Parcelize
data class Country (val country_id: String,
            val probability: Float) : Parcelable
