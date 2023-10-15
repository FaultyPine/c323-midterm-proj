package com.example.midtermproj

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "highscore_table")
data class Highscore (
    @PrimaryKey(autoGenerate = true)
    var highscoreId: Int = 0,
    @ColumnInfo(name="player_name")
    var playerName: String = "NoPlayer",
    @ColumnInfo(name="highscore_attempts")
    var numAttempts: Int = 0,
)