package com.example.football.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentMainBinding
import com.example.football.ui.adapters.SectionsPagerAdapter
import com.example.football.utils.inflaters.contentView

class MainFragment : Fragment() {
    private val binding by contentView<FragmentMainBinding>(R.layout.fragment_main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        return binding.root
    }
}