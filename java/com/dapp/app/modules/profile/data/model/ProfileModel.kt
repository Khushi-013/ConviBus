package com.dapp.app.modules.profile.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class ProfileModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConviBus: String? = MyApp.getInstance().resources.getString(R.string.lbl_convibus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtRakeshSharma: String? = MyApp.getInstance().resources.getString(R.string.lbl_rakesh_sharma)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtUPIID: String? = MyApp.getInstance().resources.getString(R.string.lbl_upi_id)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEmail: String? = MyApp.getInstance().resources.getString(R.string.msg_9284608501185_p)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtContact: String? = MyApp.getInstance().resources.getString(R.string.lbl_contact)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtMobileNo: String? = MyApp.getInstance().resources.getString(R.string.lbl_9284608501)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtAddress: String? = MyApp.getInstance().resources.getString(R.string.lbl_address)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtOStwalEmpire: String? = MyApp.getInstance().resources.getString(R.string.msg_ostwal_empire)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtArival: String? = MyApp.getInstance().resources.getString(R.string.lbl_arival)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtBoisar: String? = MyApp.getInstance().resources.getString(R.string.lbl_boisar)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDestination: String? = MyApp.getInstance().resources.getString(R.string.lbl_destination)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtVasai: String? = MyApp.getInstance().resources.getString(R.string.lbl_vasai)

)
