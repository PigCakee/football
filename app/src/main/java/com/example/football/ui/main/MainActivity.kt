package com.example.football.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.football.MainApplication
import com.example.football.R
import com.example.football.databinding.ActivityMainBinding
import com.example.football.di.AppComponent

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun initDagger() {
        appComponent = (application as MainApplication).appComponent
    }
}