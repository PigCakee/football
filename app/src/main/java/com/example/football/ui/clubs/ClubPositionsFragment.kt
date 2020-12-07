package com.example.football.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsBinding
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class ClubPositionsFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsBinding>(R.layout.fragment_club_positions)
    private lateinit var adapter: ClubPositionsPageAdapter
    private val args by navArgs<ClubPositionsFragmentArgs>()

    @Inject
    lateinit var model: ClubsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model.getAllPositions()

        binding.title.text = args.club.name
        model.positions.observe(viewLifecycleOwner, {
            TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                tab.text = it[pos]
            }.attach()

            adapter = ClubPositionsPageAdapter(this, args.club)
            binding.pager.adapter = adapter
        })

        return binding.root
    }

    inner class ClubPositionsPageAdapter(
        fragment: Fragment,
        private val club: String,
        private val positions: List<String>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = positions.size

        override fun createFragment(position: Int): Fragment {
            val fragment = ClubPositionsPageFragment()
            val bundle = Bundle().apply {
                putString(POS_ARG, positions[position])
                putString(CLUB_ARG, club)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}