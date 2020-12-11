package com.example.football.ui.positionInClub

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
import com.example.football.utils.view.FLAG_ARG
import com.example.football.utils.view.NATIONALITY_ARG
import com.example.football.utils.view.POS_ARG
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class PositionInClubsFragment : Fragment() {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private lateinit var adapter: PositionInClubsPageAdapter
    private val args by navArgs<PositionInClubsFragmentArgs>()
    private lateinit var model: PositionInClubsViewModel
    private var flag: Boolean = true

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
        model = ViewModelProvider(this, modelFactory).get(PositionInClubsViewModel::class.java)
        if (model.title.isBlank()) {
            model.title = args.position
        }
        binding.title.text = model.title

        arguments?.apply {
            flag = getBoolean(FLAG_ARG)
        }

        if (flag) {
            if (model.position == null) {
                model.position = model.title
                model.getClubsWithPosition(model.title)
            }

            model.clubs.observe(viewLifecycleOwner, {
                adapter = PositionInClubsPageAdapter(this, model.title, it, emptyList())
                binding.pager.adapter = adapter

                TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                    tab.text = it[pos]
                }.attach()
            })
        } else {
            if (model.position == null) {
                model.position = model.title
                model.getNationalitiesWithPosition(model.title)
            }

            model.nationalities.observe(viewLifecycleOwner, {
                adapter = PositionInClubsPageAdapter(this, model.title, emptyList(), it)
                binding.pager.adapter = adapter

                TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                    tab.text = it[pos]
                }.attach()
            })
        }

        binding.switchBtn.setOnClickListener {
            binding.pager.adapter = null
            model.position = null
            parentFragmentManager
                .beginTransaction()
                .detach(this)
                .attach(this.apply {
                    val bundle = Bundle().apply { putBoolean(FLAG_ARG, !flag) }
                    arguments = bundle
                })
                .commit()
        }

        binding.back.setOnClickListener { findNavController().popBackStack() }
        return binding.root
    }
}

class PositionInClubsPageAdapter(
    fragment: Fragment,
    private val pos: String,
    private val clubs: List<String>,
    private val nationalities: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return if (clubs.isNotEmpty()) clubs.size
        else nationalities.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PlayersPageFragment()
        val bundle = Bundle().apply {
            if (clubs.isNotEmpty()) {
                putString(CLUB_ARG, clubs[position])
            }
            if (nationalities.isNotEmpty()) {
                putString(NATIONALITY_ARG, nationalities[position])
            }
            putString(POS_ARG, pos)
        }
        fragment.arguments = bundle
        return fragment
    }
}
