package com.example.football.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.R
import com.example.football.ui.clubs.ClubPositionsPageFragment

class ClubPositionsPageAdapter(
    fragment: Fragment,
    context: Context
) : FragmentStateAdapter(fragment) {

    val positions = arrayOf(
        context.getString(R.string.goalkeeper),
        context.getString(R.string.forward),
        context.getString(R.string.middle),
        context.getString(R.string.defence)
    )

    override fun getItemCount() = positions.size

    override fun createFragment(position: Int): Fragment {
        return ClubPositionsPageFragment.newInstance(positions[position])
    }
}