package com.example.football.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.ItemPositionBinding
import com.example.football.model.club.Club
import com.example.football.model.club.Player
import com.example.football.ui.positions.PositionsViewModel

class PositionsAdapter(
    private val model: PositionsViewModel,
    context: Context,
    private var data: List<Club> = listOf()
) : RecyclerView.Adapter<PositionsAdapter.ViewHolder>() {

    private val positions = arrayOf(
        context.getString(R.string.goalkeeper),
        context.getString(R.string.forward),
        context.getString(R.string.middle),
        context.getString(R.string.defence)
    )

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
                club.players.filter { player -> player.position == positions[position] }
            count += matchingPlayers.size
        }
        this.position.text = positions[position]
        this.players.text = count.toString()
        this.pos = positions[position]
    }

    override fun getItemCount() = positions.size

    fun setData(clubs: List<Club>) {
        data = clubs
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemPositionBinding) : RecyclerView.ViewHolder(binding.root)
}
