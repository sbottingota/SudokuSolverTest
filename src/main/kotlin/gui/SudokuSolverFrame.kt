package com.sbottingota.sudokusolvertest.gui

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JFrame

class SudokuSolverFrame : JFrame("Sudoku Solver") {
    init {
        val board = SudokuBoard()
        val solveButton = JButton("Solve")
        val clearButton = JButton("Clear")
        solveButton.addActionListener {
            for (row in board.getBoard()) {
                for (value in row) {
                    print("$value ")
                }
                println()
            }
            println()
        }
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

}