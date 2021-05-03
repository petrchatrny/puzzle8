package com.github.petrchatrny.puzzle8.model.repositories

import androidx.lifecycle.LiveData
import com.github.petrchatrny.puzzle8.model.dao.AttemptResultDao
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult

class AttemptResultRepository(private val dao: AttemptResultDao) {
    val allAttemptResults: LiveData<List<AttemptResult>> = dao.getAllAttemptResults()

    suspend fun insertAttemptResult(attemptResult: AttemptResult) {
        dao.insertAttemptResult(attemptResult)
    }

    suspend fun deleteAllAttemptResults() {
        dao.deleteAllAttemptResults()
    }
}