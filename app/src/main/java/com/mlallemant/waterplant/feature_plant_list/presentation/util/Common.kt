package com.mlallemant.waterplant.feature_plant_list.presentation.util

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Common {

    companion object {
        
        fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
            return (ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED)
        }

        fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {

            val activity = context as Activity?
            if (activity == null)
                Log.d(TAG, "Activity is null")

            return ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                permission
            )
        }

    }
}