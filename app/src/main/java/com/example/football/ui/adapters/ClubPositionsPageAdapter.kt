package com.example.football.ui.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.R
import com.example.football.model.club.Club
import com.example.football.ui.clubs.ClubPositionsPageFragment
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG

class ClubPositionsPageAdapter(
    fragment: Fragment,
    context: Context,
    private val club: Club
) : FragmentStateAdapter(fragment) {

    val positions = arrayOf(
        context.getString(R.string.goalkeeper),
        context.getString(R.string.forward),
        context.getString(R.string.middle),
        context.getString(R.string.defence)
    )

    override fun getItemCount() = positions.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ClubPositionsPageFragment()
        val bundle = Bundle().apply {
            putString(POS_ARG, positions[position])
            putParcelable(CLUB_ARG, club)
        }
        fragment.arguments = bundle
        return fragment
    }
}