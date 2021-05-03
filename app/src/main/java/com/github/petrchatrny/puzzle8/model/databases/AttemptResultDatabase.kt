package com.github.petrchatrny.puzzle8.model.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.petrchatrny.puzzle8.model.dao.AttemptResultDao
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult
import com.github.petrchatrny.puzzle8.utils.Converters

@Database(entities = [AttemptResult::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AttemptResultDatabase : RoomDatabase() {

    abstract fun attemptResultDao(): AttemptResultDao

    companion object {
        @Volatile
        private var INSTANCE: AttemptResultDatabase? = null

        fun getDatabase(context: Context): AttemptResultDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AttemptResultDatabase::class.java,
                    "attempt_result_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}