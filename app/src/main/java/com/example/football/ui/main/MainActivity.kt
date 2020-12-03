package com.example.football.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.football.MainApplication
import com.example.football.R
import com.example.football.databinding.ActivityMainBinding
import com.example.football.di.ClubsComponent
import com.example.football.di.NationalitiesComponent
import com.example.football.di.PositionsComponent
import com.example.football.ui.adapters.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var clubsComponent: ClubsComponent
    lateinit var nationalitiesComponent: NationalitiesComponent
    lateinit var positionsComponent: PositionsComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    private fun initDagger() {
        clubsComponent =
            (application as MainApplication).appComponent.clubsComponent().create()
        clubsComponent.inject(this)

        nationalitiesComponent =
            (application as MainApplication).appComponent.nationalitiesComponent().create()
        nationalitiesComponent.inject(this)

        positionsComponent =
            (application as MainApplication).appComponent.positionsComponent().create()
        positionsComponent.inject(this)
    }
}