package com.dapp.app.modules.startorstop.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class StartOrStopModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConviBus: String? = MyApp.getInstance().resources.getString(R.string.lbl_convibus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtStart: String? = MyApp.getInstance().resources.getString(R.string.lbl_start)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTaptostartor: String? =
      MyApp.getInstance().resources.getString(R.string.msg_tap_to_start_or)

)
