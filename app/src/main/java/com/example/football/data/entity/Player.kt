package com.example.football.data.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "players_table")
@Parcelize
class Player : Parcelable {
    @PrimaryKey
    var name: String = ""
    var position: String = ""
    var nationality: String = ""
    var club: String = ""
    var favourite: Boolean = false
    @Ignore
    var icon: Bitmap? = null
}