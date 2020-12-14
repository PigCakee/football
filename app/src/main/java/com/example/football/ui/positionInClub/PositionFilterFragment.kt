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
import com.example.football.R
import com.example.football.databinding.FragmentPlayersFilterBinding
import com.example.football.ui.ClubsPositionsNationalitiesPageAdapter
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.FLAG_ARG
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class PositionInClubsFragment : Fragment() {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private lateinit var adapterNationalities: ClubsPositionsNationalitiesPageAdapter
    private val args by navArgs<PositionInClubsFragmentArgs>()
    private lateinit var model: PositionFilterViewModel
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
        model = ViewModelProvider(this, modelFactory).get(PositionFilterViewModel::class.java)
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
                adapterNationalities = ClubsPositionsNationalitiesPageAdapter(this, pos = model.title, clubs = it)
                binding.pager.adapter = adapterNationalities

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
                adapterNationalities = ClubsPositionsNationalitiesPageAdapter(this, pos = model.title, nationalities = it)
                binding.pager.adapter = adapterNationalities

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
