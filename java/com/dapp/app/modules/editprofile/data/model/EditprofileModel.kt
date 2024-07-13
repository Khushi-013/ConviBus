package com.dapp.app.modules.editprofile.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class EditprofileModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConviBus: String? = MyApp.getInstance().resources.getString(R.string.lbl_convibus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtUPIID: String? = MyApp.getInstance().resources.getString(R.string.lbl_upi_id)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtContact: String? = MyApp.getInstance().resources.getString(R.string.lbl_contact)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtAddress: String? = MyApp.getInstance().resources.getString(R.string.lbl_address)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtArrival: String? = MyApp.getInstance().resources.getString(R.string.lbl_arrival)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDestination: String? = MyApp.getInstance().resources.getString(R.string.lbl_destination)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtName: String? = MyApp.getInstance().resources.getString(R.string.lbl_name)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSUBMIT: String? = MyApp.getInstance().resources.getString(R.string.lbl_submit)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleThirteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleFourteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleFifteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleSixteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleSeventeenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleTwelveValue: String? = null
)
