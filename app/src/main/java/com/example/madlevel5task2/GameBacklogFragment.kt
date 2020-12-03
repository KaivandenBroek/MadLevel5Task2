package com.example.madlevel5task2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_game_backlog.*
import kotlinx.android.synthetic.main.game_card.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    private lateinit var navController: NavController

    private val viewModel: GameViewModel by viewModels()

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
    }

    private fun observeAddGameResult() {
        viewModel.games.observe(viewLifecycleOwner, Observer{ note ->
            note?.let {
                tvGameName.text = it.title
                tvPlatform.text = it.platform
                tvReleaseDate.text = getString(R.string.release, it.releaseDate.toString())
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_gamebacklog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.back_history -> {
//                findNavController().navigate(R.id.fragment_game)
//                return true
//            }
//            R.id.action_history -> {
//                deleteHistory()
//                return true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}