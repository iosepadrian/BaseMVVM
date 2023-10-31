package com.zynksoftware.base.common.extensions
import io.reactivex.disposables.Disposable

fun Disposable.disposeIfNotAlready() {
    if(!isDisposed) {
        dispose()
    }
}