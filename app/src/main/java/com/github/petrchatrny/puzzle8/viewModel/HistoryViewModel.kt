package com.github.petrchatrny.puzzle8.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.petrchatrny.puzzle8.model.databases.AttemptResultDatabase
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult
import com.github.petrchatrny.puzzle8.model.repositories.AttemptResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(app: Application) : AndroidViewModel(app) {
    val allAttemptResults: LiveData<List<AttemptResult>>
    private val repository: AttemptResultRepository

    init {
        val dao = AttemptResultDatabase.getDatabase(app).attemptResultDao()
        repository = AttemptResultRepository(dao)
        allAttemptResults = repository.allAttemptResults
    }

    fun insertAttemptResult(attemptResult: AttemptResult) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAttemptResult(attemptResult)
            Thread.sleep(1000)
        }
    }

    fun deleteAllAttemptResults() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllAttemptResults()
        }
    }

}