package com.dapp.app.modules.editprofile.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dapp.app.R
import com.dapp.app.appcomponents.base.BaseActivity
import com.dapp.app.databinding.ActivityEditprofileBinding
import com.dapp.app.modules.editprofile.`data`.viewmodel.EditprofileVM
import com.dapp.app.modules.profile.ui.ProfileActivity
import kotlin.Int
import kotlin.String
import kotlin.Unit

class EditprofileActivity : BaseActivity<ActivityEditprofileBinding>(R.layout.activity_editprofile)
    {
  private val viewModel: EditprofileVM by viewModels<EditprofileVM>()

  private val REQUEST_CODE_PROFILE_ACTIVITY: Int = 513


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.editprofileVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.viewRectangleSix.setOnClickListener {
      val destIntent = ProfileActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_PROFILE_ACTIVITY)
    }
    binding.imageImageEighteen.setOnClickListener {
      // TODO please, add your navigation code here
      finish()
    }
  }

  companion object {
    const val TAG: String = "EDITPROFILE_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditprofileActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
