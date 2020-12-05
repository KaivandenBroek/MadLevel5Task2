package com.example.madlevel5task2

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_game.*
import kotlinx.android.synthetic.main.game_card.*
import java.lang.Long.parseLong
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle(R.string.add_name)
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)



        fab2.setOnClickListener {
            findNavController().navigate(R.id.action_addGameFragment_to_gameBacklogFragment)
            saveGame()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_addgame, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back_added -> {
                findNavController().navigate(R.id.action_addGameFragment_to_gameBacklogFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveGame() {
        //
        val stringDate: String = tfAddDay.text.toString() + tfAddDay.text.toString() + tfAddDay.text.toString()
        val longDate: Long = parseLong(stringDate, 10)
        val date = Date(longDate)
        viewModel.updateNote(tfAddTitle.text.toString(), tfAddPlatform.text.toString(), date)
    }
}