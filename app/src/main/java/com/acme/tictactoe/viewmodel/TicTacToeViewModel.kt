package com.acme.tictactoe.viewmodel

import android.databinding.ObservableArrayMap
import android.databinding.ObservableField
import com.acme.tictactoe.model.Board

class TicTacToeViewModel : ViewModel {

  private val model: Board = Board()

  val cells = ObservableArrayMap<String, String>()
  val winner = ObservableField<String>()

  override fun onCreate() {
    // Do nothing.
  }

  override fun onPause() {
    // Do nothing.
  }

  override fun onResume() {
    // Do nothing.
  }

  override fun onDestroy() {
    // Do nothing.
  }

  fun onResetSelected() {
    model.restart()
    winner.set(null)
    cells.clear()
  }

  fun onClickedCellAt(row: Int, col: Int) {
    val playerThatMoved = model.mark(row, col)
    cells["$row$col"] = playerThatMoved.toString()
    winner.set(if (model.winner == null) null else model.winner.toString())
  }
}
