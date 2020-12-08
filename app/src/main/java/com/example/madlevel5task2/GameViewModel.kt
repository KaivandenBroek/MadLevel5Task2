package com.example.madlevel5task2

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class GameViewModel(application: Application) : AndroidViewModel(application){

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val games = gameRepository.getGames()

    private val error = MutableLiveData<String>()
    private val success = MutableLiveData<Boolean>()

    fun addGame(title: String, platform: String, date: Date) {

        //if there is an existing note, take that id to update it instead of adding a new one
        val newGame = Game(
                title = title,
                platform = platform,
                releaseDate = date
        )
        Log.v("date-test" , date.toString())

        if(isGameValid(newGame)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.addGameBacklog(newGame)
                    Log.i("database", "added game to database: $newGame")
                }
                success.value = true
            }
        }
    }

    private fun isGameValid(game: Game): Boolean {
        return when {
            game.title.isBlank() -> {
                error.value = "Please fill in a title"
                false
            }
            else -> true
        }
    }

}