package com.example.football.ui.nationalityInClub

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.FragmentClubPositionsPageBinding
import com.example.football.databinding.ItemPlayerBinding
import com.example.football.model.player.Player
import com.example.football.ui.main.MainActivity
import com.example.football.ui.nationalities.NationalitiesViewModel
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.NATIONALITY_ARG
import javax.inject.Inject

class NationalitiesInClubsPageFragment : Fragment() {
    private val binding by contentView<FragmentClubPositionsPageBinding>(R.layout.fragment_club_positions_page)
    private lateinit var model: NationalitiesInClubsViewModel

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
        model = ViewModelProvider(this, modelFactory).get(NationalitiesInClubsViewModel::class.java)
        arguments?.apply {
            val nationality: String? = getString(NATIONALITY_ARG)
            val club: String? = getString(CLUB_ARG)
            if (nationality != null && club != null) {
                model.getPlayersByNationalityInClub(nationality, club)
            }
        }

        model.playersWithNationalityInClub.observe(viewLifecycleOwner, {
            val adapter = NationalityInClubsListAdapter(it)
            binding.recyclerView.adapter = adapter
        })
        return binding.root
    }

    inner class NationalityInClubsListAdapter(
        private var data: List<Player>
    ) : RecyclerView.Adapter<NationalityInClubsListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val player = data[position]

            with(holder.binding) {
                this.name.text = player.name
                this.pos.text = player.position
            }
        }

        override fun getItemCount() = data.size

        inner class ViewHolder(val binding: ItemPlayerBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}