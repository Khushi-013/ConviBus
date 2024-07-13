package com.dapp.app.modules.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dapp.app.R
import com.dapp.app.appcomponents.base.BaseActivity
import com.dapp.app.databinding.ActivityLoginBinding
import com.dapp.app.modules.busdetails.ui.BusDetailsActivity
import com.dapp.app.modules.login.`data`.viewmodel.LoginVM
import com.dapp.app.modules.register.ui.RegisterActivity
import com.dapp.app.modules.registerone.ui.RegisterOneActivity
import kotlin.Int
import kotlin.String
import kotlin.Unit

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
  private val viewModel: LoginVM by viewModels<LoginVM>()

  private val REQUEST_CODE_REGISTER_ONE_ACTIVITY: Int = 621

  private val REQUEST_CODE_BUS_DETAILS_ACTIVITY: Int = 447

  private val REQUEST_CODE_REGISTER_ACTIVITY: Int = 884

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.loginVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.txtConfirmation.setOnClickListener {
      val destIntent = RegisterOneActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_REGISTER_ONE_ACTIVITY)
    }
    binding.imageImageEighteen.setOnClickListener {
      // TODO please, add your navigation code here
      finish()
    }
    binding.viewRectangleNine.setOnClickListener {
      val destIntent = BusDetailsActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_BUS_DETAILS_ACTIVITY)
    }
    binding.txtForgotpassword.setOnClickListener {
      val destIntent = RegisterActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_REGISTER_ACTIVITY)
    }
  }

  companion object {
    const val TAG: String = "LOGIN_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, LoginActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
