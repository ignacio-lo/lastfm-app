package com.example.lastfmapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastfmapp.R
import com.example.lastfmapp.data.api.model.track.Track
import com.example.lastfmapp.databinding.TrackItemBinding
import com.example.lastfmapp.ui.adapter.viewholder.BaseViewHolder
import kotlin.collections.ArrayList

class TrackAdapter(private val context: Context, private var list: ArrayList<Track>, private val listener: TrackActions) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface TrackActions{
        fun onTrackClicked(url: String)
    }

    fun addTracks(data: ArrayList<Track>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun sortList() {
        list = list.reversed() as ArrayList<Track>

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return TrackViewHolder(LayoutInflater.from(context).inflate(R.layout.track_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is TrackViewHolder -> holder.bind(list[position], position)
        }
    }

    inner class TrackViewHolder(itemView: View) : BaseViewHolder<Track>(itemView) {
        private val binding = TrackItemBinding.bind(itemView)

        override fun bind(item: Track, position: Int) {

            Glide.with(context)
                .load(R.drawable.ic_play_red_icon)
                .centerCrop()
                .into(binding.cvImageTrack)

            binding.tvTrackName.text = item.name

            binding.tvArtistName.text = item.artist

            binding.tvListeners.text = "${context.resources.getString(R.string.listeners)} ${item.listeners}"

            itemView.setOnClickListener {
                listener.onTrackClicked(item.url)
            }
        }
    }
}