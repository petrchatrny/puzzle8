package com.github.petrchatrny.puzzle8.view

interface GridFragmentCallback {
    fun onSolving()
    fun onAlgorithmError()
    fun onIterationsError()
    fun onSolved()
}