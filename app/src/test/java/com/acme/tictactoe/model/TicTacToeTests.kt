package com.acme.tictactoe.model

import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

/**
 * There are a lot more tests we can and should write but for now, just a few smoke tests.
 */
class TicTacToeTests {

  private lateinit var board: Board

  @Before
  fun setup() {
    board = Board()
  }

  /**
   * This test will simulate and verify x is the winner.
   *
   * X | X | X
   * O |   |
   * | O |
   */
  @Test
  fun test3inRowAcrossTopForX() {
    board.mark(0, 0) // x
    assertNull(board.winner)

    board.mark(1, 0) // o
    assertNull(board.winner)

    board.mark(0, 1) // x
    assertNull(board.winner)

    board.mark(2, 1) // o
    assertNull(board.winner)

    board.mark(0, 2) // x
    assertEquals(Player.X, board.winner)
  }


  /**
   * This test will simulate and verify o is the winner.
   *
   * O | X | X
   * | O |
   * | X | O
   */
  @Test
  fun test3inRowDiagonalFromTopLeftToBottomForO() {
    board.mark(0, 1) // x
    assertNull(board.winner)

    board.mark(0, 0) // o
    assertNull(board.winner)

    board.mark(2, 1) // x
    assertNull(board.winner)

    board.mark(1, 1) // o
    assertNull(board.winner)

    board.mark(0, 2) // x
    assertNull(board.winner)

    board.mark(2, 2) // o
    assertEquals(Player.O, board.winner)
  }
}
