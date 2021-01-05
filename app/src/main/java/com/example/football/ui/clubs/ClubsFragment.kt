package com.example.football.ui.clubs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.data.entity.Player
import com.example.football.databinding.FragmentClubsBinding
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ClubsFragment : MvpAppCompatFragment(), ClubsView {
    private val binding by contentView<FragmentClubsBinding>(R.layout.fragment_clubs)
    private lateinit var adapter: ClubsAdapter

    @Inject
    lateinit var presenterProvider: Provider<ClubsPresenter>
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
        binding.loading.visibility = View.VISIBLE
        binding.loading.playAnimation()
        adapter = ClubsAdapter(presenter, requireContext())
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        if (view != null) {
            val parentViewGroup = view?.parent as ViewGroup?
            parentViewGroup?.removeAllViews()
        }
        super.onDestroyView()
    }

    override fun setRecyclerData(list: MutableList<Pair<List<Player>, String>>) {
        binding.loading.visibility = View.INVISIBLE
        adapter.setData(list)
    }

    override fun moveForward(club: String) {
        val action = MainFragmentDirections.actionMainFragmentToClubPositionsFragment(club)
        findNavController().navigate(action)
    }
}

class ClubsAdapter(
    private val presenter: ClubsPresenter,
    private val context: Context,
    private var data: MutableList<Pair<List<Player>, String>> = mutableListOf()
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_club, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club = data[position]
        holder.club.text = club.second
        if (club.first.isNotEmpty()) {
            holder.players.text = club.first.size.toString()
        } else {
            holder.players.text = context.getString(R.string.no_players)
        }
        holder.container.setOnClickListener { presenter.handleClubClick(club.second) }
    }

    override fun getItemCount() = data.size

    fun setData(newData: MutableList<Pair<List<Player>, String>>) {
        //newData.forEach { newIt ->
        //    var exists = false
        //    for (it in data) {
        //        if (it.second == newIt.second) {
        //            exists = true
        //            break
        //        }
        //    }
        //    if (!exists) data.add(newIt)
        //}
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}