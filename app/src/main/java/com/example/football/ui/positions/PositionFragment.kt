package com.example.football.ui.positions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentPositionsBinding
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class PositionFragment : Fragment() {
    private val binding by contentView<FragmentPositionsBinding>(R.layout.fragment_positions)

    @Inject
    lateinit var model: PositionViewModel

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
        return binding.root
    }
}