package com.sbottingota.sudokusolvertest

import com.sbottingota.sudokusolvertest.gui.SudokuSolverFrame
import javax.swing.WindowConstants

fun main() {
    val frame = SudokuSolverFrame()
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    frame.isVisible = true
}