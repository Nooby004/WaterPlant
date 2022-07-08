package com.mlallemant.waterplant.feature_plant_list.presentation.take_photo

import android.Manifest
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.presentation.core.camera.component.CameraView
import com.mlallemant.waterplant.feature_plant_list.presentation.core.permission.PermissionEvent
import com.mlallemant.waterplant.feature_plant_list.presentation.core.permission.component.PermissionUI
import kotlinx.coroutines.launch


@Composable
fun TakePhotoScreen(
    navController: NavController,
    viewModel: TakePhotoViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val stateCameraPermission = viewModel.performCameraEvent.collectAsState().value

    PermissionUI(
        context,
        Manifest.permission.CAMERA,
        stringResource(id = R.string.permission_camera),
        scaffoldState
    ) { permissionEvent ->
        when (permissionEvent) {
            is PermissionEvent.OnPermissionGranted -> {
                //Todo: do something now as we have location permission
                Log.d(TAG, "Location has been granted")
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Location permission granted!")
                }
            }
            is PermissionEvent.OnPermissionDenied -> {
                navController.navigateUp()
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {


        CameraView(onImageCaptured = { uri, fromGallery ->
            Log.d(TAG, "Image Uri Captured from Camera View")


        }, onError = { imageCaptureException ->
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("An error occurred while trying to take a picture")
            }
        })

    }


}