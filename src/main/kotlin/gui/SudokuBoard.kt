package com.sbottingota.sudokusolvertest.gui

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH
import java.awt.Color.WHITE
import java.awt.Font
import java.awt.GridLayout
import java.util.regex.Pattern
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities

class SudokuBoard : JPanel() {
    private val board: Array<Array<JTextField>>;

    init {
        background = WHITE
        layout = GridLayout(BOARD_SIDE_LENGTH, BOARD_SIDE_LENGTH)

        board = Array(BOARD_SIDE_LENGTH) { Array(BOARD_SIDE_LENGTH) { JTextField() } }

        for (row in board) {
            for (cell in row) {
                cell.setSize(CELL_SIZE, CELL_SIZE)
                cell.addCaretListener {
                    if (!cell.text.matches(CELL_VALUE_PATTERN)) {
                        SwingUtilities.invokeLater { cell.text = "" }
                    }
                }
                add(cell)
            }
        }
    }

    /**
     * @return The board, represented as a matrix.
     */
    fun getBoard(): Array<Array<Int>> {
        val ret = Array(BOARD_SIDE_LENGTH) { Array(BOARD_SIDE_LENGTH) { 0 } } // init matrix of 0s

        for ((i, row) in board.withIndex()) {
            for ((j, cell) in row.withIndex()) {
                if (cell.text != "") {
                    ret[i][j] = cell.text.toInt()
                }
            }
        }

        return ret
    }
}