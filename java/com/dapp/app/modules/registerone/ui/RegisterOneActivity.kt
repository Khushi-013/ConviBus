package com.dapp.app.modules.registerone.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dapp.app.R
import com.dapp.app.appcomponents.base.BaseActivity
import com.dapp.app.databinding.ActivityRegisterOneBinding
import com.dapp.app.modules.busdetails.ui.BusDetailsActivity
import com.dapp.app.modules.registerone.`data`.viewmodel.RegisterOneVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class RegisterOneActivity : BaseActivity<ActivityRegisterOneBinding>(R.layout.activity_register_one)
    {
  private val viewModel: RegisterOneVM by viewModels<RegisterOneVM>()

  private val REQUEST_CODE_BUS_DETAILS_ACTIVITY: Int = 314


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.registerOneVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageImageNineteen.setOnClickListener {
      // TODO please, add your navigation code here
      finish()
    }
    binding.viewRectangleNine.setOnClickListener {
      val destIntent = BusDetailsActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_BUS_DETAILS_ACTIVITY)
    }
  }

  companion object {
    const val TAG: String = "REGISTER_ONE_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, RegisterOneActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
