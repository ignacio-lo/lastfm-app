package com.example.lastfmapp.data.api.model.base

class TrackProvider {

    companion object {
        fun randomTracks(): String {
            val position = (0..7).random()
            return tracks[position]
        }

        private val tracks = arrayListOf(
            "The Strokes",
            "Arctic Monkeys",
            "Bob Marley",
            "The Rolling Stones",
            "Anderson .Paak",
            "Travis Scott",
            "Daft Punk",
            "The Ramones"
        )
    }
}