package com.github.petrchatrny.puzzle8.model.entities

import com.github.petrchatrny.puzzle8.model.enums.Direction
import kotlin.random.Random

class Matrix {
    var body: Array<IntArray> = arrayOf(IntArray(3), IntArray(3), IntArray(3))
    lateinit var pos: Pair<Int, Int>

    init {
        val alreadyUsed = mutableListOf<Int>()
        var xPosition = 0
        var yPosition = 0

        while (alreadyUsed.size < 9) {
            val random: Int = Random.nextInt(0, 9)
            if (random in alreadyUsed) {
                continue
            } else {
                // save position of 0
                if (random == 0) {
                    pos = Pair(xPosition, yPosition)
                }

                body[yPosition][xPosition] = random
                alreadyUsed.add(random)
                xPosition++

                if (xPosition > 2) {
                    yPosition++
                    xPosition = 0
                }
            }
        }
    }

    fun toIntArray(): IntArray {
        val result = IntArray(9)
        var counter = 0
        for (array: IntArray in body) {
            for (item: Int in array) {
                result[counter] = item
                counter++
            }
        }
        return result
    }

    fun moveSpace(d: Direction): Boolean {
        when (d) {
            Direction.UP -> {
                if (pos.second != 0) {
                    // swap values
                    body.apply {
                        this[pos.second][pos.first] = this[pos.second - 1][pos.first]
                        this[pos.second - 1][pos.first] = 0
                    }

                    // change position
                    pos = Pair(pos.first, pos.second - 1)
                    return true
                }
                return false
            }
            Direction.DOWN -> {
                if (pos.second != 2) {
                    // swap values
                    body.apply {
                        this[pos.second][pos.first] = this[pos.second + 1][pos.first]
                        this[pos.second + 1][pos.first] = 0
                    }

                    // change position
                    pos = Pair(pos.first, pos.second + 1)
                    return true
                }
                return false
            }
            Direction.LEFT -> {
                if (pos.first != 0) {
                    // swap values
                    body.apply {
                        this[pos.second][pos.first] = this[pos.second][pos.first - 1]
                        this[pos.second][pos.first - 1] = 0
                    }

                    // change position
                    pos = Pair(pos.first - 1, pos.second)
                    return true
                }
                return false
            }
            Direction.RIGHT -> {
                if (pos.first != 2) {
                    // swap values
                    body.apply {
                        this[pos.second][pos.first] = this[pos.second][pos.first + 1]
                        this[pos.second][pos.first + 1] = 0
                    }

                    // change position
                    pos = Pair(pos.first + 1, pos.second)
                    return true
                }
                return false
            }
            Direction.NONE -> {
                return true
            }
        }
    }

    fun swap(coords: Pair<Int, Int>) {
        // down
        if (coords.second + 1 < 3 && body[coords.second + 1][coords.first] == 0) {
            body.apply {
                this[coords.second + 1][coords.first] = this[coords.second][coords.first]
                this[coords.second][coords.first] = 0
                pos = coords
            }
            return
        }

        // up
        if (coords.second - 1 > -1 && body[coords.second - 1][coords.first] == 0) {
            body.apply {
                this[coords.second - 1][coords.first] = this[coords.second][coords.first]
                this[coords.second][coords.first] = 0
                pos = coords
            }
            return
        }

        // right
        if (coords.first + 1 < 3 && body[coords.second][coords.first + 1] == 0) {
            body.apply {
                this[coords.second][coords.first + 1] = this[coords.second][coords.first]
                this[coords.second][coords.first] = 0
                pos = coords
            }
            return
        }

        // left
        if (coords.first - 1 > -1 && body[coords.second][coords.first - 1] == 0) {
            body.apply {
                this[coords.second][coords.first - 1] = this[coords.second][coords.first]
                this[coords.second][coords.first] = 0
                pos = coords
            }
            return
        }
    }

    fun isSolvable(): Boolean {
        return getInversionCount() % 2 == 0
    }

    fun copy(): Matrix {
        val m = Matrix()
        m.body = arrayOf(body[0].copyOf(), body[1].copyOf(), body[2].copyOf())
        m.pos = Pair(pos.first, pos.second)
        return m
    }

    fun getCoordsByNumber(number: Int): Pair<Int, Int> {
        var coords = Pair(-2, -2)

        for (i in 0..2) {
            for (j in 0..2) {
                if (body[i][j] == number) {
                    coords = Pair(j, i)
                    break
                }
            }
        }
        return coords
    }

    private fun getInversionCount(): Int {
        var inversionCount = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (body[i][j] != 0 && body[j][i] > body[i][j]) {
                    inversionCount++
                }
            }
        }
        return inversionCount
    }
}