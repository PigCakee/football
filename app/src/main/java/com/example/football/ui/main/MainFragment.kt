package com.example.football.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.football.R
import com.example.football.databinding.FragmentMainBinding
import com.example.football.ui.clubs.ClubsFragment
import com.example.football.ui.nationalities.NationalitiesFragment
import com.example.football.ui.positions.PositionsFragment
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUBS_POS
import com.example.football.utils.view.NATIONALITY_POS
import com.example.football.utils.view.POSITION_POS

class MainFragment : Fragment() {
    private val binding by contentView<FragmentMainBinding>(R.layout.fragment_main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sectionsPagerAdapter =
            SectionsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    // TODO replace with FragmentStateAdapter (but now NavComponent can't work with it)
    @Suppress("DEPRECATION")
    inner class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
        FragmentPagerAdapter(fm) {

        private val tabTitles = arrayOf(
            R.string.clubs,
            R.string.positions,
            R.string.nationalities
        )

        override fun getItem(position: Int): Fragment {
            return when (position) {
                CLUBS_POS -> ClubsFragment()
                POSITION_POS -> PositionsFragment()
                NATIONALITY_POS -> NationalitiesFragment()
                else -> ClubsFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return context.resources.getString(tabTitles[position])
        }

        override fun getCount(): Int {
            return tabTitles.size
        }
    }
}