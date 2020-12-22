package com.example.football.ui.positions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.data.entity.Player
import com.example.football.databinding.FragmentPositionsBinding
import com.example.football.databinding.ItemPositionBinding
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class PositionsFragment : MvpAppCompatFragment(), PositionsView {
    private val binding by contentView<FragmentPositionsBinding>(R.layout.fragment_positions)
    private lateinit var adapter: PositionsAdapter

    @Inject
    lateinit var presenterProvider: Provider<PositionsPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = PositionsAdapter(presenter)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        if (view != null) {
            val parentViewGroup = view?.parent as ViewGroup?
            parentViewGroup?.removeAllViews();
        }
        super.onDestroyView()
    }

    override fun setRecyclerData(list: MutableList<Pair<List<Player>, String>>) {
        adapter.setData(list)
    }

    override fun moveForward(position: String) {
        val action = MainFragmentDirections.actionMainFragmentToPositionInClubsFragment(position)
        findNavController().navigate(action)
    }
}

class PositionsAdapter(
    private val presenter: PositionsPresenter,
    private var data: MutableList<Pair<List<Player>, String>> = mutableListOf()
) : RecyclerView.Adapter<PositionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            this.position.text = data[position].second
            players.text = data[position].first.size.toString()
            pos = data[position].second
            container.setOnClickListener { presenter.handlePositionClick(data[position].second) }
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
