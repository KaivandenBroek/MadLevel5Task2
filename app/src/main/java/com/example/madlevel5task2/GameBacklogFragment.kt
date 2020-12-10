package com.example.madlevel5task2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game_backlog.*

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

        gamesListAdapter = GamesListAdapter(games, requireContext())

        viewManager = LinearLayoutManager(activity)
        rvGames.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGames.adapter = gamesListAdapter
        observeAddGameResult()
        createItemTouchHelper().attachToRecyclerView(rvGames)
    }

    private fun observeAddGameResult() {
        viewModel.games.observe(viewLifecycleOwner, { game ->
            games.clear()
            games.addAll(game)
            games.sortByDescending { game: Game ->  game.releaseDate }
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

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]
                games.removeAt(position)
                viewModel.deleteGame(gameToDelete)
                gamesListAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

}