package com.example.football.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.ItemNationalityBinding
import com.example.football.model.club.Club
import com.example.football.model.club.Player
import com.example.football.ui.nationalities.NationalitiesViewModel

class NationalitiesAdapter(
    private val model: NationalitiesViewModel,
    context: Context,
    private var data: List<Club> = listOf()
) : RecyclerView.Adapter<NationalitiesAdapter.ViewHolder>() {

    private val nationalities = arrayOf(
        context.getString(R.string.saxon),
        context.getString(R.string.english),
        context.getString(R.string.german),
        context.getString(R.string.belarussian),
        context.getString(R.string.polish)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNationalityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = model
        with(holder.binding) {
            filterPlayers(position)
        }
    }

    private fun ItemNationalityBinding.filterPlayers(position: Int) {
        var count = 0
        data.forEach { club ->
            val matchingPlayers: List<Player> =
                club.players.filter { player -> player.nationality == nationalities[position] }
            count += matchingPlayers.size
        }
        this.nationality.text = nationalities[position]
        this.players.text = count.toString()
        this.nation = nationalities[position]
    }

    override fun getItemCount() = nationalities.size

    fun setData(clubs: List<Club>) {
        data = clubs
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemNationalityBinding) : RecyclerView.ViewHolder(binding.root)
}