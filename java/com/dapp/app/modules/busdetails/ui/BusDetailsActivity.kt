package com.dapp.app.modules.busdetails.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dapp.app.R
import com.dapp.app.appcomponents.base.BaseActivity
import com.dapp.app.databinding.ActivityBusDetailsBinding
import com.dapp.app.modules.busdetails.`data`.viewmodel.BusDetailsVM
import com.dapp.app.modules.startorstop.ui.StartOrStopActivity
import kotlin.Int
import kotlin.String
import kotlin.Unit

class BusDetailsActivity : BaseActivity<ActivityBusDetailsBinding>(R.layout.activity_bus_details) {
  private val viewModel: BusDetailsVM by viewModels<BusDetailsVM>()

  private val REQUEST_CODE_START_OR_STOP_ACTIVITY: Int = 128


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.busDetailsVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageImageNineteen.setOnClickListener {
      // TODO please, add your navigation code here
      finish()
    }
    binding.viewRectangleNine.setOnClickListener {
      val destIntent = StartOrStopActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_START_OR_STOP_ACTIVITY)
    }
  }

  companion object {
    const val TAG: String = "BUS_DETAILS_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, BusDetailsActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
