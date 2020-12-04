package com.example.football.ui.nationalities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsPageBinding
import com.example.football.model.club.Club
import com.example.football.ui.adapters.NationalityInClubsListAdapter
import com.example.football.ui.adapters.PositionInClubsListAdapter
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.NATIONALITY_ARG

class NationalitiesInClubsPageFragment: Fragment() {
    private val binding by contentView<FragmentClubPositionsPageBinding>(R.layout.fragment_club_positions_page)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            val nationality: String? = getString(NATIONALITY_ARG)
            val club: Club? = getParcelable(CLUB_ARG)
            if (nationality != null && club != null) {
                val adapter = NationalityInClubsListAdapter(nationality, club)
                binding.recyclerView.adapter = adapter
            }
        }
        return binding.root
    }
}