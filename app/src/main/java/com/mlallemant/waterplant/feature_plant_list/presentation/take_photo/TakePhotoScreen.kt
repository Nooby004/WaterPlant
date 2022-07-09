package com.mlallemant.waterplant.feature_plant_list.presentation.take_photo

import android.Manifest
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.presentation.core.camera.component.CameraView
import com.mlallemant.waterplant.feature_plant_list.presentation.core.permission.PermissionEvent
import com.mlallemant.waterplant.feature_plant_list.presentation.core.permission.component.PermissionUI
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch


@Composable
fun TakePhotoScreen(
    navController: NavController,
    viewModel: TakePhotoViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val cameraPermissionState = viewModel.performCameraEvent.collectAsState().value
    val shouldShowPhotoState = viewModel.shouldShowPhoto.collectAsState().value
    val photoPathState = viewModel.photoPath.collectAsState().value


    PermissionUI(
        context,
        Manifest.permission.CAMERA,
        stringResource(id = R.string.permission_camera),
        scaffoldState
    ) { permissionEvent ->
        when (permissionEvent) {
            is PermissionEvent.OnPermissionGranted -> {

                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Camera permission granted!")
                }
            }
            is PermissionEvent.OnPermissionDenied -> {

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {


        if (shouldShowPhotoState) {
            Box(modifier = Modifier) {

                GlideImage(
                    imageModel = photoPathState,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 12.dp, 12.dp, 0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TakePhotoEvent.ShowPreview)
                        },
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Delete photo",
                            tint = MaterialTheme.colors.background,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 0.dp, 0.dp, 12.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("picturePath", photoPathState)

                            navController.popBackStack()
                        },
                        modifier = Modifier.size(55.dp),  //avoid the oval shape
                        shape = CircleShape,
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        contentPadding = PaddingValues(0.dp),  //avoid the little icon
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Validate photo",
                            tint = MaterialTheme.colors.background
                        )
                    }
                }


            }


        } else {
            CameraView(onImageCaptured = { uri, fromGallery ->
                Log.d(TAG, "Image Uri Captured from Camera View")

                uri.path?.let {
                    TakePhotoEvent.ShowPhoto(it)
                }?.let {
                    viewModel.onEvent(it)
                }

            }, onError = { imageCaptureException ->
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("An error occurred while trying to take a picture")
                }
            })
        }

    }


}