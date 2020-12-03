package com.example.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM GameTable LIMIT 1")
    fun getGames(): LiveData<Game?>

    @Update
    suspend fun updateNote(note: Game)

}