package com.dapp.app.appcomponents.utility.location

import android.content.Context
import android.content.Intent


/**
 * the object class which handles location updates callback by using background [LocationUpdateService].
 * @see LocationManager
 */
object LocationProvider {

    /**
     * is set of callbacks [LocationHelper.OnLocationUpdates]
     */
    private val locationUpdates = HashSet<LocationHelper.OnLocationUpdates>()

    /**
     * method which provides set of callbacks [LocationHelper.OnLocationUpdates]
     * @return the list [locationUpdates]
     */
    fun getLocationUpdates() = locationUpdates

    /**
     * to register [LocationHelper.OnLocationUpdates] event this method is called
     * @param locationUpdate is the event callback object of [LocationHelper.OnLocationUpdates] the passed object is going to add in [locationUpdate] set
     */
    fun addLocationUpdateCallback(locationUpdate: LocationHelper.OnLocationUpdates) {
        locationUpdates.add(locationUpdate)
    }


    /**
     * to un-register [LocationHelper.OnLocationUpdates] event this method is called
     * @param locationUpdate is the event callback object of [LocationHelper.OnLocationUpdates] the passed object is going to  remove from [locationUpdate] set
     */
    fun removeLocationUpdate(locationUpdate: LocationHelper.OnLocationUpdates) {
        locationUpdates.remove(locationUpdate)
    }

    /**
     * method is used to start [LocationUpdateService] service
     * @param context is used to start service
     */
    fun startUpdates(context: Context) {
        val service = Intent(context, LocationUpdateService::class.java)
        context.startService(service)
    }

    /**
     * method is used to stop [LocationUpdateService] service
     * @param context is used to pass stop service action to the [LocationUpdateService] service
     */
    fun stopUpdates(context: Context) {
        val stopIntent = Intent(context, LocationUpdateService::class.java)
        stopIntent.action = LocationUpdateService.STOP_SERVICE
        locationUpdates.clear()
        context.startService(stopIntent)
    }
}