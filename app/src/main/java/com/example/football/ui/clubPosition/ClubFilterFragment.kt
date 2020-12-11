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
import com.example.football.utils.view.FLAG_ARG
import com.example.football.utils.view.NATIONALITY_ARG
import com.example.football.utils.view.POS_ARG
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class ClubPositionsFragment : Fragment() {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private lateinit var adapter: ClubPositionsPageAdapter
    private val args by navArgs<ClubPositionsFragmentArgs>()
    private lateinit var model: ClubFilterViewModel
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
        model = ViewModelProvider(this, modelFactory).get(ClubFilterViewModel::class.java)
        if (model.title.isBlank()) {
            model.title = args.club
        }
        binding.title.text = model.title

        arguments?.apply {
            flag = getBoolean(FLAG_ARG)
        }

        if (flag) {
            if (model.club == null) {
                model.club = model.title
                model.getPositionsInClub(model.title)
            }

            binding.title.text = model.title
            model.positions.observe(viewLifecycleOwner, {
                adapter = ClubPositionsPageAdapter(this, model.title, it, emptyList())
                binding.pager.adapter = adapter

                TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                    tab.text = it[pos]
                }.attach()
            })
        } else {
            if (model.club == null) {
                model.club = model.title
                model.getNationalitiesInClub(model.title)
            }

            binding.title.text = model.title
            model.nationalities.observe(viewLifecycleOwner, {
                adapter = ClubPositionsPageAdapter(this, model.title, emptyList(), it)
                binding.pager.adapter = adapter

                TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                    tab.text = it[pos]
                }.attach()
            })
        }



        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.switchBtn.setOnClickListener {
            binding.pager.adapter = null
            model.club = null
            parentFragmentManager
                .beginTransaction()
                .detach(this)
                .attach(this.apply {
                    val bundle = Bundle().apply { putBoolean(FLAG_ARG, !flag) }
                    arguments = bundle
                })
                .commit()
        }
        return binding.root
    }
}

class ClubPositionsPageAdapter(
    fragment: Fragment,
    private val club: String,
    private val positions: List<String>,
    private val nationalities: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return if (positions.isNotEmpty()) positions.size
        else nationalities.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PlayersPageFragment()
        val bundle = Bundle().apply {
            if (positions.isNotEmpty()) {
                putString(POS_ARG, positions[position])
            }
            if (nationalities.isNotEmpty()) {
                putString(NATIONALITY_ARG, nationalities[position])
            }
            putString(CLUB_ARG, club)
        }
        fragment.arguments = bundle
        return fragment
    }
}