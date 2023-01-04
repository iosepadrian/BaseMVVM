package com.zynksoftware.base.ui.factory

import android.content.Context
import com.zynksoftware.base.ui.errorhandler.AlertDialogDisplayer
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AlertDialogDisplayerFactory {

    fun create(context: Context): AlertDialogDisplayer
}
