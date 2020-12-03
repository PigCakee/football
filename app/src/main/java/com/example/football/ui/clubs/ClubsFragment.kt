package com.example.football.ui.clubs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentClubsBinding
import com.example.football.ui.adapters.ClubsAdapter
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class ClubsFragment : Fragment() {
    private val binding by contentView<FragmentClubsBinding>(R.layout.fragment_clubs)
    private lateinit var adapter: ClubsAdapter

    @Inject
    lateinit var model: ClubsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).clubsComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.model = model
        adapter = ClubsAdapter(model, requireContext())
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.clubs.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.club.observe(viewLifecycleOwner, {
            // navigate to club's positions fragment
        })
    }
}