package com.github.petrchatrny.puzzle8.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.petrchatrny.puzzle8.model.entities.Algorithm
import com.github.petrchatrny.puzzle8.model.entities.Attempt
import com.github.petrchatrny.puzzle8.model.entities.Direction
import com.github.petrchatrny.puzzle8.model.entities.Matrix
import com.github.petrchatrny.puzzle8.view.GridFragmentCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GridViewModel : ViewModel() {
    lateinit var callback: GridFragmentCallback
    lateinit var algorithm: Algorithm
    var matrix = MutableLiveData(Matrix())
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
        matrix.value = Matrix()
    }

    private fun bfs(start: Matrix, iterations: Int): Attempt? {
        val queue: Queue<Attempt> = LinkedList()
        val explored = mutableListOf<Attempt>()
        this.iterations.postValue(iterations.toString())
        queue.add(Attempt(0, start, Direction.NONE, null))

        while (queue.isNotEmpty() && counter.value!! <= iterations) {
            val current = queue.remove()
            matrix.postValue(current.matrix)
            explored.add(current)

            if (current.matrix.isSolved()) {
                return current
            }

            for (neighbor in current.getNeighbours()) {
                if (neighbor !in queue && neighbor !in explored) {
                    queue.add(neighbor)
                }
            }
            counter.postValue(counter.value?.plus(1))
        }
        return null
    }

    private fun dfs(start: Matrix, iterations: Int): Attempt? {
        val stack: Stack<Attempt> = Stack()
        val explored = mutableListOf<Attempt>()
        this.iterations.postValue(iterations.toString())
        stack.add(Attempt(0, start, Direction.NONE, null))

        while (stack.isNotEmpty() && counter.value!! <= iterations) {
            val current = stack.pop()
            matrix.postValue(current.matrix)
            explored.add(current)

            if (current.matrix.isSolved()) {
                return current
            }

            for (neighbor in current.getNeighbours()) {
                if (neighbor !in stack && neighbor !in explored) {
                    stack.add(neighbor)
                }
            }
            counter.postValue(counter.value?.plus(1))
        }
        return null
    }
}