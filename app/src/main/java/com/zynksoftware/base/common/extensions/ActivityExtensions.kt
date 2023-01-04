package com.zynksoftware.base.common.extensions

import androidx.appcompat.app.AppCompatActivity
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.allPermanentlyDenied
import com.fondesa.kpermissions.allShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest


fun AppCompatActivity.requestPermission(permission: String,
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