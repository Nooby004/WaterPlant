package com.mlallemant.waterplant.feature_authentication.domain.extension

import android.text.TextUtils


fun String.isPasswordStrongEnough(): Boolean {
    return this.length > 5 && this.contains("[0-9]".toRegex())
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}