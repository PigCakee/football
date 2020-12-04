package com.example.football.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.football.databinding.ItemPositionBinding
import com.example.football.model.club.Club
import com.example.football.model.club.Player
import com.example.football.ui.positions.PositionsViewModel
import com.example.football.utils.view.positionsList

class PositionsAdapter(
    private val model: PositionsViewModel,
    private var data: List<Club> = listOf()
) : RecyclerView.Adapter<PositionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = model
        with(holder.binding) {
            filterPlayers(position)
        }
    }

    private fun ItemPositionBinding.filterPlayers(position: Int) {
        var count = 0
        data.forEach { club ->
            val matchingPlayers: List<Player> =
                club.players.filter { player -> player.position == positionsList[position] }
            count += matchingPlayers.size
        }
        this.position.text = positionsList[position]
        this.players.text = count.toString()
        this.pos = positionsList[position]
    }

    override fun getItemCount() = positionsList.size

    fun setData(clubs: List<Club>) {
        data = clubs
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemPositionBinding) : RecyclerView.ViewHolder(binding.root)
}
