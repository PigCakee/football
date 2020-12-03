@file:Suppress("DEPRECATION")

package com.example.football.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.football.R
import com.example.football.ui.clubs.ClubsFragment
import com.example.football.ui.nationalities.NationalitiesFragment
import com.example.football.ui.positions.PositionFragment
import com.example.football.utils.view.CLUBS_POS
import com.example.football.utils.view.NATIONALITY_POS
import com.example.football.utils.view.POSITION_POS

private val TAB_TITLES = arrayOf(
    R.string.clubs,
    R.string.positions,
    R.string.nationalities
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            CLUBS_POS -> ClubsFragment()
            POSITION_POS -> PositionFragment()
            NATIONALITY_POS -> NationalitiesFragment()
            else -> ClubsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}