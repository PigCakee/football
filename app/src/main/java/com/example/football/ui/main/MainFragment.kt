package com.example.football.ui.main

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.football.R
import com.example.football.data.db.PlayerDatabase
import com.example.football.databinding.FragmentMainBinding
import com.example.football.ui.clubs.ClubsFragment
import com.example.football.ui.nationalities.NationalitiesFragment
import com.example.football.ui.positions.PositionsFragment
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.*
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.File
import javax.inject.Inject
import javax.inject.Provider


class MainFragment : MvpAppCompatFragment(), MainView {
    private val binding by contentView<FragmentMainBinding>(R.layout.fragment_main)

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var sPrefs: SharedPreferences

    @Inject
    lateinit var playersDatabase: PlayerDatabase

    companion object {
        const val NO_FILE = "Back-up file does not exist"
        const val RESTORED = "Database is restored"
        const val BACKED_UP = "Database is backed up at: "
        const val WRITE_REQUEST_CODE = 10
        const val READ_REQUEST_CODE = 20
        const val BACKUP = "back_up"
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
            backUpDatabase()
        }

        binding.restore.setOnClickListener {
            restoreDatabase()
        }
        return binding.root
    }

    private fun backUpDatabase() {
        presenter.checkpoint()
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_TITLE, BACKUP)
        startActivityForResult(intent, WRITE_REQUEST_CODE)
    }

    private fun restoreDatabase() {
        presenter.checkpoint()
        val dataUri = sPrefs.getString(BACK_UP_PATH_KEY, null)
        if (dataUri.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_TITLE, BACKUP)
            startActivityForResult(intent, READ_REQUEST_CODE)
        } else {
            val dbFile: File = requireContext().getDatabasePath(DATABASE_NAME)
            // TODO somehow check if file is not deleted
            if (dataUri.toUri().path != null) {
                copyFile(requireContext(), dataUri.toUri(), dbFile.toUri())
                notifyDatabaseRestored()
            } else {
                with(sPrefs.edit()) {
                    putString(BACK_UP_PATH_KEY, null)
                    commit()
                }
                restoreDatabase()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                WRITE_REQUEST_CODE -> {
                    if (data?.data != null) {
                        val dbFile: File = requireContext().getDatabasePath(DATABASE_NAME)
                        copyFile(requireContext(), dbFile.toUri(), data.data!!)

                        with(sPrefs.edit()) {
                            putString(BACK_UP_PATH_KEY, data.data.toString())
                            commit()
                        }
                        notifyDatabaseBackedUp(data.data.toString())
                    }
                }
                READ_REQUEST_CODE -> {
                    if (data?.data != null) {
                        val dbFile: File = requireContext().getDatabasePath(DATABASE_NAME)
                        copyFile(requireContext(), data.data!!, dbFile.toUri())
                        //presenter.updateDatabase()
                        with(sPrefs.edit()) {
                            putString(BACK_UP_PATH_KEY, data.data.toString())
                            commit()
                        }
                        notifyDatabaseRestored()
                    }
                }
            }
        }
    }

    private fun copyFile(context: Context, src: Uri, dst: Uri) {
        context.contentResolver.openInputStream(src).use { `in` ->
            context.contentResolver.openOutputStream(dst).use { out ->
                val buf = ByteArray(1024)
                var len: Int
                if (`in` != null) {
                    while (`in`.read(buf).also { len = it } > 0) {
                        out?.write(buf, 0, len)
                    }
                }
            }
        }
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