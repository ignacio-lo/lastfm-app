package com.example.lastfmapp.ui.track.viewmodel

import androidx.lifecycle.*
import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.domain.Repo
import com.example.lastfmapp.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrackViewModel(private val repo: Repo) : ViewModel() {

    var page = 1
    var lastSearch = ""
    var trackList = MutableLiveData<Resource<List<Track>>>()

    fun getTracks(trackName: String, nextPage: Boolean) {

        if (nextPage) {
            page += 1
        } else {
            page = 1
            lastSearch = trackName
        }

        viewModelScope.launch {

            val result = repo.getTracks(lastSearch, page)

            result.collect {
                trackList.postValue(it)
            }
        }
    }
}