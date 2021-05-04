package com.github.petrchatrny.puzzle8.collections.onClickListeners

import com.github.petrchatrny.puzzle8.model.entities.AttemptResult

interface OnAttemptClickListener {
    fun onAttemptClick(attempt: AttemptResult)
}