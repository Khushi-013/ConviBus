package com.dapp.app.appcomponents.utility.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.dapp.app.appcomponents.di.MyApp.Companion.getInstance

/**
 * The object class which helps to get location updates
 * this class allows getting user location without starting any background service
 */
object LocationHelper {

    //Location update interval
    private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 3000

    // A data object that contains quality of service parameters for requests to the FusedLocationProviderClient.
    private var locationRequest: LocationRequest? = null

    // which uses to check location settings based on location request
    private var locationSettingsRequest: LocationSettingsRequest? = null

    // callback method which used to emit location update
    private var onLocationUpdate: OnLocationUpdates? = null

    // google play location service callback used to manage location updates
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val currentLocation = locationResult.lastLocation
            if (currentLocation != null) {
                onLocationUpdate?.onUpdate(currentLocation)
            }
            if (currentLocation != null) {
                Log.d(
                    "Locations",
                    currentLocation.latitude.toString() + "," + currentLocation.longitude
                )
            }
        }
    }


    /**
     * method which starts the location update, also which checks the location permission
     * before starting the Google play location service.
     *
     * @param context to handle location permission.
     */
    private fun startLocationUpdates(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //if permission is not granted.
            onLocationUpdate?.onFail(null)
            return
        }

        locationRequest ?: return //just make sure the not null

        if (locationSettingsRequest == null)
            return

        LocationServices.getSettingsClient(getInstance())
            .checkLocationSettings(locationSettingsRequest!!).addOnSuccessListener {

                LocationServices.getFusedLocationProviderClient(getInstance())
                    .requestLocationUpdates(
                        locationRequest!!,
                        locationCallback,
                        Looper.getMainLooper()
                    )

            }.addOnFailureListener {
                onLocationUpdate?.onFail(it)
            }
    }


    /**
     * method which initialize location request and builds location setting request
     */
    private fun initData() {
        locationRequest = LocationRequest.create()
        locationRequest?.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        buildLocationSettingsRequest()
    }

    /**
     * method which builds location setting request
     */
    private fun buildLocationSettingsRequest() {
        locationRequest?.let {
            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(it)
            locationSettingsRequest = builder.build()
        }
    }

    /**
     * method which assign the passed location update callback to [onLocationUpdate]
     * @param onLocationUpdate which are location update callback.
     */
    fun registerLocationCallback(onLocationUpdate: OnLocationUpdates) {
        LocationHelper.onLocationUpdate = onLocationUpdate
    }

    /**
     * method which starts the location updates
     * @param context is used to check location permission.
     * @param onLocationUpdate the callback interface object which will register for capture location updates from
     * Google play location service - location updates.
     */
    fun startUpdates(context: Context, onLocationUpdate: OnLocationUpdates) {
        initData()
        startLocationUpdates(context)
        registerLocationCallback(onLocationUpdate)
    }

    /**
     * to un-register or stop location updates this method is used.
     */
    fun stopUpdate() {
        onLocationUpdate = null
        locationRequest = null
        LocationServices.getFusedLocationProviderClient(getInstance())
            .removeLocationUpdates(locationCallback)
    }

    /**
     * The callback interface which used to handle location updates event and failures
     */
    interface OnLocationUpdates {

        /**
         * method invokes when google play location service started and [LocationResult] found.
         * @param location is founded from [LocationResult]
         */
        fun onUpdate(location: Location)

        /**
         * method invokes when any exception found in google play location service,
         * or location permission is not allowed
         * @param e is object of exception
         */
        fun onFail(e: Exception?)
    }
}