package com.example.football.ui.nationalities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.data.entity.Player
import com.example.football.databinding.FragmentNationalitiesBinding
import com.example.football.databinding.ItemNationalityBinding
import com.example.football.ui.main.MainActivity
import com.example.football.ui.main.MainFragmentDirections
import com.example.football.utils.inflaters.contentView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class NationalitiesFragment : MvpAppCompatFragment(), NationalitiesView {
    private val binding by contentView<FragmentNationalitiesBinding>(R.layout.fragment_nationalities)
    private lateinit var adapter: NationalitiesAdapter

    @Inject
    lateinit var presenterProvider: Provider<NationalitiesPresenter>
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
        adapter = NationalitiesAdapter(presenter)
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
        binding.loading.visibility = View.INVISIBLE
        adapter.setData(list)
    }

    override fun moveForward(nationality: String) {
        val action =
            MainFragmentDirections.actionMainFragmentToNationalitiesInClubsFragment(nationality)
        findNavController().navigate(action)
    }
}

class NationalitiesAdapter(
    private val presenter: NationalitiesPresenter,
    private var data: MutableList<Pair<List<Player>, String>> = mutableListOf()
) : RecyclerView.Adapter<NationalitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNationalityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            nationality.text = data[position].second
            players.text = data[position].first.size.toString()
            nation = data[position].second
            container.setOnClickListener { presenter.handleNationalityClick(data[position].second) }
        }
    }

    override fun getItemCount() = data.size

    fun setData(newData: MutableList<Pair<List<Player>, String>>) {
        newData.forEach { newIt ->
            var exists = false
            for (it in data) {
                if (it.second == newIt.second) {
                    exists = true
                    break
                }
            }
            if (!exists) data.add(newIt)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemNationalityBinding) : RecyclerView.ViewHolder(binding.root)
}
