package game

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH
import com.sbottingota.sudokusolvertest.game.SudokuState
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertTrue

class TestSudokuState {
    @Test
    @DisplayName("Given a valid game state, there should be no exceptions in instantiation.")
    fun givenValidState_ShouldNotThrowException() {
        val states = mutableListOf<Array<Array<Int>>>()
        states.add(
            arrayOf(
                arrayOf(0, 1, 0, 0, 0, 0, 6, 7, 0),
                arrayOf(9, 0, 0, 8, 0, 0, 4, 0, 5),
                arrayOf(0, 0, 0, 6, 4, 3, 0, 8, 0),
                arrayOf(0, 2, 8, 0, 7, 6, 0, 1, 0),
                arrayOf(0, 9, 0, 4, 0, 0, 5, 6, 8),
                arrayOf(6, 4, 1, 0, 0, 5, 7, 9, 2),
                arrayOf(0, 0, 0, 2, 0, 0, 8, 0, 1),
                arrayOf(4, 0, 2, 1, 0, 0, 9, 0, 7),
                arrayOf(0, 8, 0, 0, 5, 4, 2, 3, 6)
            )
        )
        states.add(
            arrayOf(
                arrayOf(0, 0, 8, 3, 6, 0, 0, 0, 1),
                arrayOf(6, 1, 0, 0, 8, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 0, 4, 0),
                arrayOf(9, 8, 7, 0, 4, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 0, 3, 8, 7, 4),
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 0, 0),
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )

        for (state in states) {
            assertDoesNotThrow { SudokuState(state) }
        }
    }

    @Test
    @DisplayName("Given an invalid state, an IllegalArgumentException should be thrown.")
    fun givenInvalidState_ShouldThrowException() {
        val states = mutableListOf<Array<Array<Int>>>()

        // empty array
        states.add(emptyArray<Array<Int>>())

        // array with the wrong dimensions
        states.add(
            arrayOf(
                arrayOf(1, 2, 3),
                arrayOf(4, 5, 6),
                arrayOf(7, 8, 9)
            )
        )

        // states with right dimensions, but invalid values
        states.add(Array(BOARD_SIDE_LENGTH) { Array(BOARD_SIDE_LENGTH) { 1 } })
        states.add(
            arrayOf( // 8s in same column
                arrayOf(0, 0, 3, 8, 6, 0, 0, 0, 1),
                arrayOf(6, 1, 0, 0, 0, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 0, 4, 0),
                arrayOf(9, 8, 7, 0, 4, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 0, 3, 8, 7, 4),
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 0, 0),
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )
        states.add(
            arrayOf(
                arrayOf(0, 1, 0, 0, 0, 0, 6, 7, 0),
                arrayOf(9, 0, 0, 8, 9, 0, 4, 0, 5), // 9s in same row
                arrayOf(0, 0, 0, 6, 4, 3, 0, 8, 0),
                arrayOf(0, 2, 8, 0, 7, 6, 0, 1, 0),
                arrayOf(0, 9, 0, 4, 0, 0, 5, 6, 8),
                arrayOf(6, 4, 1, 0, 0, 5, 7, 9, 2),
                arrayOf(0, 0, 4, 2, 0, 0, 8, 4, 1),
                arrayOf(4, 0, 2, 1, 0, 0, 9, 0, 7),
                arrayOf(0, 8, 0, 0, 5, 4, 2, 3, 6)
            )
        )
        states.add(
            arrayOf(
                arrayOf(0, 0, 8, 3, 6, 0, 0, 0, 1),
                arrayOf(6, 1, 0, 0, 8, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 0, 4, 0),
                arrayOf(9, 8, 7, 4, 0, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 4, 3, 8, 7, 0), // 4s in same box
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 0, 0),
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )

        for (state in states) {
            assertThrows<IllegalArgumentException> { SudokuState(state) }
        }
    }

    @Test
    @DisplayName("Given a valid state and move, should return a state with that move applied.")
    fun givenValidStateAndValidMove_ShouldReturnStateWithMoveApplied() {
        val states = mutableListOf<Array<Array<Int>>>()
        states.add(
            arrayOf(
                arrayOf(0, 1, 0, 0, 0, 0, 6, 7, 0),
                arrayOf(9, 0, 0, 8, 0, 0, 4, 0, 5),
                arrayOf(0, 0, 0, 6, 4, 3, 0, 8, 0),
                arrayOf(0, 2, 8, 0, 7, 6, 0, 1, 0),
                arrayOf(0, 9, 0, 4, 0, 0, 5, 6, 8),
                arrayOf(6, 4, 1, 0, 0, 5, 7, 9, 2),
                arrayOf(0, 0, 0, 2, 0, 0, 8, 0, 1),
                arrayOf(4, 0, 2, 1, 0, 0, 9, 0, 7),
                arrayOf(0, 8, 0, 0, 5, 4, 2, 3, 6)
            )
        )
        states.add(
            arrayOf(
                arrayOf(0, 0, 8, 3, 6, 0, 0, 0, 1),
                arrayOf(6, 1, 0, 0, 8, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 0, 4, 0),
                arrayOf(9, 8, 7, 0, 4, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 0, 3, 8, 7, 4),
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 0, 0),
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )

        val moves = mutableListOf<Array<Array<Int>>>()
        moves.add(
            arrayOf(
                arrayOf(0, 0, 2),
                arrayOf(5, 3, 3),
                arrayOf(7, 1, 3)
            )
        )

        moves.add(
            arrayOf(
                arrayOf(0, 0, 2),
                arrayOf(2, 6, 6),
                arrayOf(7, 7, 8)
            )
        )

        val expectedOutput = MutableList<MutableList<Array<Array<Int>>>>(moves.size) { mutableListOf() }
        expectedOutput[0].add(
            arrayOf(
                arrayOf(2, 1, 0, 0, 0, 0, 6, 7, 0), // move in column 0
                arrayOf(9, 0, 0, 8, 0, 0, 4, 0, 5),
                arrayOf(0, 0, 0, 6, 4, 3, 0, 8, 0),
                arrayOf(0, 2, 8, 0, 7, 6, 0, 1, 0),
                arrayOf(0, 9, 0, 4, 0, 0, 5, 6, 8),
                arrayOf(6, 4, 1, 0, 0, 5, 7, 9, 2),
                arrayOf(0, 0, 0, 2, 0, 0, 8, 0, 1),
                arrayOf(4, 0, 2, 1, 0, 0, 9, 0, 7),
                arrayOf(0, 8, 0, 0, 5, 4, 2, 3, 6)
            )
        )
        expectedOutput[0].add(
            arrayOf(
                arrayOf(0, 1, 0, 0, 0, 0, 6, 7, 0),
                arrayOf(9, 0, 0, 8, 0, 0, 4, 0, 5),
                arrayOf(0, 0, 0, 6, 4, 3, 0, 8, 0),
                arrayOf(0, 2, 8, 0, 7, 6, 0, 1, 0),
                arrayOf(0, 9, 0, 4, 0, 0, 5, 6, 8),
                arrayOf(6, 4, 1, 3, 0, 5, 7, 9, 2), // move in column 3
                arrayOf(0, 0, 0, 2, 0, 0, 8, 0, 1),
                arrayOf(4, 0, 2, 1, 0, 0, 9, 0, 7),
                arrayOf(0, 8, 0, 0, 5, 4, 2, 3, 6),
            )
        )
        expectedOutput[0].add(
            arrayOf(
                arrayOf(0, 1, 0, 0, 0, 0, 6, 7, 0),
                arrayOf(9, 0, 0, 8, 0, 0, 4, 0, 5),
                arrayOf(0, 0, 0, 6, 4, 3, 0, 8, 0),
                arrayOf(0, 2, 8, 0, 7, 6, 0, 1, 0),
                arrayOf(0, 9, 0, 4, 0, 0, 5, 6, 8),
                arrayOf(6, 4, 1, 0, 0, 5, 7, 9, 2),
                arrayOf(0, 0, 0, 2, 0, 0, 8, 0, 1),
                arrayOf(4, 3, 2, 1, 0, 0, 9, 0, 7), // move in column 1
                arrayOf(0, 8, 0, 0, 5, 4, 2, 3, 6)
            )
        )
        expectedOutput[1].add(
            arrayOf(
                arrayOf(2, 0, 8, 3, 6, 0, 0, 0, 1), // move in column 0
                arrayOf(6, 1, 0, 0, 8, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 0, 4, 0),
                arrayOf(9, 8, 7, 0, 4, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 0, 3, 8, 7, 4),
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 0, 0),
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )
        expectedOutput[1].add(
            arrayOf(
                arrayOf(0, 0, 8, 3, 6, 0, 0, 0, 1),
                arrayOf(6, 1, 0, 0, 8, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 6, 4, 0), // move in column 6
                arrayOf(9, 8, 7, 0, 4, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 0, 3, 8, 7, 4),
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 0, 0),
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )
        expectedOutput[1].add(
            arrayOf(
                arrayOf(0, 0, 8, 3, 6, 0, 0, 0, 1),
                arrayOf(6, 1, 0, 0, 8, 0, 2, 5, 0),
                arrayOf(0, 7, 0, 1, 2, 9, 0, 4, 0),
                arrayOf(9, 8, 7, 0, 4, 0, 1, 0, 0),
                arrayOf(1, 6, 0, 5, 0, 3, 8, 7, 4),
                arrayOf(0, 3, 4, 8, 7, 1, 0, 2, 6),
                arrayOf(0, 4, 0, 7, 5, 8, 3, 1, 0),
                arrayOf(0, 0, 0, 0, 0, 4, 5, 8, 0), // move in column 7
                arrayOf(8, 0, 0, 9, 3, 2, 0, 6, 0)
            )
        )

        for ((i, state) in states.withIndex()) {
            for ((j, move) in moves[i].withIndex()) {
                val moved = SudokuState(state).moved(move[0], move[1], move[2])
                assertTrue(moved.board.contentDeepEquals(expectedOutput[i][j]))
            }
        }
    }
}