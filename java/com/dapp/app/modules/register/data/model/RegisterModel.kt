package com.dapp.app.modules.register.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class RegisterModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConviBus: String? = MyApp.getInstance().resources.getString(R.string.lbl_convibus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtCurrentPasswor: String? =
      MyApp.getInstance().resources.getString(R.string.msg_current_passwor)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNewPassword: String? = MyApp.getInstance().resources.getString(R.string.lbl_new_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtConfirmPasswor: String? =
      MyApp.getInstance().resources.getString(R.string.msg_confirm_passwor)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtRESET: String? = MyApp.getInstance().resources.getString(R.string.lbl_reset)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleSevenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleTenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleElevenValue: String? = null
)
