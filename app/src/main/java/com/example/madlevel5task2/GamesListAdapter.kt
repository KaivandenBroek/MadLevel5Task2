package com.example.madlevel5task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.game_card.view.*

class GamesListAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GamesListAdapter.ViewHolder>()
{
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun databind(game : Game) {
                itemView.tvGameName.text = game.title
                itemView.tvPlatform.text = game.platform
                itemView.tvReleaseDate.text = game.releaseDate.toString()
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
        holder.databind(games[position])
    }
}