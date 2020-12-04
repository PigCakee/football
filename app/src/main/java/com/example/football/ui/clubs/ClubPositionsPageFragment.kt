package com.example.football.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsPageBinding
import com.example.football.utils.inflaters.contentView

class ClubPositionsPageFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsPageBinding>(R.layout.fragment_club_positions_page)

    companion object {
        const val ARG = "arg"
        fun newInstance(argument: String): ClubPositionsPageFragment {
            return ClubPositionsPageFragment().apply { arguments?.putString(ARG, argument) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.takeIf { it.containsKey(ARG) }?.apply {
            // TODO set adapter with getString(ARG)
        }
        return binding.root
    }
}