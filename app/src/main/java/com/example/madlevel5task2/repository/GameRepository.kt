package com.example.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.database.GameRoomDatabase
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.dao.GameDao

class GameRepository(context: Context) {

    private val gameDao: GameDao?

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database?.gameDao()
    }

    fun getGames(): LiveData<List<Game>> {
        return gameDao?.getGames() ?: MutableLiveData(emptyList())
    }

    suspend fun addGameBacklog(game: Game) = gameDao?.addGame(game)

    suspend fun deleteGame(game: Game) = gameDao?.deleteGame(game)

    suspend fun deleteAllGames() = gameDao?.deleteAllGames()
}