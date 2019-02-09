package com.acme.tictactoe.viewModel

import com.acme.tictactoe.viewmodel.TicTacToeViewModel
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

/**
 * There are a lot more tests we can and should write but for now, just a few smoke tests.
 */
class TicTacToeViewModelTests {

  private lateinit var viewModel: TicTacToeViewModel

  @Before
  fun setup() {
    viewModel = TicTacToeViewModel()
  }

  private fun clickAndAssertValueAt(row: Int, col: Int, expectedValue: String) {
    viewModel.onClickedCellAt(row, col)
    assertEquals(expectedValue, viewModel.cells["" + row + col])
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
    clickAndAssertValueAt(0, 0, "X")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(1, 0, "O")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(0, 1, "X")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(2, 1, "O")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(0, 2, "X")
    assertEquals("X", viewModel.winner.get())
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
    clickAndAssertValueAt(0, 1, "X")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(0, 0, "O")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(2, 1, "X")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(1, 1, "O")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(0, 2, "X")
    assertNull(viewModel.winner.get())

    clickAndAssertValueAt(2, 2, "O")
    assertEquals("O", viewModel.winner.get())
  }
}
