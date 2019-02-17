package com.acme.tictactoe.model

import org.junit.Assert.assertArrayEquals
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
   * This test tests both invalid and valid marks.
   *
   * X | X | X
   *   | O | O
   *   |   |
   */
  @Test
  fun testMark() {
    board.mark(0, 0)  // x
    assertEquals(Cell(Player.X.name), board.cells[0][0])
    board.mark(1, 1)  // o
    assertEquals(Cell(Player.O.name), board.cells[1][1])

    var expected = arrayOf(
        arrayOf(Cell(Player.X.name), Cell(), Cell()),
        arrayOf(Cell(), Cell(Player.O.name), Cell()),
        arrayOf(Cell(), Cell(), Cell())
    )
    board.mark(1, 1)  // invalid: Already set.
    assertArrayEquals(expected, board.cells)

    board.mark(2, 3)  // invalid: Out of bounds.
    assertArrayEquals(expected, board.cells)

    board.mark(0, 1)  // x
    assertEquals(Cell(Player.X.name), board.cells[0][1])

    board.mark(1, 2)  // o
    assertEquals(Cell(Player.O.name), board.cells[1][2])

    board.mark(0, 2)  // x, winner.
    assertEquals(Cell(Player.X.name), board.cells[0][2])

    expected = arrayOf(
        arrayOf(Cell(Player.X.name), Cell(Player.X.name), Cell(Player.X.name)),
        arrayOf(Cell(), Cell(Player.O.name), Cell(Player.O.name)),
        arrayOf(Cell(), Cell(), Cell())
    )
    board.mark(1, 0)  // invalid: Game finished.
    assertArrayEquals(expected, board.cells)
  }

  /**
   * This test will simulate and verify x is the winner.
   *
   * X | X | X
   * O |   |
   *   | O |
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
   *   | O |
   *   | X | O
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
