package com.example.lastfmapp.data.api.model.track

import com.google.gson.annotations.SerializedName

data class TrackResponse (
    @SerializedName("results")
    val results: TrackMatches?
)

data class TrackMatches(
    @SerializedName("trackmatches")
    val trackData: TrackData?
)

data class TrackData(
    @SerializedName("track")
    val list: ArrayList<Track>
)