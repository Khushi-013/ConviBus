package com.dapp.app.modules.busdetails.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapp.app.modules.busdetails.`data`.model.BusDetailsModel
import org.koin.core.KoinComponent

class BusDetailsVM : ViewModel(), KoinComponent {
  val busDetailsModel: MutableLiveData<BusDetailsModel> = MutableLiveData(BusDetailsModel())

  var navArguments: Bundle? = null
}
