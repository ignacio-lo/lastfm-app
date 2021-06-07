package com.example.lastfmapp.domain

import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getTracks(trackName: String, page: Int): Flow<Resource<List<Track>>>
}