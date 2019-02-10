package com.acme.tictactoe.util

import com.acme.tictactoe.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposables
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RxUtilTests {

  private lateinit var viewModel: BaseViewModel

  @Before
  fun setup() {
    viewModel = object : BaseViewModel() {}
  }

  @Test
  fun test_disposeOnPause() {
    val disposable = Disposables.empty()
    assertFalse(disposable.isDisposed)

    disposable.disposeOnPause(viewModel)
    viewModel.onPause()

    assertTrue(disposable.isDisposed)
  }

  @Test
  fun test_disposeOnDestroy() {
    val disposable1 = Disposables.empty()
    val disposable2 = Disposables.empty()
    assertFalse(disposable1.isDisposed)
    assertFalse(disposable2.isDisposed)

    disposable1.disposeOnPause(viewModel)
    disposable2.disposeOnDestroy(viewModel)

    viewModel.onDestroy()

    assertTrue(disposable1.isDisposed)
    assertTrue(disposable2.isDisposed)
  }
}
