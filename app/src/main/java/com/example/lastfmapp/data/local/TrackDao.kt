package com.example.lastfmapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lastfmapp.data.api.model.track.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks")
    fun getTracks(): Flow<List<Track>>

    @Query("SELECT * FROM tracks")
    suspend fun getTracksList(): List<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(tracks: List<Track>)

    @Query("DELETE FROM tracks")
    suspend fun deleteAllTracks()
}