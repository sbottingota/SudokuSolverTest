package com.sbottingota.sudokusolvertest.solver

import com.sbottingota.sudokusolvertest.game.Game

interface SudokuSolver {
    /**
     * @param game The game to solve.
     * @return Whether the function managed to successfully solve the game.
     */
    fun solve(game: Game): Boolean
}