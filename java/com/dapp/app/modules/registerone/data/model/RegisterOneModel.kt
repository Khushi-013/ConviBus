package com.dapp.app.modules.registerone.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class RegisterOneModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConviBus: String? = MyApp.getInstance().resources.getString(R.string.lbl_convibus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEmailIdPhone: String? =
      MyApp.getInstance().resources.getString(R.string.msg_email_id_phone)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPassword: String? = MyApp.getInstance().resources.getString(R.string.lbl_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtName: String? = MyApp.getInstance().resources.getString(R.string.lbl_name)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtRegister: String? = MyApp.getInstance().resources.getString(R.string.lbl_register)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleTenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleElevenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleSevenValue: String? = null
)
