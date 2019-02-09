package com.acme.tictactoe.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.acme.tictactoe.R
import com.acme.tictactoe.databinding.TictactoeBinding
import com.acme.tictactoe.viewmodel.TicTacToeViewModel

class TicTacToeActivity : AppCompatActivity() {

  private var viewModel = TicTacToeViewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<TictactoeBinding>(this, R.layout.tictactoe)
    binding.viewModel = viewModel
    viewModel.onCreate()
  }

  override fun onPause() {
    super.onPause()
    viewModel.onPause()
  }

  override fun onResume() {
    super.onResume()
    viewModel.onResume()
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.onDestroy()
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
}
