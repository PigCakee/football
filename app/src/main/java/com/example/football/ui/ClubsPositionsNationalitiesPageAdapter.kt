package com.example.football.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.ui.playersPage.PlayersPageFragment
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.FRAGMENT_POS
import com.example.football.utils.view.NATIONALITY_ARG
import com.example.football.utils.view.POS_ARG

class ClubsPositionsNationalitiesPageAdapter(
    fragment: Fragment,
    private val club: String? = null,
    private val pos: String? = null,
    private val nationality: String? = null,
    private val clubs: List<String>? = null,
    private val positions: List<String>? = null,
    private val nationalities: List<String>? = null
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return (clubs ?: positions ?: nationalities)?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PlayersPageFragment()
        val bundle = Bundle().apply {
            if (!positions.isNullOrEmpty()) {
                putString(POS_ARG, positions[position])
            }
            if (!nationalities.isNullOrEmpty()) {
                putString(NATIONALITY_ARG, nationalities[position])
            }
            if (!clubs.isNullOrEmpty()) {
                putString(CLUB_ARG, clubs[position])
            }
            if (club != null) {
                putString(CLUB_ARG, club)
            }
            if (pos != null) {
                putString(POS_ARG, pos)
            }
            if (nationality != null) {
                putString(NATIONALITY_ARG, nationality)
            }
            putString(FRAGMENT_POS, position.toString())
        }
        fragment.arguments = bundle
        return fragment
    }
}