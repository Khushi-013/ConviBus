package com.dapp.app.appcomponents.utility.location

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp.Companion.getInstance

/**
 * The service class which use to handle location updates callback
 * this allows to get user location with use of google play location service
 */
class LocationUpdateService : Service() {

    // The main entry point for interacting with the fused location provide
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    // location settings-enabler
    private var mSettingsClient: SettingsClient? = null

    // A data object that contains quality of service parameters for requests to the FusedLocationProviderClient.
    private var locationRequest: LocationRequest? = null

    // which uses to check location settings based on location request
    private var locationSettingsRequest: LocationSettingsRequest? = null

    //onCreate
    override fun onCreate() {
        super.onCreate()
        initData()
    }

    // google play service callback used to get location or failure
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val currentLocation = locationResult.lastLocation
            LocationProvider.getLocationUpdates().forEach {
                if (currentLocation != null) {
                    it.onUpdate(currentLocation)
                } // we are invoking onUpdate method to receivers which registered using LocationProvider
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
     * service class method which used to handle service related actions,
     * here we are handling [STOP_SERVICE] action
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.getAction().equals(STOP_SERVICE)) {
            stopForeground(true)
            stopSelfResult(startId)
        } else {
            prepareForegroundNotification()
            startLocationUpdates()
        }

        return START_STICKY
    }

    /**
     * method which starts the location update, also which checks the location permission
     * before starting the google play service location.
     */
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //if permission is not granted..
            LocationProvider.getLocationUpdates().forEach {
                it.onFail(null) // on fail we are invoking fail method to receivers which registered using LocationProvider
            }
            return
        }

        locationRequest ?: return //just make sure the not null

        if (locationSettingsRequest == null)
            return

        mSettingsClient?.checkLocationSettings(locationSettingsRequest!!)?.addOnSuccessListener {
            mFusedLocationClient?.requestLocationUpdates(
                locationRequest!!,
                locationCallback, Looper.getMainLooper()
            )

        }?.addOnFailureListener { e ->
            LocationProvider.getLocationUpdates().forEach {
                it.onFail(e) // on fail we are invoking fail method to receivers which registered using LocationProvider
            }
        }

    }

    /**
     * to notify notification for the background service this method is used.
     */
    private fun prepareForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                AppConstants.CHANNEL_ID,
                "Location Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }

//        TODO uncomment for handling pending intent of location service
//        val notificationIntent = Intent(this, LocationActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            AppConstants.SERVICE_LOCATION_REQUEST_CODE,
//            notificationIntent, 0
//        )

        val notification = NotificationCompat.Builder(this, AppConstants.CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentTitle("Fetching Location..")
            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentIntent(pendingIntent) // uncomment for handling pending intent
            .build()
        startForeground(AppConstants.LOCATION_SERVICE_NOTIF_ID, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /**
     * method which initialize location request and builds location setting request
     */
    private fun initData() {
        locationRequest = LocationRequest.create()
        locationRequest?.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getInstance())
        mSettingsClient = LocationServices.getSettingsClient(getInstance())
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

    companion object {
        //Location update interval
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 3000

        //Stop Service Action
        const val STOP_SERVICE: String = "STOP_SERVICE"
    }

    override fun onDestroy() {
        super.onDestroy()

        //once we stop the service then remove play service location update callback
        mFusedLocationClient!!.removeLocationUpdates(locationCallback)
    }
}