package com.dapp.app.modules.splashscreen.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class SplashScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtStartyourjour: String? =
      MyApp.getInstance().resources.getString(R.string.msg_start_your_jour)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTheUltimateBu: String? =
      MyApp.getInstance().resources.getString(R.string.msg_the_ultimate_bu)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtGetStarted: String? = MyApp.getInstance().resources.getString(R.string.lbl_get_started)

)
