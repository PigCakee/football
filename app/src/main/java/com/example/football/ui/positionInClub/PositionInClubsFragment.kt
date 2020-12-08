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
import com.example.football.databinding.FragmentPositionInClubsBinding
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.POS_ARG
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class PositionInClubsFragment : Fragment() {
    private val binding by contentView<FragmentPositionInClubsBinding>(R.layout.fragment_position_in_clubs)
    private lateinit var adapter: PositionInClubsPageAdapter
    private val args by navArgs<PositionInClubsFragmentArgs>()
    private lateinit var model: PositionInClubsViewModel

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

        binding.title.text = args.position
        model.clubs.observe(viewLifecycleOwner, {
            adapter = PositionInClubsPageAdapter(this, args.position, it)
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

    inner class PositionInClubsPageAdapter(
        fragment: Fragment,
        private val pos: String,
        private val clubs: List<String>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = clubs.size

        override fun createFragment(position: Int): Fragment {
            val fragment = PositionInClubsPageFragment()
            val bundle = Bundle().apply {
                putString(CLUB_ARG, clubs[position])
                putString(POS_ARG, pos)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}