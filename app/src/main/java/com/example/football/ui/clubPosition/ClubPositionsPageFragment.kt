package com.example.football.ui.clubPosition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsPageBinding
import com.example.football.databinding.ItemPlayerBinding
import com.example.football.model.player.Player
import com.example.football.ui.clubs.ClubsViewModel
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG
import javax.inject.Inject

class ClubPositionsPageFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsPageBinding>(R.layout.fragment_club_positions_page)

    @Inject
    lateinit var model: ClubsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            val position: String? = getString(POS_ARG)
            val club: String? = getString(CLUB_ARG)
            if (club != null && position != null) {
                model.getPlayersByPositionInClub(position, club)
            }
        }

        model.playersOnPositionInClub.observe(viewLifecycleOwner, {
            val adapter = ClubPositionsListAdapter(it)
            binding.recyclerView.adapter = adapter
        })

        return binding.root
    }

    inner class ClubPositionsListAdapter(
        private val data: List<Player>
    ) : RecyclerView.Adapter<ClubPositionsListAdapter.ViewHolder>() {

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