package com.example.football.ui.positions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.football.R
import com.example.football.databinding.FragmentPositionInClubsBinding
import com.example.football.ui.adapters.PositionInClubsPageAdapter
import com.example.football.utils.inflaters.contentView
import com.google.android.material.tabs.TabLayoutMediator

class PositionInClubsFragment : Fragment() {
    private val binding by contentView<FragmentPositionInClubsBinding>(R.layout.fragment_position_in_clubs)
    private lateinit var adapter: PositionInClubsPageAdapter
    private val args by navArgs<PositionInClubsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = PositionInClubsPageAdapter(this, args.position, args.clubs)
        binding.pager.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = args.position
        TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
            tab.text = args.clubs.clubs[pos].name
        }.attach()
    }
}