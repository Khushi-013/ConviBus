package com.dapp.app.modules.busdetails.`data`.model

import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.String

data class BusDetailsModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConviBus: String? = MyApp.getInstance().resources.getString(R.string.lbl_convibus)
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
  var txtBusID: String? = MyApp.getInstance().resources.getString(R.string.lbl_bus_id)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSUBMIT: String? = MyApp.getInstance().resources.getString(R.string.lbl_submit)
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
