package com.example.football.ui.playersPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.FragmentPlayersFilterPageBinding
import com.example.football.databinding.ItemPlayerBinding
import com.example.football.model.player.Player
import com.example.football.model.player.PositionsIconFactory
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.NATIONALITY_ARG
import com.example.football.utils.view.POS_ARG
import javax.inject.Inject

class PlayersPageFragment : Fragment() {
    private val binding by contentView<FragmentPlayersFilterPageBinding>(R.layout.fragment_players_filter_page)
    private lateinit var model: PlayersPageViewModel

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
        model = ViewModelProvider(this, modelFactory).get(PlayersPageViewModel::class.java)
        arguments?.apply {
            val position: String? = getString(POS_ARG)
            val nationality: String? = getString(NATIONALITY_ARG)
            val club: String? = getString(CLUB_ARG)
            if (club != null && position != null) {
                model.getPlayersByPositionInClub(position, club)
            }
            if (nationality != null && club != null) {
                model.getPlayersByNationalityInClub(nationality, club)
            }
        }

        model.playersOnPositionInClub.observe(viewLifecycleOwner, {
            val adapter = PlayersListAdapter(requireContext(), it)
            binding.recyclerView.adapter = adapter
        })

        model.playersWithNationalityInClub.observe(viewLifecycleOwner, {
            val adapter = PlayersListAdapter(requireContext(), it)
            binding.recyclerView.adapter = adapter
        })
        return binding.root
    }
}

class PlayersListAdapter(
    private val context: Context,
    private val data: List<Player>
) : RecyclerView.Adapter<PlayersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = data[position]

        with(holder.binding) {
            val playerInClub = player.position + " | " + player.club
            name.text = player.name
            pos.text = playerInClub
            nationality.text = player.nationality
            posIcon.setImageDrawable(
                PositionsIconFactory.getPositionIcon(
                    context,
                    player.position
                )
            )
        }
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root)
}