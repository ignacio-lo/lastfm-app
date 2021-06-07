package com.example.lastfmapp.data

import androidx.room.withTransaction
import com.example.lastfmapp.BuildConfig
import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.data.local.AppDatabase
import com.example.lastfmapp.utils.Resource
import com.example.lastfmapp.utils.RetrofitClient
import com.example.lastfmapp.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

class DataSourceImpl(private val appDatabase: AppDatabase): DataSource {

    private val trackDao = appDatabase.trackDao()

    override suspend fun getTracks(trackName: String, page: Int): Resource<ArrayList<Track>> {
        return Resource.Success(RetrofitClient.apiService.getTracks(methodNameTrackSearch, trackName, limitTracks, page, BuildConfig.API_KEY, format)?.results?.trackData?.list ?: arrayListOf())
    }

    override fun getTracksFromDb(): Flow<List<Track>> {
        return appDatabase.trackDao().getTracks()
    }

    override suspend fun insertTracksToDb(tracks: ArrayList<Track>) {
        trackDao.insertTrack(tracks.toList())
    }

    override suspend fun deleteTracksFromDb() {
        trackDao.deleteAllTracks()
    }

    override suspend fun getTracksWithCache(trackName: String, page: Int) = networkBoundResource(
        query = {
            getTracksFromDb()
        },
        fetch = {
            getTracks(trackName, page)
        },
        saveFetchResult = { tracks ->
            appDatabase.withTransaction {
                deleteTracksFromDb()
                when (tracks) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        insertTracksToDb(tracks.data!!)
                    }
                    is Resource.Failure -> {

                    }
                }
            }
        }
    )

    companion object {
        val methodNameTrackSearch = "track.search"
        val limitTracks = 10
        val format = "json"
    }
}