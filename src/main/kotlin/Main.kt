package com.sbottingota.sudokusolvertest

import com.sbottingota.sudokusolvertest.gui.SudokuSolverFrame
import javax.swing.JFrame

fun main() {
    val frame = SudokuSolverFrame()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isVisible = true
}