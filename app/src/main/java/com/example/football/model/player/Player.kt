package com.example.football.model.player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val name: String,
    val position: String,
    val nationality: String,
    val club: String
): Parcelable