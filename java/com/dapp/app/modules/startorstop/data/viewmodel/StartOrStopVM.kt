package com.dapp.app.modules.startorstop.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapp.app.modules.startorstop.`data`.model.StartOrStopModel
import org.koin.core.KoinComponent

class StartOrStopVM : ViewModel(), KoinComponent {
  val startOrStopModel: MutableLiveData<StartOrStopModel> = MutableLiveData(StartOrStopModel())

  var navArguments: Bundle? = null
}
