package com.example.football.model.club

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val name: String,
    val position: String,
    val nationality: String
): Parcelable {
}