package com.acme.tictactoe.viewModel

import com.acme.tictactoe.viewmodel.BaseViewModel
import com.acme.tictactoe.viewmodel.BaseViewModel.LifeCycleShouldDispose
import io.reactivex.disposables.Disposables
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BaseViewModelTests {

  private lateinit var viewModel: BaseViewModel

  @Before
  fun setup() {
    viewModel = object : BaseViewModel() {}
  }

  @Test
  fun test_addToPauseDisposeBag() {
    assertNull(viewModel.disposableMap[LifeCycleShouldDispose.PAUSE])
    assertNull(viewModel.disposableMap[LifeCycleShouldDispose.DESTROY])

    val disposable = Disposables.empty()
    viewModel.addToPauseDisposeBag(disposable)
    assertEquals(1, viewModel.disposableMap[LifeCycleShouldDispose.PAUSE]?.size())
    assertNull(viewModel.disposableMap[LifeCycleShouldDispose.DESTROY])
  }

  @Test
  fun test_addToDestroyDisposeBag() {
    assertNull(viewModel.disposableMap[LifeCycleShouldDispose.PAUSE])
    assertNull(viewModel.disposableMap[LifeCycleShouldDispose.DESTROY])

    val disposable = Disposables.empty()
    viewModel.addToDestroyDisposeBag(disposable)
    assertNull(viewModel.disposableMap[LifeCycleShouldDispose.PAUSE])
    assertEquals(1, viewModel.disposableMap[LifeCycleShouldDispose.DESTROY]?.size())
  }

  @Test
  fun test_onPause() {
    val disposable1 = Disposables.empty()
    val disposable2 = Disposables.empty()

    viewModel.addToPauseDisposeBag(disposable1)
    viewModel.addToDestroyDisposeBag(disposable2)

    assertFalse(disposable1.isDisposed)
    assertFalse(disposable2.isDisposed)

    viewModel.onPause()

    assertTrue(disposable1.isDisposed)
    assertFalse(disposable2.isDisposed)
  }

  @Test
  fun test_onDestroy() {
    val disposable1 = Disposables.empty()
    val disposable2 = Disposables.empty()

    viewModel.addToPauseDisposeBag(disposable1)
    viewModel.addToDestroyDisposeBag(disposable2)

    assertFalse(disposable1.isDisposed)
    assertFalse(disposable2.isDisposed)

    viewModel.onDestroy()

    assertTrue(disposable1.isDisposed)
    assertTrue(disposable2.isDisposed)
  }
}
