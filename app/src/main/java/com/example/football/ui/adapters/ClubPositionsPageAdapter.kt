package com.example.football.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.model.club.Club
import com.example.football.ui.clubs.ClubPositionsPageFragment
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG
import com.example.football.utils.view.positionsList

class ClubPositionsPageAdapter(
    fragment: Fragment,
    private val club: Club
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = positionsList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ClubPositionsPageFragment()
        val bundle = Bundle().apply {
            putString(POS_ARG, positionsList[position])
            putParcelable(CLUB_ARG, club)
        }
        fragment.arguments = bundle
        return fragment
    }
}