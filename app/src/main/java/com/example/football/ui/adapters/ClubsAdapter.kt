package com.example.football.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.ClubBinding
import com.example.football.model.club.Club
import com.example.football.ui.clubs.ClubsViewModel

class ClubsAdapter(
    private val model: ClubsViewModel,
    private val context: Context,
    private var data: List<Club> = listOf()
) : RecyclerView.Adapter<ClubsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ClubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club = data[position]

        holder.binding.model = model
        with(holder.binding) {
            this.club.text = club.name
            this.clubb = club
            if (club.players.isNotEmpty()) {
                this.players.text = club.players.size.toString()
            } else {
                this.players.text = context.getString(R.string.no_players)
            }
        }
    }

    override fun getItemCount() = data.size

    fun setData(groups: List<Club>) {
        data = groups
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ClubBinding) : RecyclerView.ViewHolder(binding.root)
}