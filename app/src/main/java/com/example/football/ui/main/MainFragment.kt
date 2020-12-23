package com.example.football.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.football.R
import com.example.football.databinding.FragmentMainBinding
import com.example.football.ui.clubs.ClubsFragment
import com.example.football.ui.nationalities.NationalitiesFragment
import com.example.football.ui.positions.PositionsFragment
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUBS_POS
import com.example.football.utils.view.NATIONALITY_POS
import com.example.football.utils.view.POSITION_POS
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : MvpAppCompatFragment(), MainView {
    private val binding by contentView<FragmentMainBinding>(R.layout.fragment_main)

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    companion object {
        const val NO_FILE = "Back-up file does not exist"
        const val RESTORED = "Database is restored"
        const val BACKED_UP = "Database is backed up at: "
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.progressBar.visibility = View.VISIBLE

        binding.backUp.setOnClickListener {
            if (isExternalStorageAvailableForRW()) {
                presenter.backUpDatabase(requireContext())
            }
        }

        binding.restore.setOnClickListener {
            if (isExternalStorageAvailableForRW()) {
                presenter.restoreDatabase(requireContext())
            }
        }
        return binding.root
    }

    override fun notifyDatabaseReady() {
        binding.progressBar.visibility = View.INVISIBLE
        val sectionsPagerAdapter =
            SectionsPagerAdapter(requireContext(), childFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun notifyDatabaseBackedUp(path: String) {
        val snackbar =
            Snackbar.make(binding.root, BACKED_UP + path, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorCarbonBlack
            )
        )
        snackbar.show()
    }

    override fun notifyDatabaseRestored() {
        val snackbar =
            Snackbar.make(binding.root, RESTORED, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorCarbonBlack
            )
        )
        snackbar.show()
    }

    override fun notifyBackUpDoesNotExist() {
        val snackbar =
            Snackbar.make(binding.root, NO_FILE, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorCarbonBlack
            )
        )
        snackbar.show()
    }

    private fun isExternalStorageAvailableForRW(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}

// TODO replace with FragmentStateAdapter (but now NavComponent can't work with it)
@Suppress("DEPRECATION")
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf(
        R.string.clubs,
        R.string.positions,
        R.string.nationalities
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            CLUBS_POS -> ClubsFragment()
            POSITION_POS -> PositionsFragment()
            NATIONALITY_POS -> NationalitiesFragment()
            else -> ClubsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}