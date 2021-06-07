package com.example.lastfmapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lastfmapp.data.api.model.track.Track
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: TrackDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        dao = db.trackDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAnGetTracks() = runBlocking {
        val track = Track("Glitter", "Tyler, The Creator", "fakerul.com", "yes", "15986432", "abc123")

        dao.insertTrack(listOf(track))

        val tracks = dao.getTracksList()

        assertThat(tracks.contains(track)).isTrue()
    }
}