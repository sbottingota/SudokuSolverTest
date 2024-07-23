package com.sbottingota.sudokusolvertest.game

class SudokuState(val board: Array<Array<Int>>) {
    init {
        if (board.isEmpty()) {
            throw IllegalArgumentException("'board' must have dimensions ${BOARD_SIDE_LENGTH}x${BOARD_SIDE_LENGTH}, but was empty")
        }
        if (board.size != BOARD_SIDE_LENGTH || board[0].size != BOARD_SIDE_LENGTH) {
            throw IllegalArgumentException("'board' must have dimensions ${BOARD_SIDE_LENGTH}x${BOARD_SIDE_LENGTH}, but had dimensions ${board.size}x${board[0].size}")
        }

        for (row in board) {
            for (square in row) {
                if (square != EMPTY_SQUARE_VALUE && square !in 1..BOARD_SIDE_LENGTH) {
                    throw IllegalArgumentException("'board' must only contain the values $EMPTY_SQUARE_VALUE (if the square is empty), or in the range 1-$BOARD_SIDE_LENGTH (inclusive)")
                }
            }
        }

        if (!isValidBoard(board)) {
            throw IllegalArgumentException("'board' contained an illegal configuration (there can only be one of each number, excluding $EMPTY_SQUARE_VALUE, per row, column, and box)")
        }
    }

    /**
     * @param x The x coord for the new move.
     * @param y The y coord for the new move.
     * @param newVal The new value to assign `board[x][y]` for the returned copy of the game state.
     * @return A copy of the current state, with the appropriate move carried out.
     */
    fun moved(x: Int, y: Int, newVal: Int): SudokuState {
        if (newVal == EMPTY_SQUARE_VALUE) {
            throw IllegalArgumentException("Cannot assign square to $EMPTY_SQUARE_VALUE, as that denotes an empty square")
        } else if (board[x][y] != EMPTY_SQUARE_VALUE) {
            throw IllegalArgumentException("Tried to assign value to square which was not empty")
        }

        val newBoard = board.map { it.clone() }.toTypedArray()
        newBoard[x][y] = newVal

        return SudokuState(newBoard)
    }

    /**
     * @return A list of all the possible valid moves from this state.
     */
    fun getPossibleMoves(): List<SudokuState> {
        val possibleMoves = mutableListOf<SudokuState>()

        for (x in 0..<BOARD_SIDE_LENGTH) {
            for (y in 0..<BOARD_SIDE_LENGTH) {
                for (newVal in 1..BOARD_SIDE_LENGTH) {
                    try {
                        val move = moved(x, y, newVal)
                        possibleMoves.add(move)
                    } catch (_: IllegalArgumentException) {
                    } // skip invalid moves
                }
            }
        }

        return possibleMoves
    }

    companion object {

        /**
         * Check if a board is valid (there is at most one number per row, column, or box.
         * @param board The board checked (assumed to be the right dimensions).
         * @return Whether the board is valid.
         */
        private fun isValidBoard(board: Array<Array<Int>>): Boolean {
            for (row in 0..<BOARD_SIDE_LENGTH) {
                if (!isValidSection(board[row])) {
                    return false
                }
            }

            for (col in 0..<BOARD_SIDE_LENGTH) {
                if (!isValidSection(board.map { row -> row[col] }.toTypedArray())) {
                    return false
                }
            }


            for (boxX in 0..BOARD_SIDE_LENGTH - BOX_SIDE_LENGTH step BOX_SIDE_LENGTH) {
                for (boxY in 0..BOARD_SIDE_LENGTH - BOX_SIDE_LENGTH step BOX_SIDE_LENGTH) {
                    val box = board
                        .slice(boxX..<boxX + BOX_SIDE_LENGTH)
                        .map { subarr -> subarr.slice(boxY..<boxY + BOX_SIDE_LENGTH) }
                        .flatten()
                        .toTypedArray()

                    if (!isValidSection(box)) {
                        return false
                    }
                }
            }

            return true
        }

        /**
         * @return If the input has at most one of each item (except for EMPTY_SQUARE_VALUE)
         */
        private fun isValidSection(arr: Array<Int>): Boolean {
            for (n in 1..BOARD_SIDE_LENGTH) {
                if (arr.count { x -> x == n } > 1) {
                    return false
                }
            }

            return true
        }
    }
}