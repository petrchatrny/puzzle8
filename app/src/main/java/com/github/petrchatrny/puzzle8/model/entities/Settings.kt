package com.github.petrchatrny.puzzle8.model.entities

data class Settings(private var _layout: String, private var _goal: String) {
    var layout = _layout
        set(value) {
            field = value
            _layout = value
        }

    var goal = _goal
        set(value) {
            field = value
            _goal = value
        }
}

