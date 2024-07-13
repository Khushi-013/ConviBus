package com.dapp.app.modules.editprofile.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapp.app.modules.editprofile.`data`.model.EditprofileModel
import org.koin.core.KoinComponent

class EditprofileVM : ViewModel(), KoinComponent {
  val editprofileModel: MutableLiveData<EditprofileModel> = MutableLiveData(EditprofileModel())

  var navArguments: Bundle? = null
}
