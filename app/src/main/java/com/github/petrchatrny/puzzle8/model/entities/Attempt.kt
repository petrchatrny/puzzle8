package com.github.petrchatrny.puzzle8.model.entities

import com.github.petrchatrny.puzzle8.model.enums.Direction

class Attempt(
    val number: Int,
    val matrix: Matrix,
    private val lastMove: Direction,
    val parent: Attempt?
) {
    fun getNeighbours(): List<Attempt> {
        val result = mutableListOf<Attempt>()

        var mMatrix: Matrix = matrix.copy()

        if (lastMove != Direction.DOWN && mMatrix.moveSpace(Direction.UP)) {
            result.add(Attempt(number + 1, mMatrix, Direction.UP, this))
            mMatrix = matrix.copy()
        }

        if (lastMove != Direction.UP && mMatrix.moveSpace(Direction.DOWN)) {
            result.add(Attempt(number + 1, mMatrix, Direction.DOWN, this))
            mMatrix = matrix.copy()
        }

        if (lastMove != Direction.LEFT && mMatrix.moveSpace(Direction.RIGHT)) {
            result.add(Attempt(number + 1, mMatrix, Direction.RIGHT, this))
            mMatrix = matrix.copy()
        }

        if (lastMove != Direction.RIGHT && mMatrix.moveSpace(Direction.LEFT)) {
            result.add(Attempt(number + 1, mMatrix, Direction.LEFT, this))
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other is Attempt) {
            return matrix.toIntArray() contentEquals other.matrix.toIntArray()
        }
        return false
    }

    override fun hashCode(): Int {
        var result = number
        result = 31 * result + matrix.hashCode()
        result = 31 * result + lastMove.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }
}


