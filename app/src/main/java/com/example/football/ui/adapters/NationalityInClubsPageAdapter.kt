package com.example.football.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.model.club.Clubs
import com.example.football.ui.nationalities.NationalitiesInClubsPageFragment
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.NATIONALITY_ARG

class NationalityInClubsPageAdapter(
    fragment: Fragment,
    private val nationality: String,
    private val clubs: Clubs
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = clubs.clubs.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NationalitiesInClubsPageFragment()
        val bundle = Bundle().apply {
            putParcelable(CLUB_ARG, clubs.clubs[position])
            putString(NATIONALITY_ARG, nationality)
        }
        fragment.arguments = bundle
        return fragment
    }
}