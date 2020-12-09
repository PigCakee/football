package com.example.football.model.player

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.football.R

class PositionsIconFactory {
    companion object {
        fun getPositionIcon(context: Context, position: String): Drawable? {
            return when (position) {
                context.getString(R.string.goalkeeper) -> ContextCompat.getDrawable(context, R.drawable.ic_goalkeeper)
                context.getString(R.string.midfield) -> ContextCompat.getDrawable(context, R.drawable.ic_middlefield)
                context.getString(R.string.defender) -> ContextCompat.getDrawable(context, R.drawable.ic_defender)
                context.getString(R.string.forward) -> ContextCompat.getDrawable(context, R.drawable.ic_forward)
                else ->  ContextCompat.getDrawable(context, R.drawable.ic_goalkeeper)
            }
        }
    }
}