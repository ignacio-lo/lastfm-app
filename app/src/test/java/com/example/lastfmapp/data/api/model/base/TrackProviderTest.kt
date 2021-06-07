package com.example.lastfmapp.data.api.model.base

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrackProviderTest {

    @Test
    fun validTrackProvider() {
        val result = TrackProvider.randomTracks()

        assertThat(result).isNotEqualTo("")
    }
}