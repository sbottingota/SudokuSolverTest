package solver

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH
import com.sbottingota.sudokusolvertest.game.EMPTY_SQUARE_VALUE
import com.sbottingota.sudokusolvertest.game.Game
import com.sbottingota.sudokusolvertest.solver.SudokuSolverImpl
import org.junit.jupiter.api.DisplayName
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertTrue

class TestGameSolver {
    private val maxSolveTime = Duration.ofSeconds(20)

    @Test
    @DisplayName("Given a valid unsolved sudoku puzzle, should return it solved. ")
    fun givenUnsolvedState_ShouldReturnSolved() {
        val games = mutableListOf<Game>()
        games.add(Game(arrayOf(
            arrayOf(0, 1, 7, 2, 3, 0, 0, 0, 0),
            arrayOf(4, 3, 0, 0, 0, 0, 0, 7, 1),
            arrayOf(0, 0, 0, 0, 7, 0, 3, 6, 0),
            arrayOf(0, 4, 0, 0, 5, 0, 0, 0, 0),
            arrayOf(2, 7, 0, 6, 0, 0, 0, 5, 0),
            arrayOf(5, 0, 9, 0, 8, 0, 7, 0, 3),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 5),
            arrayOf(8, 5, 6, 0, 0, 0, 1, 2, 7),
            arrayOf(0, 0, 0, 5, 0, 8, 0, 0, 6)
        )))
        games.add(Game(Array(BOARD_SIDE_LENGTH) { Array(BOARD_SIDE_LENGTH) { EMPTY_SQUARE_VALUE } })) // board of nulls

        val solver = SudokuSolverImpl()

        for (game in games) {
            solver.solve(game)
            assertTrue(game.isSolved())
        }
    }
}