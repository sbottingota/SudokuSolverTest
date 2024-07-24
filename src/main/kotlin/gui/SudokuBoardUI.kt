package com.sbottingota.sudokusolvertest.gui

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH
import com.sbottingota.sudokusolvertest.game.BOX_SIDE_LENGTH
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities

class SudokuBoardUI : JPanel() {
    private val board: Array<Array<JTextField>>;

    init {
        background = BOARD_COLOR
        border = BOARD_BORDER

        layout = GridBagLayout()

        board = Array(BOARD_SIDE_LENGTH) { Array(BOARD_SIDE_LENGTH) { JTextField() } }

        for ((x, row) in board.withIndex()) {
            for ((y, cell) in row.withIndex()) {
                cell.font = CELL_FONT

                // make sure you can only input legal values into cells
                cell.addCaretListener {
                    if (!cell.text.matches(CELL_VALUE_PATTERN)) {
                        if (cell.text[0].toString().matches(CELL_VALUE_PATTERN)) {
                            SwingUtilities.invokeLater {
                                cell.text = cell.text[0].toString()
                            }
                        } else {
                            SwingUtilities.invokeLater {
                                cell.text = ""
                            }
                        }
                    }
                }

                val constraints = GridBagConstraints()
                constraints.gridx = x
                constraints.gridy = y

                constraints.ipadx = CELL_SIZE

                val insets = Insets(0, 0, 0, 0)

                if ((x + 1) % BOX_SIDE_LENGTH == 0 && x != BOARD_SIDE_LENGTH - 1) { // if cell is on the right edge of a box, excluding cells not neighboring another box
                    insets.right = BOX_EDGE_INSET_SIZE
                }
                if ((y + 1) % BOX_SIDE_LENGTH == 0 && y != BOARD_SIDE_LENGTH - 1) { // if cell is on the bottom edge of a box, excluding cells not neighboring another box
                    insets.bottom = BOX_EDGE_INSET_SIZE
                }
                constraints.insets = insets
                add(cell, constraints)
            }
        }
    }

    /**
     * @return The board, represented as a matrix.
     */
    fun getBoardValues(): Array<Array<Int>> {
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

    /**
     * Set the board to match the passed in matrix.
     */
    fun setBoardValues(newBoard: Array<Array<Int>>) {
        if (newBoard.size != BOARD_SIDE_LENGTH || newBoard[0].size != BOARD_SIDE_LENGTH) {
            throw IllegalArgumentException("'newBoard' must be of dimensions ${BOARD_SIDE_LENGTH}x${BOARD_SIDE_LENGTH}")
        }

        for (i in 0..<BOARD_SIDE_LENGTH) {
            for (j in 0..<BOARD_SIDE_LENGTH) {
                board[i][j].text = newBoard[i][j].toString()
            }
        }
    }

    /**
     * Clear the board.
     */
    fun clear() {
        for (row in board) {
            for (cell in row) {
                cell.text = ""
            }
        }
    }
}