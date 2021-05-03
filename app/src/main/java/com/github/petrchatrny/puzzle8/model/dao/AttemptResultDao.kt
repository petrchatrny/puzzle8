package com.github.petrchatrny.puzzle8.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult

@Dao
interface AttemptResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAttemptResult(attemptResult: AttemptResult)

    @Query("SELECT * FROM attempt_result_table ORDER BY id ASC")
    fun getAllAttemptResults(): LiveData<List<AttemptResult>>

    @Query("DELETE FROM attempt_result_table")
    suspend fun deleteAllAttemptResults()

}