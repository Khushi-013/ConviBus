package com.dapp.app.modules.profile.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dapp.app.R
import com.dapp.app.appcomponents.base.BaseActivity
import com.dapp.app.appcomponents.views.ImagePickerFragmentDialog
import com.dapp.app.databinding.ActivityProfileBinding
import com.dapp.app.modules.editprofile.ui.EditprofileActivity
import com.dapp.app.modules.profile.`data`.viewmodel.ProfileVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
  private val viewModel: ProfileVM by viewModels<ProfileVM>()

  private val REQUEST_CODE_EDITPROFILE_ACTIVITY: Int = 849


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.profileVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageImageSixteen.setOnClickListener {
      val destIntent = EditprofileActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_EDITPROFILE_ACTIVITY)
    }
    binding.imageImageThirteen.setOnClickListener {
      ImagePickerFragmentDialog().show(supportFragmentManager)
      { path ->//TODO HANDLE DATA
      }

    }
    binding.btnEdit.setOnClickListener {
      val destIntent = EditprofileActivity.getIntent(this, null)
      startActivityForResult(destIntent, REQUEST_CODE_EDITPROFILE_ACTIVITY)
    }
    binding.imageImageEighteen.setOnClickListener {
      // TODO please, add your navigation code here
      finish()
    }
  }

  companion object {
    const val TAG: String = "PROFILE_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ProfileActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
