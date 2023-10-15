package com.example.midtermproj


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Highscore::class], version = 1)
abstract class HighscoreDatabaseImpl: RoomDatabase() {
    abstract val highscoreDao: HighscoreDao
    companion object {
        @Volatile
        private var INSTANCE: HighscoreDatabaseImpl? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): HighscoreDatabaseImpl {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, HighscoreDatabaseImpl::class.java, "highscore_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}