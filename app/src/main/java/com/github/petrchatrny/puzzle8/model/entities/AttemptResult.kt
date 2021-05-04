package com.github.petrchatrny.puzzle8.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.petrchatrny.puzzle8.model.enums.Algorithm
import java.io.Serializable
import java.util.*

@Entity(tableName = "attempt_result_table")
data class AttemptResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val status: Boolean,
    val totalSteps: Int,
    val steps: List<IntArray>?,
    val algorithm: Algorithm,
    val timestamp: Date
) : Serializable
