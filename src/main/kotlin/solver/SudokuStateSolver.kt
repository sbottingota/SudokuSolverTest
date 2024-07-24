package com.sbottingota.sudokusolvertest.solver

import com.sbottingota.sudokusolvertest.game.SudokuState
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class SudokuStateSolver {
    fun solve(initialState: SudokuState): SudokuState {
        if (initialState.isSolved()) return initialState

        val statesToVisit: Queue<SudokuState> = ConcurrentLinkedQueue()
        statesToVisit.add(initialState)

        val visitedStateHashes = ArrayList<Int>()
        visitedStateHashes.add(initialState.hashCode())

        while (true) {
            val selectedState = statesToVisit.remove()
            val nextStates = selectedState.getPossibleMoves().filter { state -> state.hashCode() !in visitedStateHashes }

            for (nextState in nextStates) {
                if (nextState.isSolved()) return nextState
            }

            statesToVisit.addAll(nextStates)

            println(statesToVisit.size)
        }
    }
}