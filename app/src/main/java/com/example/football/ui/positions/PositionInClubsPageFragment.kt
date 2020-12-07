package com.example.football.ui.positions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsPageBinding
import com.example.football.databinding.ItemPlayerBinding
import com.example.football.model.club.Player
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG
import javax.inject.Inject

class PositionInClubsPageFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsPageBinding>(R.layout.fragment_club_positions_page)

    @Inject
    lateinit var model: PositionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            val position: String? = getString(POS_ARG)
            val club: String? = getString(CLUB_ARG)
            if (position != null && club != null) {
                model.getPlayersByPositionInClub(position, club)
            }
        }

        model.playersOnPositionInClub.observe(viewLifecycleOwner, {
            val adapter = PositionInClubsListAdapter(it)
            binding.recyclerView.adapter = adapter
        })

        return binding.root
    }

    inner class PositionInClubsListAdapter(
        private val data: List<Player>
    ) : RecyclerView.Adapter<PositionInClubsListAdapter.ViewHolder>() {

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

        inner class ViewHolder(val binding: ItemPlayerBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}