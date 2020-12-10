package com.example.madlevel5task2

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.model.Game
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_game.*
import java.text.ParseException
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

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
            if(validateFields()) {
                saveGame()
                findNavController().navigate(R.id.action_addGameFragment_to_gameBacklogFragment)
            }
        }
    }

    private fun validateFields(): Boolean {
        if (tfAddTitle.text.isNullOrEmpty()) {
            Snackbar.make(requireView(), R.string.noTitle, Snackbar.LENGTH_SHORT)
                .show()
            return false
        }

        if (tfAddPlatform.text.isNullOrEmpty()) {
            Snackbar.make(requireView(), R.string.noPlatform, Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        if(tfAddDay.text.isNullOrEmpty() || tfAddMonth.text.isNullOrEmpty() ||
            tfAddYear.text.isNullOrEmpty() || tfAddMonth.text.toString().toInt() > 12) {
            Snackbar.make(requireView(), R.string.noValidDate, Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        return true
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
        val stringDate: String = tfAddDay.text.toString() + "-" +
                tfAddMonth.text.toString() + "-" + tfAddYear.text.toString()

        val date: Date
        date = formatter.parse(stringDate)

        val game = Game(
            title = tfAddTitle.text.toString(),
            platform = tfAddPlatform.text.toString(),
            releaseDate = date
        )

        viewModel.addGame(game)
    }
}