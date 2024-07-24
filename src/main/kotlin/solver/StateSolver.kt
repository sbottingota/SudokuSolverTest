package com.sbottingota.sudokusolvertest.solver

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH
import com.sbottingota.sudokusolvertest.game.EMPTY_SQUARE_VALUE
import com.sbottingota.sudokusolvertest.game.Game

class StateSolver {
    /**
     * @param game The game to solve.
     * @return Whether the function managed to successfully solve the game.
     */
    fun solve(game: Game): Boolean {
        if (game.isSolved()) return true

        val (x, y) = findEmptySquare(game)!!

        for (newVal in 1..BOARD_SIDE_LENGTH) {
            try {
                game.move(x, y, newVal)

                if (solve(game)) {
                    return true
                }

                game.unmove()
            } catch (_: IllegalArgumentException) {}


        }

        return false
    }

    /**
     * Finds the first empty square in the game.
     * @param game The game to search for the empty squares for
     * @return A [Pair] representing the indices of the empty square.
     */
    private fun findEmptySquare(game: Game): Pair<Int, Int>? {
        for (x in 0..<BOARD_SIDE_LENGTH) {
            for (y in 0..<BOARD_SIDE_LENGTH) {
                if (game.board[x][y] == EMPTY_SQUARE_VALUE) return Pair(x, y)
            }
        }

        return null
    }
}