package com.example.football.model.club

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
    val name: String,
    var players: List<Player>
): Parcelable