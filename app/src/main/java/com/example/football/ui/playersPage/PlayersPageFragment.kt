package com.example.football.ui.playersPage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.data.entity.Player
import com.example.football.data.entity.PositionsIconFactory
import com.example.football.databinding.FragmentPlayersFilterPageBinding
import com.example.football.databinding.ItemPlayerBinding
import com.example.football.ui.main.MainActivity
import com.example.football.utils.inflaters.contentView
import com.example.football.utils.view.CLUB_ARG
import com.example.football.utils.view.NATIONALITY_ARG
import com.example.football.utils.view.POS_ARG
import com.tbruyelle.rxpermissions3.RxPermissions
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class PlayersPageFragment : MvpAppCompatFragment(), PlayersPageView {
    private val binding by contentView<FragmentPlayersFilterPageBinding>(R.layout.fragment_players_filter_page)
    private lateinit var adapter: PlayersListAdapter
    private var position = 0

    @Inject
    lateinit var presenterProvider: Provider<PlayersPagePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    companion object {
        const val CAMERA_REQUEST_CODE = 10
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
        adapter = PlayersListAdapter(this, presenter, requireContext())
        binding.recyclerView.adapter = adapter
        arguments?.apply {
            val position: String? = getString(POS_ARG)
            val nationality: String? = getString(NATIONALITY_ARG)
            val club: String? = getString(CLUB_ARG)
            if (club != null && position != null) {
                presenter.getPlayersByPositionInClub(position, club)
            }
            if (nationality != null && club != null) {
                presenter.getPlayersByNationalityInClub(nationality, club)
            }
            if (nationality != null && position != null) {
                presenter.getPlayersWithNationalityInPosition(nationality, position)
            }
        }
        return binding.root
    }

    override fun setPlayersOnPositionInClubData(list: List<Player>) {
        adapter.setData(list)
    }

    override fun setPlayersWithNationalityInClubData(list: List<Player>) {
        adapter.setData(list)
    }

    override fun setPlayersWithNationalityInPositionData(list: List<Player>) {
        adapter.setData(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            adapter.setImageAtPosition(imageBitmap)
        }
    }

    inner class PlayersListAdapter(
        private val fragment: Fragment,
        private val presenter: PlayersPagePresenter,
        private val context: Context,
        private var data: MutableList<Player> = mutableListOf()
    ) : RecyclerView.Adapter<PlayersListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val player = data[position]

            with(holder.binding) {
                val playerInClub = player.position + " | " + player.club
                name.text = player.name
                pos.text = playerInClub
                nationality.text = player.nationality
                if (player.icon != null) {
                    posIcon.setImageBitmap(player.icon)
                } else {
                    posIcon.setImageBitmap(
                        PositionsIconFactory.getPositionIcon(context, player.position)?.toBitmap()
                    )
                    player.icon =
                        PositionsIconFactory.getPositionIcon(context, player.position)?.toBitmap()
                }
                setFavourite(player)

                favoutite.setOnClickListener {
                    player.favourite = !player.favourite
                    notifyItemChanged(position)
                    presenter.updatePlayer(player)
                }

                posIcon.setOnClickListener {
                    val rxPermissions = RxPermissions(fragment)
                    rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe {
                            if (it) {
                                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                this@PlayersPageFragment.position = position
                                startActivityForResult(takePicture, CAMERA_REQUEST_CODE)
                            }
                        }
                }
            }
        }

        private fun ItemPlayerBinding.setFavourite(player: Player) {
            favoutite.setImageDrawable(
                if (player.favourite) ContextCompat.getDrawable(context, R.drawable.ic_star_yellow)
                else ContextCompat.getDrawable(context, R.drawable.ic_star_white)
            )
        }

        fun setData(list: List<Player>) {
            val diffResult = DiffUtil.calculateDiff(DiffCallback(data, list))
            data.clear()
            data.addAll(list)
            diffResult.dispatchUpdatesTo(this)
        }

        fun setImageAtPosition(bitmap: Bitmap) {
            data[position].icon = bitmap
            notifyItemChanged(position)
        }

        override fun getItemCount() = data.size

        inner class ViewHolder(val binding: ItemPlayerBinding) :
            RecyclerView.ViewHolder(binding.root)

        inner class DiffCallback(
            private val oldData: List<Player>,
            private val newData: List<Player>
        ) : DiffUtil.Callback() {

            override fun getOldListSize(): Int = oldData.size

            override fun getNewListSize(): Int = newData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition] == newData[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldPlayer = oldData[oldItemPosition]
                val newPlayer = newData[newItemPosition]
                return oldPlayer.name == newPlayer.name
                        && oldPlayer.favourite == newPlayer.favourite
                        && oldPlayer.club == newPlayer.club
                        && oldPlayer.nationality == newPlayer.nationality
                        && oldPlayer.position == newPlayer.position
            }
        }
    }
}
