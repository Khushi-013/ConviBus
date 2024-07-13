package com.dapp.app.extensions

import android.telephony.PhoneNumberUtils
import android.widget.Toast
import com.dapp.app.R
import com.dapp.app.appcomponents.di.MyApp
import kotlin.Boolean
import kotlin.String

public fun String?.isText(isMandatory: Boolean = false): Boolean {
  if (isNullOrEmpty()) {
    if (isMandatory) {
      Toast.makeText(
              MyApp.getInstance(),
              R.string.msg_please_enter_valid_text,
              Toast.LENGTH_SHORT
          ).show()
    }
    return !isMandatory
  }
  val textRegex = "[a-zA-Z ]+".toRegex()
  val result = 
  textRegex.matches(this)
  if (!result) {
    Toast.makeText(
            MyApp.getInstance(),
            R.string.msg_please_enter_valid_text,
            Toast.LENGTH_SHORT
        ).show()
  }
  return result
}

public fun String?.isMobileNumber(isMandatory: Boolean = false): Boolean {
  if (isNullOrEmpty()) {
    if (isMandatory) {
      Toast.makeText(
              MyApp.getInstance(),
              R.string.msg_please_enter_valid_mo_nu,
              Toast.LENGTH_SHORT
          ).show()
    }
    return !isMandatory
  }
  val result = 
  PhoneNumberUtils.isGlobalPhoneNumber(this)
  if (!result) {
    Toast.makeText(
            MyApp.getInstance(),
            R.string.msg_please_enter_valid_mo_nu,
            Toast.LENGTH_SHORT
        ).show()
  }
  return result
}

/**
 * Password should have, 
 * - at least a upper case letter
 * - at least a lower case letter
 * - at least a digit
 * - at least a special character [@#$%^&+=]
 * - length of at least 4
 * ⚫ no white space allowed
 */
public fun String?.isValidPassword(isMandatory: Boolean = false): Boolean {
  if (isNullOrEmpty()) {
    if (isMandatory) {
      Toast.makeText(
              MyApp.getInstance(),
              R.string.msg_please_enter_valid_pa,
              Toast.LENGTH_SHORT
          ).show()
    }
    return !isMandatory
  }
  val passwordRegex =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%^&+=])(?=\\S+${'$'}).{4,}${'$'}".toRegex()
  val result = 
  passwordRegex.matches(this)
  if (!result) {
    Toast.makeText(
            MyApp.getInstance(),
            R.string.msg_please_enter_valid_pa,
            Toast.LENGTH_SHORT
        ).show()
  }
  return result
}

public fun String?.isNumeric(isMandatory: Boolean = false): Boolean {
  if (isNullOrEmpty()) {
    if (isMandatory) {
      Toast.makeText(
              MyApp.getInstance(),
              R.string.msg_please_enter_valid_nu,
              Toast.LENGTH_SHORT
          ).show()
    }
    return !isMandatory
  }
  val numberRegex = "^[0-9]*${'$'}".toRegex()
  val result = 
  numberRegex.matches(this)
  if (!result) {
    Toast.makeText(
            MyApp.getInstance(),
            R.string.msg_please_enter_valid_nu,
            Toast.LENGTH_SHORT
        ).show()
  }
  return result
}

public fun String?.isValidPhone(isMandatory: Boolean = false): Boolean {
  if (isNullOrEmpty()) {
    if (isMandatory) {
      Toast.makeText(
              MyApp.getInstance(),
              R.string.msg_please_enter_valid_phone_nu,
              Toast.LENGTH_SHORT
          ).show()
    }
    return !isMandatory
  }
  val result = 
  PhoneNumberUtils.isGlobalPhoneNumber(this)
  if (!result) {
    Toast.makeText(
            MyApp.getInstance(),
            R.string.msg_please_enter_valid_phone_nu,
            Toast.LENGTH_SHORT
        ).show()
  }
  return result
}
