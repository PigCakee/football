package com.example.football.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.football.databinding.ItemPlayerBinding
import com.example.football.model.club.Club
import com.example.football.model.club.Player

class ClubPositionsListAdapter(
    private val position: String,
    club: Club
) : RecyclerView.Adapter<ClubPositionsListAdapter.ViewHolder>() {
    private var data: List<Player> = club.players.filter { it.position == position }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = data[position]

        with(holder.binding) {
            this.name.text = player.name
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root)
}