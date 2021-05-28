package com.example.myapplicationviewmodel.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class City(
    val city: String,
    val lat: Double,
    val lon: Double,


) : Parcelable
