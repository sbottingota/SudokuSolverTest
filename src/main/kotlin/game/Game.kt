package com.sbottingota.sudokusolvertest.game

import java.util.*

class Game(val board: Array<Array<Int>>) {
    private val moveHistory: Stack<Move> = Stack()
    var nMoves = 0
        private set

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
        move(Move(x, y, newVal))
    }

    /**
     * @param move The move to apply.
     * @return A copy of the current state, with the appropriate move carried out.
     * @throws IllegalArgumentException If the move is not valid.
     */
    fun move(move: Move) {
        if (!isValidMove(move)) {
            throw IllegalArgumentException("Invalid move")
        }

        board[move.x][move.y] = move.newVal
        moveHistory.push(move)
        nMoves++;
    }

    /**
     * Reverses the last move
     * @throws EmptyStackException If there is no previous state.
     */
    fun unmove() {
        val lastMove = moveHistory.pop()
        board[lastMove.x][lastMove.y] = EMPTY_SQUARE_VALUE
        nMoves--;
    }

    private fun isValidMove(move: Move): Boolean {
        if (board[move.x][move.y] != EMPTY_SQUARE_VALUE) return false

        // temporarily apply changes to see if they are valid
        board[move.x][move.y] = move.newVal
        val ret = isValidBoard(board)
        board[move.x][move.y] = EMPTY_SQUARE_VALUE

        return ret
    }

    /**
     * @return A list of all the possible valid moves from this state.
     */
    fun getPossibleMoves(): List<Move> {
        val possibleMoves = mutableListOf<Move>()

        for (x in 0..<BOARD_SIDE_LENGTH) {
            for (y in 0..<BOARD_SIDE_LENGTH) {
                for (newVal in 1..BOARD_SIDE_LENGTH) {
                    val move = Move(x, y, newVal)
                    if (isValidMove(move)) {
                        possibleMoves.add(move)
                    }
                }
            }
        }

        return possibleMoves
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

    override fun equals(other: Any?): Boolean {
        if (other !is Game) return false

        return board.contentDeepEquals(other.board)
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
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

    data class Move(val x: Int, val y: Int, val newVal: Int) {
        init {
            if (x !in 0..<BOARD_SIDE_LENGTH) {
                throw IllegalArgumentException("'x' must be between 0 and ${BOARD_SIDE_LENGTH - 1} (inclusive), but was $x")
            }

            if (y !in 0..<BOARD_SIDE_LENGTH) {
                throw IllegalArgumentException("'y' must be between 0 and ${BOARD_SIDE_LENGTH - 1} (inclusive), but was $y")
            }

            if (newVal !in 1..BOARD_SIDE_LENGTH) {
                throw IllegalArgumentException("'newVal' must be between 1 and $BOARD_SIDE_LENGTH (inclusive), but was $newVal")
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Move) return false
            return x == other.x && y == other.y && newVal == other.newVal
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            result = 31 * result + newVal
            return result
        }
    }
}