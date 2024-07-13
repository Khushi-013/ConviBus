package com.dapp.app.modules.registerone.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapp.app.modules.registerone.`data`.model.RegisterOneModel
import org.koin.core.KoinComponent

class RegisterOneVM : ViewModel(), KoinComponent {
  val registerOneModel: MutableLiveData<RegisterOneModel> = MutableLiveData(RegisterOneModel())

  var navArguments: Bundle? = null
}
