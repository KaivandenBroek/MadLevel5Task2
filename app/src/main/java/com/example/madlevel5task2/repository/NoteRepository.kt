package com.example.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.madlevel5task2.database.GameRoomDatabase
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.dao.GameDao


class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getGames(): LiveData<Game?> {
        return gameDao.getGames()
    }

    suspend fun updateNotepad(note: Game) {
        gameDao.updateNote(note)
    }
}