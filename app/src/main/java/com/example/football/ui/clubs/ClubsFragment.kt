package com.example.football.ui.clubs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.FragmentClubsBinding
import com.example.football.databinding.ItemClubBinding
import com.example.football.model.player.Player
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class ClubsFragment : Fragment() {
    private val binding by contentView<FragmentClubsBinding>(R.layout.fragment_clubs)
    private lateinit var adapter: ClubsAdapter
    private lateinit var navController: NavController

    @Inject
    lateinit var model: ClubsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
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
        navController = Navigation.findNavController(view)

        model.getClubs()

        model.playersInClub.observe(viewLifecycleOwner, {
            adapter.addData(it)
        })

        model.club.observe(viewLifecycleOwner, {
            if (it != null) {
                val action = MainFragmentDirections.actionMainFragmentToClubPositionsFragment(it)
                navController.navigate(action)
                model.club.value = null
            }
        })
    }

    inner class ClubsAdapter(
        private val model: ClubsViewModel,
        private val context: Context,
        private var data: MutableList<Pair<List<Player>, String>> = mutableListOf()
    ) : RecyclerView.Adapter<ClubsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemClubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val club = data[position]

            holder.binding.model = model
            with(holder.binding) {
                this.club.text = club.second
                this.clubData = club.second
                if (club.first.isNotEmpty()) {
                    this.players.text = club.first.size.toString()
                } else {
                    this.players.text = context.getString(R.string.no_players)
                }
            }
        }

        override fun getItemCount() = data.size

        fun addData(clubs: Pair<List<Player>, String>) {
            if (!data.contains(clubs)) {
                data.add(clubs)
                notifyDataSetChanged()
            }
        }

        inner class ViewHolder(val binding: ItemClubBinding) : RecyclerView.ViewHolder(binding.root)
    }
}