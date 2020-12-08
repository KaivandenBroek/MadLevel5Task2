package com.example.madlevel5task2

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game_backlog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private lateinit var games: ArrayList<Game>
    private lateinit var gamesListAdapter: GamesListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val viewModel: GameViewModel by viewModels()
//    private val gamesListAdapter = GamesListAdapter(games)
    /*
    De games word aangepast bij het aanmaken van de fragment,
    maar games.add is unreachable omdat die pas in de observer word aangeroepen
    dus, moet ik van games de livedata maken? idk want die kan ik niet aanpassen
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle(R.string.app_name)
        return inflater.inflate(R.layout.fragment_game_backlog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_gameBacklogFragment_to_addGameFragment)
        }

        gameRepository = GameRepository(requireContext())

        initViews()
    }

    private fun initViews() {
        games = arrayListOf()

        gamesListAdapter = GamesListAdapter(games)

        viewManager = LinearLayoutManager(activity)
        rvGames.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGames.adapter = gamesListAdapter
        observeAddGameResult()
    }

    private fun observeAddGameResult() {
        viewModel.games.observe(viewLifecycleOwner, { game ->
            games.clear()
            games.addAll(game)
            gamesListAdapter.notifyDataSetChanged()
        })
    }

    private fun deleteAllGames() {
        viewModel.deleteAllGames()
        gamesListAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_gamebacklog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_backlog -> {
                deleteAllGames()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}