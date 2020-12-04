package com.example.football.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsBinding
import com.example.football.ui.adapters.ClubPositionsPageAdapter
import com.example.football.utils.inflaters.contentView
import com.google.android.material.tabs.TabLayoutMediator

class ClubPositionsFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsBinding>(R.layout.fragment_club_positions)
    private lateinit var adapter: ClubPositionsPageAdapter
    // TODO get args by SafeArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = ClubPositionsPageAdapter(this, requireContext())
        binding.pager.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, _ ->
            adapter.positions.forEach { tab.text = it }
        }.attach()
    }
}