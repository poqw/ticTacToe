package com.acme.tictactoe.util

import com.acme.tictactoe.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable

fun Disposable.disposeOnPause(viewModel: BaseViewModel) {
  viewModel.addToPauseDisposeBag(this)
}

fun Disposable.disposeOnDestroy(viewModel: BaseViewModel) {
  viewModel.addToDestroyDisposeBag(this)
}