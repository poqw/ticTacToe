package com.acme.tictactoe.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.acme.tictactoe.R
import com.acme.tictactoe.util.disposeOnDestroy
import com.acme.tictactoe.util.disposeOnPause
import com.acme.tictactoe.viewmodel.TicTacToeViewModel
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.tictactoe.*

class TicTacToeActivity : AppCompatActivity() {

  private var viewModel = TicTacToeViewModel()

  private lateinit var cellButtons: List<List<Button>>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.tictactoe)

    cellButtons = listOf(
        listOf(cell_0_0, cell_0_1, cell_0_2),
        listOf(cell_1_0, cell_1_1, cell_1_2),
        listOf(cell_2_0, cell_2_1, cell_2_2)
    ).also { buttons ->
      buttons.forEachIndexed { col, cellsAtRow ->
        cellsAtRow.forEachIndexed { row, cell ->
          cell.clicks()
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({
                viewModel.onClickedCellAt(row, col)
              }, ::handleError)
              .disposeOnDestroy(viewModel)
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()

    viewModel.winnerName
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ winnerName ->
          if (winnerName.isNotEmpty()) {
            winnerPlayerLabel.text = winnerName
            winnerPlayerViewGroup.visibility = View.VISIBLE
          } else {
            winnerPlayerViewGroup.visibility = View.GONE
          }
        }, ::handleError)
        .disposeOnPause(viewModel)

    viewModel.cells
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ cells ->
          cellButtons.forEachIndexed { col, cellsAtRow ->
            cellsAtRow.forEachIndexed { row, cell ->
              cell.text = cells[row][col]
            }
          }
        }, ::handleError)
        .disposeOnPause(viewModel)
  }

  override fun onPause() {
    viewModel.onPause()
    super.onPause()
  }

  override fun onDestroy() {
    viewModel.onDestroy()
    super.onDestroy()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_tictactoe, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_reset -> {
        viewModel.onResetSelected()
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  private fun handleError(error: Throwable) {
    throw error
  }
}
