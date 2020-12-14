package com.example.football.ui.positions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    private lateinit var model: PositionsViewModel

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
        model = ViewModelProvider(this, modelFactory).get(PositionsViewModel::class.java)
        binding.model = model
        adapter = PositionsAdapter(model)
        binding.recyclerView.adapter = adapter

        model.playersOnPositions.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.position.observe(viewLifecycleOwner, {
            if (it != null) {
                val action = MainFragmentDirections.actionMainFragmentToPositionInClubsFragment(it)
                findNavController().navigate(action)
                model.position.value = null
            }
        })

        return binding.root
    }
}

class PositionsAdapter(
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
            players.text = data[position].first.size.toString()
            pos = data[position].second
        }
    }

    override fun getItemCount() = data.size

    fun setData(newData: MutableList<Pair<List<Player>, String>>) {
        data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemPositionBinding) :
        RecyclerView.ViewHolder(binding.root)
}
