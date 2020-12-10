package com.example.madlevel5task2

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.game_card.view.*
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class GamesListAdapter(private val games: ArrayList<Game>, val context: Context) :
    RecyclerView.Adapter<GamesListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(game: Game) {
            itemView.tvGameName.text = game.title
            itemView.tvPlatform.text = game.platform
            val format = SimpleDateFormat("dd MMM yyyy")
            val date = format.format(game.releaseDate)
            itemView.tvReleaseDate.text = context.getString(R.string.release, date.toString())
            //itemView.tvReleaseDate.text = getContext().getString(R.string.release, date.toString())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_card, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return games.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }
}