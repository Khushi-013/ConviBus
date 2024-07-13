package com.dapp.app.appcomponents.utility.location

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsStatusCodes

/**
 * Use to manage Location permission lifecycle and other related events
 * @param eventCallback is the callback interface which used to emit location updates event
 * also it used to get activity source
 */
class LocationManager(private val eventCallback: EventCallback) :
    LocationPermissionHandler.LocationPermissionCallback {

    //callback which going to register in location update service
    private var locationUpdates: LocationHelper.OnLocationUpdates? = null

    //Request Code which used in GPS not enable error resolution
    private val REQUEST_CHECK_SETTINGS = 4

    //Flag to mange requests
    private var isGpsRequested = false
    private var isGpsGranted = true

    //object to manage location permission
    private val locationPermission = LocationPermissionHandler(this)

    /**
     * method which create location update callbacks and assign it to [locationUpdates] class variable
     * @return [LocationHelper.OnLocationUpdates] which is used in Location Provider class for
     * register the background service callbacks
     */
    private fun getLocationUpdateCallback(): LocationHelper.OnLocationUpdates {
        if (locationUpdates == null) {
            locationUpdates = object : LocationHelper.OnLocationUpdates {
                override fun onUpdate(location: Location) {
                    eventCallback.onLocationUpdate(location)
                }

                override fun onFail(e: Exception?) {
                    if (!isGpsRequested) {
                        isGpsRequested = true
                    } else {
                        return
                    }

                    val statusCode = (e as? ApiException)?.statusCode
                    executeWithContext {
                        when (statusCode) {
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                                Log.i(
                                    TAG,
                                    "Location settings are not satisfied. Attempting to upgrade " +
                                            "location settings "
                                )
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    val rae = e as ResolvableApiException
                                    rae.startResolutionForResult(
                                        it,
                                        REQUEST_CHECK_SETTINGS
                                    )
                                } catch (sie: IntentSender.SendIntentException) {
                                    Log.i(
                                        TAG,
                                        "PendingIntent unable to execute request."
                                    )
                                }
                            }
                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                val errorMessage =
                                    "Location settings are inadequate, and cannot be " +
                                            "fixed here. Fix in Settings."
                                Log.e(TAG, errorMessage)
                                Toast.makeText(
                                    it,
                                    errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    }
                }
            }
        }
        return locationUpdates!!
    }

    /**
     * method used to start location update by using LocationProvider which
     * starts the background service for location updates
     */
    private fun startLocationUpdates() {
        LocationProvider.addLocationUpdateCallback(getLocationUpdateCallback())
        executeWithContext {
            LocationProvider.startUpdates(it)
        }
    }

    /**
     * method which used to initiate location updates by checking permission
     * also if permission is not granted it requests permission
     */
    fun initLocationUpdates() {
        isGpsRequested = false
        isGpsGranted = true
        if (isLocationIsGranted()) {
            startLocationUpdates()
        } else {
            requestPermission()
        }
    }

    /**
     * once activity or fragments are resumed at that time this method is invoked
     * to start the location service because we are handling GPS request, and also we are checking
     * the location is granted then we start location service again.
     */
    fun resumeLocationUpdates() {
        if (isLocationIsGranted() && (!isGpsRequested || isGpsGranted)) {
            startLocationUpdates()
        }
    }

    /**
     * when we want to stop background location service and to un-register the callback this method is used
     * it is used in activity or fragment onPause method to disable the location service
     */
    fun stopLocationUpdates() {
        LocationProvider.removeLocationUpdate(getLocationUpdateCallback())
        executeWithContext {
            LocationProvider.stopUpdates(it)
        }
    }

    /**
     * method used to identify location permission is granted or not
     * @return boolean if permission is not granted then return value is false
     */
    fun isLocationIsGranted() = locationPermission.isLocationIsGranted()

    /**
     * To get activity context this method is used,
     * @return [Activity] from callback we are preparing the Activity to return
     */
    private fun getContext() = when (eventCallback.getActivitySrc()) {
        is Activity -> eventCallback.getActivitySrc() as Activity
        is Fragment -> (eventCallback.getActivitySrc() as Fragment).activity
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
    private fun requestPermission() {
        locationPermission.requestPermission()
    }

    /**
     * Receive the result from startResolutionForResult for request code [REQUEST_CHECK_SETTINGS],
     * It receives the resolution for GPS disable error, here we are checking the result and based on it, we will
     * start or stop the location service
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when (resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        isGpsRequested = false
                        isGpsGranted = true
                        Log.i(
                            TAG,
                            "User agreed to make required location settings changes."
                        )
                        startLocationUpdates()
                    }
                    AppCompatActivity.RESULT_CANCELED -> {
                        isGpsGranted = false
                        stopLocationUpdates()
                        Log.i(
                            TAG,
                            "User chose not to make required location settings changes."
                        )
                    }
                }
            }
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * invoke when we request for permission by calling [requestPermission]
     *
     * here we are handling permission request by using [LocationPermissionHandler.onRequestPermissionsResult]
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermission.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun isLocationPermissionGranted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getActivitySrc(): Any {
        return eventCallback.getActivitySrc()
    }

    override fun onPermissionGranted() {
        startLocationUpdates()
    }

    /**
     * interface used to handle callback methods for location updates
     * also it provides context for starting or stopping location service
     */
    interface EventCallback {
        /**
         * to get Activity Source it is maybe activity or maybe the fragment
         * because we don't want to hold any reference of activity or context item we are using callback method
         * @return Any which is Activity or Fragment
         */
        fun getActivitySrc(): Any

        /**
         * callback event to update the location
         */
        fun onLocationUpdate(location: Location)
    }

    public companion object {
        public const val TAG: String = "LOCATION_EVENT"
    }
}