package com.example.lastfmapp.data.api

import com.example.lastfmapp.data.api.model.track.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getTracks(@Query("method") method: String,
                          @Query("track") track: String,
                          @Query("limit") limit: Int,
                          @Query("page") page: Int,
                          @Query("api_key") apiKey: String,
                          @Query("format") format: String) : TrackResponse?
}