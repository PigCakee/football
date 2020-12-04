package com.example.football.ui.positions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.football.R
import com.example.football.databinding.FragmentPositionsBinding
import com.example.football.model.club.Club
import com.example.football.model.club.Clubs
import com.example.football.ui.adapters.PositionsAdapter
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class PositionsFragment : Fragment() {
    private val binding by contentView<FragmentPositionsBinding>(R.layout.fragment_positions)
    private lateinit var adapter: PositionsAdapter
    private lateinit var navController: NavController

    @Inject
    lateinit var model: PositionsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).positionsComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.model = model
        adapter = PositionsAdapter(model)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        model.clubs.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.position.observe(viewLifecycleOwner, {
            if (it != null) {
                val clubsArrayList = arrayListOf<Club>()
                model.clubs.value?.let { club -> clubsArrayList.addAll(club) }
                val action = MainFragmentDirections.actionMainFragmentToPositionInClubsFragment(
                    Clubs(clubsArrayList),
                    it
                )
                navController.navigate(action)
                model.position.value = null
            }
        })
    }
}