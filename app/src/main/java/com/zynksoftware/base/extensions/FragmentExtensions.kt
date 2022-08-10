package com.zynksoftware.base.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.allPermanentlyDenied
import com.fondesa.kpermissions.allShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest

fun Fragment.navigateToNextDestination(navDirections: NavDirections) {
    val destinationId =
        findNavController().currentDestination?.getAction(navDirections.actionId)?.destinationId

    findNavController().currentDestination?.let { node ->
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != null) {
            currentNode?.findNode(destinationId)?.let {
                findNavController().navigate(navDirections)
            }
        }
    }
}

fun Fragment.requestPermission(
    permission: String,
    vararg otherPermissions: String,
    allGranted: (PermissionRequest) -> Unit = {},
    allPermanentlyDeniedCallback: (PermissionRequest) -> Unit = {},
    allShouldShowRationaleCallback: (PermissionRequest) -> Unit = {}
) {
    val permissionRequest = permissionsBuilder(permission, *otherPermissions).build()
    permissionRequest.addListener { result ->
        when {
            result.allPermanentlyDenied() -> {
                allPermanentlyDeniedCallback.invoke(permissionRequest)
            }
            result.allShouldShowRationale() -> {
                allShouldShowRationaleCallback.invoke(permissionRequest)
            }
            result.allGranted() -> {
                allGranted.invoke(permissionRequest)
            }
        }
    }
    permissionRequest.send()
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        applicationContext,
        text,
        duration
    ).show()
}