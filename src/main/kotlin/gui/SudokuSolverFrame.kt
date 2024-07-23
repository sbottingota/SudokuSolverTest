package com.sbottingota.sudokusolvertest.gui

import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JFrame

class SudokuSolverFrame : JFrame("Sudoku Solver") {
    init {
        val board = SudokuBoard()
        val button = JButton("Print Board Contents")
        button.addActionListener {
            for (row in board.getBoard()) {
                for (value in row) {
                    print("$value ")
                }
                println()
            }
            println()
        }

        layout = BorderLayout()
        add(board, BorderLayout.CENTER)
        add(button, BorderLayout.SOUTH)

        setSize(WINDOW_SIZE, WINDOW_SIZE)
    }
}