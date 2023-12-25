package com.zigcon.common.artifacts.validator

/**
 * @Author Dharmesh
 * @Date 25-09-2022
 *
 * Information
 **/
class PhoneNumberValidator(private val mobileNumber: String?) {

    fun isMobileNumberEmpty(): Boolean {
        return mobileNumber.isNullOrEmpty()
    }

    fun isMobileNumberInValid(): Boolean {
        return !(mobileNumber?.length == 10)
    }
}