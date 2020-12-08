package com.example.madlevel5task2

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_game.*
import java.text.ParseException
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
            saveGame()
            findNavController().navigate(R.id.action_addGameFragment_to_gameBacklogFragment)
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
        val stringDate: String = tfAddDay.text.toString() +"-" +
                tfAddMonth.text.toString()+"-" + tfAddYear.text.toString()
        val date: Date
//        val df = SimpleDateFormat("dd-mm-yyyy", Locale.UK)
//        val date: Date? = df.parse(stringDate)
        date = screwDates(stringDate)
//        try { // date validator
//            val date:Date = df.parse(stringDate)
//        } catch (e: ParseException) {
//            //TODO SNACKBAR error
//            Log.e(e.toString(), "NOT LEGAL DATE")
//        }

            viewModel.addGame(tfAddTitle.text.toString(), tfAddPlatform.text.toString(), date)

    }

    @Throws(ParseException::class)
    fun screwDates(dateStr: String): Date {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.parse(dateStr)
    }
}