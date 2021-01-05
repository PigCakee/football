package com.example.football.ui.clubs;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.football.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_)
    ConstraintLayout container;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.club_)
    TextView club;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.players_)
    TextView players;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
