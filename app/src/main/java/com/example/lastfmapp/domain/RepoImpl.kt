package com.example.lastfmapp.domain

import com.example.lastfmapp.data.DataSource
import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.utils.Resource
import kotlinx.coroutines.flow.Flow

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getTracks(trackName: String, page: Int): Flow<Resource<List<Track>>> {
        return dataSource.getTracksWithCache(trackName, page)
    }
}