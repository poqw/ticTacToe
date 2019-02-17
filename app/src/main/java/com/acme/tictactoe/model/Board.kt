package com.acme.tictactoe.model

class Board {
  private companion object {
    const val BOARD_SIZE = 3
  }

  lateinit var cells: Array<Array<Cell>>
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
   */
  fun mark(row: Int, col: Int) {
    if (!isValid(row, col)) {
      return
    }

    cells[row][col] = Cell(currentTurn.name)

    if (isWinningMoveByPlayer(currentTurn, row, col)) {
      state = GameState.FINISHED
      winner = currentTurn

    } else {
      // flip the current turn and continue
      flipCurrentTurn()
    }

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
    return cells[row][col].text != null
  }

  /**
   * Algorithm adapted from http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html
   * @param player
   * @param currentRow
   * @param currentCol
   * @return true if `player` who just played the move at the `currentRow`, `currentCol`
   * has a tic tac toe.
   */
  private fun isWinningMoveByPlayer(player: Player, currentRow: Int, currentCol: Int): Boolean {
    return ((cells[currentRow][0] == Cell(player.name)
        && cells[currentRow][1] == Cell(player.name)
        && cells[currentRow][2] == Cell(player.name)) // 3-in-the-row
        || (cells[0][currentCol] == Cell(player.name)
        && cells[1][currentCol] == Cell(player.name)
        && cells[2][currentCol] == Cell(player.name)) // 3-in-the-column
        || (currentRow == currentCol
        && cells[0][0] == Cell(player.name)
        && cells[1][1] == Cell(player.name)
        && cells[2][2] == Cell(player.name)) // 3-in-the-diagonal
        || (currentRow + currentCol == 2
        && cells[0][2] == Cell(player.name)
        && cells[1][1] == Cell(player.name)
        && cells[2][0] == Cell(player.name))) // 3-in-the-opposite-diagonal
  }

  private fun flipCurrentTurn() {
    currentTurn = if (currentTurn == Player.X) Player.O else Player.X
  }
}
