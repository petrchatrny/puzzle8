package com.github.petrchatrny.puzzle8.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.petrchatrny.puzzle8.model.databases.AttemptResultDatabase
import com.github.petrchatrny.puzzle8.model.entities.Attempt
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult
import com.github.petrchatrny.puzzle8.model.entities.Matrix
import com.github.petrchatrny.puzzle8.model.entities.Settings
import com.github.petrchatrny.puzzle8.model.enums.Algorithm
import com.github.petrchatrny.puzzle8.model.enums.Direction
import com.github.petrchatrny.puzzle8.model.repositories.AttemptResultRepository
import com.github.petrchatrny.puzzle8.model.repositories.SettingsRepository
import com.github.petrchatrny.puzzle8.view.GridFragmentCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class GridViewModel(app: Application) : AndroidViewModel(app) {
    private val attemptResultRepository = AttemptResultRepository(
        AttemptResultDatabase.getDatabase(app).attemptResultDao()
    )
    private val settingsRepository = SettingsRepository(app)
    lateinit var callback: GridFragmentCallback

    lateinit var algorithm: Algorithm
    var matrix = MutableLiveData<Matrix>()
    var counter = MutableLiveData<Int>()
    var iterations = MutableLiveData<String>()

    fun solvePuzzle() {
        var error = false
        counter.value = 0

        // validate inputs
        if (iterations.value == null || iterations.value == "") {
            callback.onIterationsError()
            error = true
        }

        if (!this::algorithm.isInitialized) {
            callback.onAlgorithmError()
            error = true
        }

        if (error) {
            return
        }

        // execute algorithm
        callback.onSolving()
        when (algorithm) {
            Algorithm.BFS -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        bfs(matrix.value!!, iterations.value!!.toInt())
                        withContext(Dispatchers.Main) {
                            callback.onSolved()
                        }
                    }
                }
            }
            Algorithm.DFS -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        dfs(matrix.value!!, iterations.value!!.toInt())
                        withContext(Dispatchers.Main) {
                            callback.onSolved()
                        }
                    }
                }
            }
        }
    }

    fun randomPuzzle() {
        viewModelScope.launch {
            var mMatrix = Matrix()
            while (!mMatrix.isSolvable()) {
                mMatrix = Matrix()
            }
            matrix.postValue(mMatrix)
        }
    }

    private fun bfs(start: Matrix, iterations: Int) {
        val queue: Queue<Attempt> = LinkedList()
        val explored = mutableListOf<Attempt>()
        this.iterations.postValue(iterations.toString())
        queue.add(Attempt(0, start, Direction.NONE, null))

        while (queue.isNotEmpty() && counter.value!! <= iterations) {
            val current = queue.remove()
            matrix.postValue(current.matrix)
            explored.add(current)

            if (isMatrixSolved(current.matrix)) {
                saveResult(current, Algorithm.BFS)
                return
            }

            for (neighbor in current.getNeighbours()) {
                if (neighbor !in queue && neighbor !in explored) {
                    queue.add(neighbor)
                }
            }
            counter.postValue(counter.value?.plus(1))
        }
        saveResult(null, Algorithm.BFS)
    }

    private fun dfs(start: Matrix, iterations: Int) {
        val stack: Stack<Attempt> = Stack()
        val explored = mutableListOf<Attempt>()
        this.iterations.postValue(iterations.toString())
        stack.add(Attempt(0, start, Direction.NONE, null))

        while (stack.isNotEmpty() && counter.value!! <= iterations) {
            val current = stack.pop()
            matrix.postValue(current.matrix)
            explored.add(current)

            if (isMatrixSolved(current.matrix)) {
                saveResult(current, Algorithm.DFS)
                return
            }

            for (neighbor in current.getNeighbours()) {
                if (neighbor !in stack && neighbor !in explored) {
                    stack.add(neighbor)
                }
            }
            counter.postValue(counter.value?.plus(1))
        }
        saveResult(null, Algorithm.DFS)
    }

    private fun saveResult(attempt: Attempt?, algorithm: Algorithm) {
        val attemptResult: AttemptResult

        if (attempt != null) {
            attemptResult = AttemptResult(
                id = 0,
                status = true,
                totalSteps = counter.value!!,
                steps = tracertResult(attempt),
                algorithm = algorithm,
                Date()
            )
        } else {
            attemptResult = AttemptResult(
                id = 0,
                status = false,
                totalSteps = counter.value!!,
                steps = null,
                algorithm = algorithm,
                Date()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            attemptResultRepository.insertAttemptResult(attemptResult)
        }
    }

    private fun tracertResult(attempt: Attempt): List<IntArray> {
        val path = mutableListOf<IntArray>()
        var temp = attempt
        path.add(temp.matrix.toIntArray())
        while (temp.parent != null) {
            path.add(temp.parent!!.matrix.toIntArray())
            temp = temp.parent!!
        }
        return path.asReversed()
    }

    private fun isMatrixSolved(matrix: Matrix): Boolean {
        var string = ""
        matrix.toIntArray().map {
            string += it.toString()
        }

        val flowResult: Settings
        runBlocking {
            flowResult = settingsRepository.getSettings().first()
        }

        return string == flowResult.goal
    }
}