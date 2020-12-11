package com.example.football.ui.clubPosition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.R
import com.example.football.databinding.FragmentPlayersFilterBinding
import com.example.football.ui.main.MainActivity
import com.example.football.ui.playersPage.PlayersPageFragment
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class ClubPositionsFragment : Fragment() {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private lateinit var adapter: ClubPositionsPageAdapter
    private val args by navArgs<ClubPositionsFragmentArgs>()
    private lateinit var model: ClubFilterViewModel

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(this, modelFactory).get(ClubFilterViewModel::class.java)

        if (model.club == null) {
            model.club = args.club
            model.getPositionsInClub(args.club)
        }

        binding.title.text = args.club
        model.positions.observe(viewLifecycleOwner, {
            adapter = ClubPositionsPageAdapter(this, args.club, it)
            binding.pager.adapter = adapter

            TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                tab.text = it[pos]
            }.attach()
        })

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}

class ClubPositionsPageAdapter(
    fragment: Fragment,
    private val club: String,
    private val positions: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = positions.size

    override fun createFragment(position: Int): Fragment {
        val fragment = PlayersPageFragment()
        val bundle = Bundle().apply {
            putString(POS_ARG, positions[position])
            putString(CLUB_ARG, club)
        }
        fragment.arguments = bundle
        return fragment
    }
}