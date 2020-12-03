package com.example.madlevel5task2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface gameDao {

    @Insert
    suspend fun insertGame(game: Game)

}