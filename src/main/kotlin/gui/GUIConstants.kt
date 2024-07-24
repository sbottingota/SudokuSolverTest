package com.sbottingota.sudokusolvertest.gui

import com.sbottingota.sudokusolvertest.game.BOARD_SIDE_LENGTH
import java.awt.Color
import java.awt.Font
import java.awt.Insets
import javax.swing.BorderFactory
import javax.swing.border.Border

const val CELL_SIZE = 25
val CELL_FONT = Font("Serif", Font.PLAIN, 20)
val CELL_VALUE_PATTERN = Regex("[1-$BOARD_SIDE_LENGTH]?")

const val BOX_EDGE_INSET_SIZE = 10
val BUTTONS_INSET = Insets(20, 30, 0, 0)

const val WINDOW_SIZE = 500

val BOARD_COLOR: Color = Color.WHITE
val BOARD_BORDER: Border = BorderFactory.createLineBorder(Color.BLACK)