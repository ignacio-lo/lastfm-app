package com.example.lastfmapp.ui.track

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lastfmapp.data.DataSourceImpl
import com.example.lastfmapp.data.api.model.base.TrackProvider
import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.data.local.AppDatabase
import com.example.lastfmapp.databinding.FragmentTrackBinding
import com.example.lastfmapp.domain.RepoImpl
import com.example.lastfmapp.ui.adapter.TrackAdapter
import com.example.lastfmapp.ui.track.viewmodel.TrackViewModel
import com.example.lastfmapp.utils.Resource
import com.example.lastfmapp.utils.VMFactory

class TrackFragment : Fragment(), TrackAdapter.TrackActions {

    private val viewModel by viewModels<TrackViewModel> {
        VMFactory(
            RepoImpl(DataSourceImpl(AppDatabase.getDatabase(requireActivity().applicationContext)))
        )
    }

    private var _binding: FragmentTrackBinding? = null
    private val binding get() = _binding!!
    private var lastInformedCount = 0
    private var nextPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTrackBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSortList.setOnClickListener{
            sortList()
        }

        setRecyclerViewOnScrollListener()

        setSearchView()

        loadTracks(TrackProvider.randomTracks())

        viewModel.trackList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                    if (nextPage) {
                        binding.progressBar.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = ConstraintLayout.LayoutParams.UNSET
                        }
                    }
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (result.data.isNullOrEmpty()) {
                        binding.emptyContainer.root.visibility = View.VISIBLE
                        binding.rvTracks.visibility = View.GONE
                    } else {
                        binding.emptyContainer.root.visibility = View.GONE
                        binding.rvTracks.visibility = View.VISIBLE

                        if (!nextPage) {
                            setAdapter(result.data as ArrayList<Track>)
                        } else {
                            addTracks(result.data as ArrayList<Track>)
                        }
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE

                    //If data is not null is because there is data on cache
                    if (!result.data.isNullOrEmpty()) {
                        setAdapter(result.data as ArrayList<Track>)

                        Toast.makeText(requireContext(), "Error getting tracks. Working offline.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun loadTracks(textToSearch: String) {
        nextPage = false
        viewModel.getTracks(textToSearch, nextPage)
    }

    private fun loadNextPage() {
        nextPage = true
        viewModel.getTracks("", nextPage)
    }

    private fun setAdapter(list: ArrayList<Track>) {
        binding.rvTracks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTracks.setHasFixedSize(true)
        binding.rvTracks.adapter = TrackAdapter(requireContext(), list, this)
    }

    private fun addTracks(list: ArrayList<Track>) {
        (binding.rvTracks.adapter as TrackAdapter).addTracks(list)
    }

    private fun sortList() {
        (binding.rvTracks.adapter as TrackAdapter).sortList()
    }

    private fun setRecyclerViewOnScrollListener() {
        binding.rvTracks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition: Int = (binding.rvTracks.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val totalItemCount = recyclerView.layoutManager!!.itemCount

                if (lastInformedCount != totalItemCount && totalItemCount == lastVisibleItemPosition + 1) {
                    loadNextPage()
                    lastInformedCount = recyclerView.layoutManager!!.itemCount
                }
            }
        })
    }

    private fun setSearchView() {
        binding.svSearchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadTracks(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onTrackClicked(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}