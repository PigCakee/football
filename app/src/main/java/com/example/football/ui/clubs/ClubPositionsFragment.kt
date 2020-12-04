package com.example.football.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsBinding
import com.example.football.ui.adapters.ClubPositionsPageAdapter
import com.example.football.utils.inflaters.contentView
import com.google.android.material.tabs.TabLayoutMediator

class ClubPositionsFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsBinding>(R.layout.fragment_club_positions)
    private lateinit var adapter: ClubPositionsPageAdapter
    private val args by navArgs<ClubPositionsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = ClubPositionsPageAdapter(this, requireContext(), args.club)
        binding.pager.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = args.club.name
        TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
            tab.text = adapter.positions[pos]
        }.attach()
    }
}