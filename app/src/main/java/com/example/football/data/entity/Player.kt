package com.example.football.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "players_table")
@Parcelize
data class Player(
    @PrimaryKey
    val name: String,
    val position: String,
    val nationality: String,
    val club: String,
    var favourite: Boolean
): Parcelable