package com.dapp.app.appcomponents.utility.location

import android.Manifest
import android.app.Activity
import androidx.fragment.app.Fragment
import com.dapp.app.appcomponents.di.MyApp
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

/**
 * class used to handle location permission
 * @param permissionCallback is event callback object of [LocationPermissionCallback] which used to handle events when permission result
 */
class LocationPermissionHandler(private val permissionCallback: LocationPermissionCallback) :
    EasyPermissions.PermissionCallbacks {
    companion object {
        const val REQUEST_PERMISSION_LOCATION: Int = 11
    }

    /**
     * callback method of [EasyPermissions.PermissionCallbacks]
     * if permission is denied by the user this method is invoke, here we are handling
     * Open Setting Dialog to allow permission
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        executeWithContext { activity ->
            if (EasyPermissions.somePermissionPermanentlyDenied(
                    activity,
                    *perms.toTypedArray()
                )
            ) {
                SettingsDialog.Builder(activity).build().show()
            }
        }

    }

    /**
     * callback method of [EasyPermissions.PermissionCallbacks]
     * if permission is granted by the user this method is invoke, here we are handling our callback [permissionCallback]
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (isLocationIsGranted())
            permissionCallback.onPermissionGranted()
    }

    /**
     * Callback for the result from requesting permissions. This method
     * invoke when we request for permission by calling [requestPermission]
     *
     * here we are handling permission request by using [EasyPermissions.onRequestPermissionsResult]
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode, permissions, grantResults,
            this
        )
    }

    /**
     * To get activity context this method is used,
     * @return [Activity] - from callback we are preparing the Activity to return
     */
    private fun getContext() = when (permissionCallback.getActivitySrc()) {
        is Activity -> permissionCallback.getActivitySrc() as Activity
        is Fragment -> (permissionCallback.getActivitySrc() as Fragment).activity
        else -> null
    }

    /**
     * To execute statements with activity context this method is used,
     * @param onContext is callback method which invokes with the Activity parameter.
     */
    private fun executeWithContext(onContext: (Activity) -> Unit) {
        getContext()?.let { onContext(it) }
    }

    /**
     * To request the location permission this method is used.
     * To use : requestPermission()
     */
    fun requestPermission() {
        if (isLocationIsGranted()) {
            permissionCallback.onPermissionGranted()
        } else
            executeWithContext { activity ->
                EasyPermissions.requestPermissions(
                    activity,
                    "This application need permission",
                    REQUEST_PERMISSION_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
    }

    /**
     * public method to identify location permission is granted or not
     * @return boolean if permission is not granted then return value is false
     */
    fun isLocationIsGranted() = hasPermission()

    /**
     * private method which check location permission is granted or not
     * @return boolean if permission is not granted then return value is false
     */
    private fun hasPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            MyApp.getInstance(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    /**
     * use to handle location permission callback
     */
    interface LocationPermissionCallback {
        /**
         * to get Activity Source it is maybe activity or maybe the fragment
         * because we don't want to hold any reference of activity or context item we are using callback method
         * @return Any which is Activity or Fragment
         */
        fun getActivitySrc(): Any

        /**
         * once location permission is granted this method will invoke
         */
        fun onPermissionGranted()

        /**
         * once onRequestPermissionsResult method invoke from Activity or Fragment,
         * this method is used to handle result
         */
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        )

        abstract fun isLocationPermissionGranted(): Boolean
    }
}