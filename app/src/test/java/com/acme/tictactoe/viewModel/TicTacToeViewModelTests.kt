package com.acme.tictactoe.viewModel

import com.acme.tictactoe.model.Cell
import com.acme.tictactoe.viewmodel.TicTacToeViewModel
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TicTacToeViewModelTests {

  private lateinit var viewModel: TicTacToeViewModel
  private val disposable = CompositeDisposable()

  @Before
  fun setup() {
    viewModel = TicTacToeViewModel()
    disposable.addAll(viewModel.cells.subscribe(), viewModel.winnerName.subscribe())
  }

  @After
  fun tearDown() {
    disposable.dispose()
  }

  @Test
  fun test_onClickedCellAt() {
    viewModel.onClickedCellAt(0, 0)
    assertNotNull(viewModel.cells.value)
    assertEquals("X", viewModel.cells.value!![0][0])

    viewModel.onClickedCellAt(2, 0)
    assertNotNull(viewModel.cells.value)
    assertEquals("O", viewModel.cells.value!![2][0])

    viewModel.onClickedCellAt(1, 1)
    assertNotNull(viewModel.cells.value)
    assertEquals("X", viewModel.cells.value!![1][1])
  }

  @Test
  fun test_onResetSelected() {
    viewModel.onClickedCellAt(0, 0) // X
    viewModel.onClickedCellAt(0, 1) // O
    viewModel.onClickedCellAt(0, 2) // X
    viewModel.onClickedCellAt(1, 2) // O
    viewModel.onClickedCellAt(1, 0) // X

    val dirtyBoard = arrayOf(
        arrayOf("X", "O", "X"),
        arrayOf("X", "", "O"),
        arrayOf("", "", "")
    )
    assertNotNull(viewModel.cells.value)
    assertArrayEquals(dirtyBoard, viewModel.cells.value)

    viewModel.onResetSelected()

    val cleanBoard = arrayOf(
        arrayOf("", "", ""),
        arrayOf("", "", ""),
        arrayOf("", "", "")
    )
    assertNotNull(viewModel.cells.value)
    assertArrayEquals(cleanBoard, viewModel.cells.value)
  }

  @Test
  fun test_cellsFromModel() {
    val cells = arrayOf(
        arrayOf(Cell("X"), Cell("O"), Cell("X")),
        arrayOf(Cell("X"), Cell("X"), Cell("O")),
        arrayOf(Cell("O"), Cell("X"), Cell("O"))
    )

    val expected = arrayOf(
        arrayOf("X", "O", "X"),
        arrayOf("X", "X", "O"),
        arrayOf("O", "X", "O")
    )
    assertArrayEquals(expected, viewModel.cellsToString(cells))
  }
}
