package com.example.football.ui.positions

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
import com.example.football.databinding.FragmentPositionsBinding
import com.example.football.databinding.ItemPositionBinding
import com.example.football.model.player.Player
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

        model.getPositions()

        model.positions.observe(viewLifecycleOwner, {
            it.forEach { position ->
                model.getPlayersByPosition(position)
            }
        })

        model.playersOnPositions.observe(viewLifecycleOwner, {
            adapter.addData(it)
        })

        model.position.observe(viewLifecycleOwner, {
            if (it != null) {
                val action = MainFragmentDirections.actionMainFragmentToPositionInClubsFragment(
                    it
                )
                navController.navigate(action)
                model.position.value = null
            }
        })
    }

    inner class PositionsAdapter(
        private val model: PositionsViewModel,
        private var data: MutableList<Pair<List<Player>, String>> = mutableListOf()
    ) : RecyclerView.Adapter<PositionsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.model = model
            with(holder.binding) {
                this.position.text = data[position].second
                this.players.text = data[position].first.size.toString()
                this.pos = data[position].second
            }
        }

        override fun getItemCount() = data.size

        fun addData(pair: Pair<List<Player>, String>) {
            data.add(pair)
            notifyDataSetChanged()
        }

        inner class ViewHolder(val binding: ItemPositionBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}

