package com.example.football.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.model.club.Clubs
import com.example.football.ui.positions.PositionInClubsPageFragment
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG

class PositionInClubsPageAdapter (
    fragment: Fragment,
    private val pos: String,
    private val clubs: Clubs
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = clubs.clubs.size

    override fun createFragment(position: Int): Fragment {
        val fragment = PositionInClubsPageFragment()
        val bundle = Bundle().apply {
            putParcelable(CLUB_ARG, clubs.clubs[position])
            putString(POS_ARG, pos)
        }
        fragment.arguments = bundle
        return fragment
    }
}