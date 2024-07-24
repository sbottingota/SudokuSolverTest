package com.sbottingota.sudokusolvertest.game

class Game(private val board: Array<Array<Int>>) {
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
     * @param x The x coord for the move.
     * @param y The y coord of the move.
     * @param newVal The value to put at the specified position.
     * @throws IllegalArgumentException If the move is not valid.
     */
    fun move(x: Int, y: Int, newVal: Int) {
        if (!isValidMove(x, y, newVal)) {
            throw IllegalArgumentException("Invalid move")
        }

        board[x][y] = newVal
    }

    /**
     * Clears the specified square.
     */
    fun clear(x: Int, y: Int) {
        board[x][y] = EMPTY_SQUARE_VALUE
    }

    private fun isValidMove(x: Int, y: Int, newVal: Int): Boolean {
        if (board[x][y] != EMPTY_SQUARE_VALUE) return false

        // temporarily apply changes to see if they are valid
        board[x][y] = newVal
        val ret = isValidBoard(board)
        board[x][y] = EMPTY_SQUARE_VALUE

        return ret
    }

    /**
     * @return If the state is fully solved; i.e. if there are no blank spaces.
     */
    fun isSolved(): Boolean {
        for (row in board) {
            if (row.contains(EMPTY_SQUARE_VALUE)) return false
        }

        return true
    }

    /**
     * @return A copy of the board, as an int matrix.
     */
    fun getBoard(): Array<Array<Int>> {
        return board.copyOf() // copy so that board cannot be updated from outside the class
    }

    companion object {
        /**
         * Check if a board is valid (there is at most one number per row, column, or box).
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