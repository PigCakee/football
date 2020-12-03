package com.example.football.ui.nationalities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.football.R
import com.example.football.databinding.FragmentNationalitiesBinding
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import javax.inject.Inject

class NationalitiesFragment : Fragment() {

    private val binding by contentView<FragmentNationalitiesBinding>(R.layout.fragment_nationalities)

    @Inject
    lateinit var model: NationalitiesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).nationalitiesComponent.inject(this)
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