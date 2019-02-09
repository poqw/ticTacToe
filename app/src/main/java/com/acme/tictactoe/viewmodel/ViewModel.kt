package com.acme.tictactoe.viewmodel

interface ViewModel {
  fun onCreate()
  fun onPause()
  fun onResume()
  fun onDestroy()
}
