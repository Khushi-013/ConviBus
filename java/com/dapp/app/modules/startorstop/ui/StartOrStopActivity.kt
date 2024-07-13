package com.dapp.app.modules.startorstop.ui

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.dapp.app.R
import com.dapp.app.appcomponents.base.BaseActivity
import com.dapp.app.appcomponents.utility.location.LocationManager
import com.dapp.app.appcomponents.utility.location.LocationPermissionHandler.Companion.REQUEST_PERMISSION_LOCATION
import com.dapp.app.databinding.ActivityStartOrStopBinding
import com.dapp.app.modules.profile.ui.ProfileActivity
import com.dapp.app.modules.startorstop.`data`.viewmodel.StartOrStopVM
import kotlin.Any
import kotlin.Array
import kotlin.Int
import kotlin.IntArray
import kotlin.String
import kotlin.Unit
import com.dapp.app.Manifest





class StartOrStopActivity :
    BaseActivity<ActivityStartOrStopBinding>(R.layout.activity_start_or_stop),
    LocationManager.EventCallback {
  private val viewModel: StartOrStopVM by viewModels<StartOrStopVM>()

  private val locationManager: LocationManager = LocationManager(this)

  private val REQUEST_CODE_PROFILE_ACTIVITY: Int = 589

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.startOrStopVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageImageEighteen.setOnClickListener {
      // TODO please, add your navigation code here
      finish()
    }
    binding.linearColumnstart.setOnClickListener {
      locationManager.initLocationUpdates()
    }
    binding.txtTaptostartor.setOnClickListener {
      val destIntent = ProfileActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_PROFILE_ACTIVITY)
    }
    binding.txtStart.setOnClickListener {
      // Check for location permission here and request it if needed
      if (locationManager.isLocationPermissionGranted()) {
        // Start location updates
        locationManager.initLocationUpdates()
        // Load the HTML page with Leaflet map
        binding.mapWebView.loadUrl("file:///android_asset/leaflet_map.html")
      } else {
        // Request location permission
        requestLocationPermission()
      }
    }

  }

  private fun requestLocationPermission() {
    ActivityCompat.requestPermissions(
      this,
      arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
      REQUEST_PERMISSION_LOCATION
    )
  }
  override fun onLocationUpdate(location: Location) {
    // Pass location data to JavaScript code within the WebView
    binding.mapWebView.evaluateJavascript(
      "updateLocation(${location.latitude}, ${location.longitude});",
      null
    )
  }



  override fun onResume() {
    super.onResume()
    locationManager.resumeLocationUpdates()
  }

  override fun onPause() {
    super.onPause()
    locationManager.stopLocationUpdates()
  }


  override fun getActivitySrc(): Any = this

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    `data`: Intent?
  ) {
    super.onActivityResult(requestCode,resultCode,data)
    locationManager.onActivityResult(requestCode, resultCode, data)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode,permissions,grantResults)
    when(requestCode) {
      REQUEST_PERMISSION_LOCATION -> {
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
      }
    }
  }


  companion object {
    const val TAG: String = "START_OR_STOP_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, StartOrStopActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
