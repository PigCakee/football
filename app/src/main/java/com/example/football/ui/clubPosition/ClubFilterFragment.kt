package com.example.football.ui.clubPosition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.football.R
import com.example.football.databinding.FragmentPlayersFilterBinding
import com.example.football.ui.ClubsPositionsNationalitiesPageAdapter
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.FLAG_ARG
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ClubPositionsFragment : MvpAppCompatFragment(), ClubFilterView {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private val args by navArgs<ClubPositionsFragmentArgs>()
    private var flag: Boolean = true

    @Inject
    lateinit var presenterProvider: Provider<ClubFilterPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply { flag = getBoolean(FLAG_ARG) }

        if (presenter.title.isBlank()) { presenter.title = args.club }

        if (presenter.club == null) { presenter.club = presenter.title }

        if (flag) { presenter.getPositionsInClub(presenter.title) }
        else { presenter.getNationalitiesInClub(presenter.title) }

        binding.title.text = presenter.title

        binding.back.setOnClickListener { findNavController().popBackStack() }

        binding.switchBtn.setOnClickListener {
            binding.pager.adapter = null
            presenter.club = null
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

    override fun setRecyclerData(list: List<String>) {
        if (flag) {
            binding.pager.adapter =
                ClubsPositionsNationalitiesPageAdapter(
                    this,
                    club = presenter.title,
                    positions = list
                )
        } else {
            binding.pager.adapter =
                ClubsPositionsNationalitiesPageAdapter(
                    this,
                    club = presenter.title,
                    nationalities = list
                )
        }
        TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
            tab.text = list[pos]
        }.attach()
    }
}
