package com.example.lastfmapp.data

import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getTracks(trackName: String, page: Int): Resource<ArrayList<Track>>

    fun getTracksFromDb(): Flow<List<Track>>

    suspend fun insertTracksToDb(tracks: ArrayList<Track>)

    suspend fun deleteTracksFromDb()

    suspend fun getTracksWithCache(trackName: String, page: Int): Flow<Resource<List<Track>>>
}