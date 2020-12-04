package com.example.football.ui.positions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsPageBinding
import com.example.football.model.club.Club
import com.example.football.ui.adapters.PositionInClubsListAdapter
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG

class PositionInClubsPageFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsPageBinding>(R.layout.fragment_club_positions_page)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            val position: String? = getString(POS_ARG)
            val club: Club? = getParcelable(CLUB_ARG)
            if (position != null && club != null) {
                val adapter = PositionInClubsListAdapter(position, club)
                binding.recyclerView.adapter = adapter
            }
        }
        return binding.root
    }
}