package com.sbottingota.sudokusolvertest.gui

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH

const val CELL_SIZE = 1
const val CELL_FONT_SIZE = 5
val CELL_VALUE_PATTERN = Regex("[1-$BOARD_SIDE_LENGTH]?")

const val WINDOW_SIZE = 500