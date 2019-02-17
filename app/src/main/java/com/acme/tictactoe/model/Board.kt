package com.acme.tictactoe.model

import com.acme.tictactoe.model.Player.O
import com.acme.tictactoe.model.Player.X

class Board {
  private companion object {
    const val BOARD_SIZE = 3
  }

  private lateinit var cells: Array<Array<Cell>>
  private lateinit var state: GameState
  private lateinit var currentTurn: Player
  var winner: Player? = null
    private set

  private enum class GameState {
    IN_PROGRESS, FINISHED
  }

  init {
    restart()
  }

  /**
   * Restart or start a new game, will clear the board and win status
   */
  fun restart() {
    cells = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Cell() } }
    winner = null
    currentTurn = Player.X
    state = GameState.IN_PROGRESS
  }

  /**
   * Mark the current row for the player who's current turn it is.
   * Will perform no-op if the arguments are out of range or if that position is already played.
   * Will also perform a no-op if the game is already over.
   *
   * @param row 0..2
   * @param col 0..2
   * @return the player that moved or null if we did not move anything.
   */
  fun mark(row: Int, col: Int): Player? {
    if (!isValid(row, col)) {
      return null
    }

    val playerThatMoved: Player = currentTurn
    cells[row][col].value = currentTurn

    if (isWinningMoveByPlayer(currentTurn, row, col)) {
      state = GameState.FINISHED
      winner = currentTurn

    } else {
      // flip the current turn and continue
      flipCurrentTurn()
    }

    return playerThatMoved
  }

  private fun isValid(row: Int, col: Int): Boolean {
    return if (state == GameState.FINISHED) {
      false
    } else if (isOutOfBounds(row) || isOutOfBounds(col)) {
      false
    } else {
      !isCellValueAlreadySet(row, col)
    }
  }

  private fun isOutOfBounds(idx: Int): Boolean {
    return idx < 0 || idx > 2
  }

  private fun isCellValueAlreadySet(row: Int, col: Int): Boolean {
    return cells[row][col].value != null
  }

  /**
   * Algorithm adapted from http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html
   * @param player
   * @param currentRow
   * @param currentCol
   * @return true if `player` who just played the move at the `currentRow`, `currentCol`
   * has a tic tac toe.
   */
  private fun isWinningMoveByPlayer(player: Player?, currentRow: Int, currentCol: Int): Boolean {
    return ((cells[currentRow][0].value == player
        && cells[currentRow][1].value == player
        && cells[currentRow][2].value == player) // 3-in-the-row
        || (cells[0][currentCol].value == player
        && cells[1][currentCol].value == player
        && cells[2][currentCol].value == player) // 3-in-the-column
        || (currentRow == currentCol
        && cells[0][0].value == player
        && cells[1][1].value == player
        && cells[2][2].value == player) // 3-in-the-diagonal
        || (currentRow + currentCol == 2
        && cells[0][2].value == player
        && cells[1][1].value == player
        && cells[2][0].value == player)) // 3-in-the-opposite-diagonal
  }

  private fun flipCurrentTurn() {
    currentTurn = if (currentTurn == X) O else X
  }
}
