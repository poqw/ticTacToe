package com.acme.tictactoe.viewmodel

import android.support.annotation.VisibleForTesting
import com.acme.tictactoe.model.Board
import com.acme.tictactoe.model.Cell
import io.reactivex.subjects.BehaviorSubject

class TicTacToeViewModel : BaseViewModel() {

  private val model: Board = Board()

  val cells = BehaviorSubject.create<Array<Array<String>>>()
  val winnerName = BehaviorSubject.create<String>()

  fun onClickedCellAt(row: Int, col: Int) {
    model.mark(row, col)

    model.winner?.let { winner ->
      winnerName.onNext(winner.name)
    }

    cells.onNext(cellsToString(model.cells))
  }

  fun onResetSelected() {
    model.restart()

    winnerName.onNext(model.winner?.name ?: "")

    cells.onNext(cellsToString(model.cells))
  }

  @VisibleForTesting
  fun cellsToString(cells: Array<Array<Cell>>): Array<Array<String>> {
    return cells
        .map { cellsRow ->
          cellsRow
              .map { cell ->
                cell.text ?: ""
              }
              .toTypedArray()
        }
        .toTypedArray()
  }
}
