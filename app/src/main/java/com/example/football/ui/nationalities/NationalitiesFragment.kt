package com.example.football.ui.nationalities

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
import com.example.football.databinding.FragmentNationalitiesBinding
import com.example.football.databinding.ItemNationalityBinding
import com.example.football.model.player.Player
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class NationalitiesFragment : Fragment() {
    private val binding by contentView<FragmentNationalitiesBinding>(R.layout.fragment_nationalities)
    private lateinit var adapter: NationalitiesAdapter
    private lateinit var model: NationalitiesViewModel

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
        model = ViewModelProvider(this, modelFactory).get(NationalitiesViewModel::class.java)
        binding.model = model
        adapter = NationalitiesAdapter(model)
        binding.recyclerView.adapter = adapter

        model.playersWithNationalityData.observe(viewLifecycleOwner, {
            it.forEach { pair -> adapter.addData(pair) }
        })

        model.nationality.observe(viewLifecycleOwner, {
            if (it != null) {
                val action =
                    MainFragmentDirections.actionMainFragmentToNationalitiesInClubsFragment(it)
                findNavController().navigate(action)
                model.nationality.value = null
            }
        })

        return binding.root
    }
}

class NationalitiesAdapter(
    private val model: NationalitiesViewModel,
    private var data: MutableList<Pair<List<Player>, String>> = mutableListOf()
) : RecyclerView.Adapter<NationalitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNationalityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = model
        with(holder.binding) {
            nationality.text = data[position].second
            players.text = data[position].first.size.toString()
            nation = data[position].second
        }
    }

    override fun getItemCount() = data.size

    fun addData(players: Pair<List<Player>, String>) {
        if (!data.contains(players)) {
            data.add(players)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val binding: ItemNationalityBinding) : RecyclerView.ViewHolder(binding.root)
}
