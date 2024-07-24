package com.sbottingota.sudokusolvertest.gui

import com.sbottingota.sudokusolvertest.game.Game
import com.sbottingota.sudokusolvertest.solver.StateSolver
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JFrame

class SudokuSolverFrame : JFrame("Sudoku Solver") {
    init {
        val board = SudokuBoard()

        val solveButton = JButton("Solve")
        val clearButton = JButton("Clear")

        solveButton.addActionListener { solve(board) }
        clearButton.addActionListener { board.clear() }

        layout = GridBagLayout()

        val boardConstraints = GridBagConstraints()
        boardConstraints.gridx = 0
        boardConstraints.gridy = 0
        boardConstraints.gridheight = 2
        add(board, boardConstraints)

        val buttonConstraints = GridBagConstraints()
        buttonConstraints.gridx = 1
        buttonConstraints.gridy = 0
        buttonConstraints.insets = BUTTONS_INSET
        buttonConstraints.anchor = GridBagConstraints.NORTH

        add(solveButton, buttonConstraints)

        buttonConstraints.gridy++
        add(clearButton, buttonConstraints)

        setSize(WINDOW_SIZE, WINDOW_SIZE)
    }

    fun solve(board: SudokuBoard) {
        val solver = StateSolver()

        val game: Game
        try {
            game = Game(board.getBoardValues())
        } catch (_: IllegalArgumentException) { // illegal board configuration
            return
        }
        solver.solve(game)

        board.setBoardValues(game.board)
    }
}