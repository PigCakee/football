package com.example.football.model.club

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clubs(
    val clubs: ArrayList<Club>
): Parcelable