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
import java.util.*

class GameViewModel(application: Application) : AndroidViewModel(application){

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val games = gameRepository.getGames()

    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun updateNote(title: String, platform: String, date: Date) {

        //if there is an existing note, take that id to update it instead of adding a new one
        val newNote = Game(
                title = title,
                platform = platform,
                releaseDate = date
        )
        Log.v("test" , title)

        if(isGameValid(newNote)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.updateNotepad(newNote)
                }
                success.value = true
            }
        }
    }

    private fun isGameValid(game: Game): Boolean {
        return when {
            game.title.isBlank() -> {
                error.value = "Title game must not be empty"
                false
            }
            else -> true
        }
    }

}