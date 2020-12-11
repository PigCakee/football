package com.example.football.ui.nationalityInClub

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

class NationalitiesInClubsFragment : Fragment() {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private lateinit var adapter: NationalityInClubsPageAdapter
    private val args by navArgs<NationalitiesInClubsFragmentArgs>()
    private lateinit var model: NationalitiesFilterViewModel
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
        model = ViewModelProvider(this, modelFactory).get(NationalitiesFilterViewModel::class.java)

        if (model.title.isBlank()) {
            model.title = args.nationality
        }
        binding.title.text = model.title

        arguments?.apply {
            flag = getBoolean(FLAG_ARG)
        }

        if (flag) {
            if (model.nationality == null) {
                model.nationality = model.title
                model.getClubsWithNationality(model.title)
            }

            model.clubs.observe(viewLifecycleOwner, {
                adapter = NationalityInClubsPageAdapter(this, model.title, it, emptyList())
                binding.pager.adapter = adapter

                TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                    tab.text = it[pos]
                }.attach()

                binding.back.setOnClickListener { findNavController().popBackStack() }
            })
        } else {
            if (model.nationality == null) {
                model.nationality = model.title
                model.getPositionsWithNationality(model.title)
            }

            model.positions.observe(viewLifecycleOwner, {
                adapter = NationalityInClubsPageAdapter(this, model.title, emptyList(), it)
                binding.pager.adapter = adapter

                TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
                    tab.text = it[pos]
                }.attach()

                binding.back.setOnClickListener { findNavController().popBackStack() }
            })
        }


        binding.switchBtn.setOnClickListener {
            binding.pager.adapter = null
            model.nationality = null
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

class NationalityInClubsPageAdapter(
    fragment: Fragment,
    private val nationality: String,
    private val clubs: List<String>,
    private val positions: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return if (clubs.isNotEmpty()) clubs.size
        else positions.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PlayersPageFragment()
        val bundle = Bundle().apply {
            if (clubs.isNotEmpty()) { putString(CLUB_ARG, clubs[position]) }
            if (positions.isNotEmpty()) { putString(POS_ARG, positions[position]) }
            putString(NATIONALITY_ARG, nationality)
        }
        fragment.arguments = bundle
        return fragment
    }
}
