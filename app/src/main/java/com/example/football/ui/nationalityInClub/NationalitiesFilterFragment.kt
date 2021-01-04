package com.example.football.ui.nationalityInClub

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.football.R
import com.example.football.databinding.FragmentPlayersFilterBinding
import com.example.football.ui.ClubsPositionsNationalitiesPageAdapter
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.FLAG_ARG
import com.example.football.utils.view.OFFSCREEN_PAGE_LIMIT
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class NationalitiesInClubsFragment : MvpAppCompatFragment(), NationalitiesFilterView {
    private val binding by contentView<FragmentPlayersFilterBinding>(R.layout.fragment_players_filter)
    private val args by navArgs<NationalitiesInClubsFragmentArgs>()
    private var flag: Boolean = true

    @Inject
    lateinit var presenterProvider: Provider<NationalitiesFilterPresenter>
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

        if (presenter.title.isBlank()) { presenter.title = args.nationality }

        if (presenter.nationality == null) { presenter.nationality = presenter.title }

        if (flag) { presenter.getClubsWithNationality(presenter.title) }
        else { presenter.getPositionsWithNationality(presenter.title) }

        binding.title.text = presenter.title

        binding.back.setOnClickListener { findNavController().popBackStack() }

        binding.switchBtn.setOnClickListener {
            binding.pager.adapter = null
            presenter.nationality = null
            parentFragmentManager
                .beginTransaction()
                .detach(this)
                .attach(this.apply {
                    val bundle = Bundle().apply { putBoolean(FLAG_ARG, !flag) }
                    arguments = bundle
                })
                .commit()
        }
        binding.pager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT + OFFSCREEN_PAGE_LIMIT
        return binding.root
    }

    override fun setRecyclerData(list: List<String>) {
        if (flag) {
            binding.pager.adapter =
                ClubsPositionsNationalitiesPageAdapter(
                    this,
                    nationality = presenter.title,
                    clubs = list
                )
        } else {
            binding.pager.adapter =
                ClubsPositionsNationalitiesPageAdapter(
                    this,
                    nationality = presenter.title,
                    positions = list
                )
        }
        TabLayoutMediator(binding.tabs, binding.pager) { tab, pos ->
            tab.text = list[pos]
        }.attach()
    }
}

