package com.example.football.ui.nationalities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.football.R
import com.example.football.databinding.FragmentPositionInClubsBinding
import com.example.football.ui.adapters.NationalityInClubsPageAdapter
import com.example.football.ui.adapters.PositionInClubsPageAdapter
import com.example.football.ui.positions.PositionInClubsFragmentArgs
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.clubsList
import com.example.football.utils.view.nationalitiesList
import com.google.android.material.tabs.TabLayoutMediator

class NationalitiesInClubsFragment : Fragment() {
    private val binding by contentView<FragmentPositionInClubsBinding>(R.layout.fragment_position_in_clubs)
    private lateinit var adapter: NationalityInClubsPageAdapter
    private val args by navArgs<NationalitiesInClubsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = NationalityInClubsPageAdapter(this, args.nationality, args.clubs)
        binding.pager.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = args.nationality
        TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
            tab.text = clubsList[pos]
        }.attach()
    }
}