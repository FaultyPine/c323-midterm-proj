package com.example.midtermproj

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HighscoreDao {
    @Insert
    suspend fun insert(score: Highscore)
    @Update
    suspend fun update(score: Highscore)
    @Delete
    suspend fun delete(score: Highscore)
    @Query("SELECT * FROM highscore_table WHERE highscoreId = :key")
    fun get(key: Int): LiveData<Highscore>
    @Query("SELECT * FROM highscore_table ORDER BY highscore_attempts ASC")
    fun getAll(): LiveData<List<Highscore>>
}