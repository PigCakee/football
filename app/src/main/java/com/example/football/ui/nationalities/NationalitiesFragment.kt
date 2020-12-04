package com.example.football.ui.nationalities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.football.R
import com.example.football.databinding.FragmentNationalitiesBinding
import com.example.football.model.club.Club
import com.example.football.model.club.Clubs
import com.example.football.ui.adapters.NationalitiesAdapter
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class NationalitiesFragment : Fragment() {
    private val binding by contentView<FragmentNationalitiesBinding>(R.layout.fragment_nationalities)
    private lateinit var adapter: NationalitiesAdapter
    private lateinit var navController: NavController

    @Inject
    lateinit var model: NationalitiesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).nationalitiesComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.model = model
        adapter = NationalitiesAdapter(model)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        model.clubs.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.nationality.observe(viewLifecycleOwner, {
            if (it != null) {
                val clubsArrayList = arrayListOf<Club>()
                model.clubs.value?.let { club -> clubsArrayList.addAll(club) }
                val action =
                    MainFragmentDirections.actionMainFragmentToNationalitiesInClubsFragment(
                        Clubs(clubsArrayList), it
                    )
                navController.navigate(action)
                model.nationality.value = null
            }
        })
    }
}