package com.example.lastfmapp.data.api.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tracks")
data class Track(

    @PrimaryKey
    @SerializedName("name")
    val name: String = "",

    @SerializedName("artist")
    var artist: String = "",

    @SerializedName("url")
    var url: String = "",

    @SerializedName("streamable")
    var streamable: String = "",

    @SerializedName("listeners")
    var listeners: String = "",

    @SerializedName("mbid")
    var mbid: String = ""
)