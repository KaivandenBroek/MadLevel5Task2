package com.example.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM GameTable")
    fun getGames(): LiveData<Game?>

    @Insert
    suspend fun addGame(game: Game)

    @Query("DELETE FROM GameTable")
    suspend fun deleteAllGames()

}