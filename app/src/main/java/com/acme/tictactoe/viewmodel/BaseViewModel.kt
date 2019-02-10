package com.acme.tictactoe.viewmodel

import android.support.annotation.VisibleForTesting
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel {
  enum class LifeCycleShouldDispose {
    PAUSE,
    DESTROY
  }

  @VisibleForTesting
  val disposableMap = mutableMapOf<LifeCycleShouldDispose, CompositeDisposable>()

  fun addToPauseDisposeBag(disposable: Disposable) {
    disposableMap.getOrPut(LifeCycleShouldDispose.PAUSE) { CompositeDisposable() }.add(disposable)
  }

  fun addToDestroyDisposeBag(disposable: Disposable) {
    disposableMap.getOrPut(LifeCycleShouldDispose.DESTROY) { CompositeDisposable() }.add(disposable)
  }

  open fun onPause() {
    disposableMap[LifeCycleShouldDispose.PAUSE]?.dispose()
  }

  open fun onDestroy() {
    // Should dispose all disposables.
    LifeCycleShouldDispose.values().forEach { lifeCycle ->
      disposableMap[lifeCycle]?.dispose()
    }
  }
}
