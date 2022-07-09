package com.mlallemant.waterplant.feature_plant_list.presentation.take_photo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class TakePhotoViewModel : ViewModel() {

    private val _performCameraEvent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val performCameraEvent = _performCameraEvent.asStateFlow()

    private val _shouldShowPhoto: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val shouldShowPhoto = _shouldShowPhoto.asStateFlow()

    private val _photoPath: MutableStateFlow<String> = MutableStateFlow("")
    val photoPath = _photoPath.asStateFlow()

    fun setPerformCameraEvent(request: Boolean) {
        _performCameraEvent.value = request
    }


    fun onEvent(event: TakePhotoEvent) {
        when (event) {
            is TakePhotoEvent.ShowPhoto -> {
                _shouldShowPhoto.value = true
                _photoPath.value = event.path
            }
            is TakePhotoEvent.ShowPreview -> {
                _shouldShowPhoto.value = false

                // Delete photo
                viewModelScope.launch {
                    try {
                        File(_photoPath.value).delete()
                        _photoPath.value = ""
                    } catch (e: Exception) {
                        Log.e("TakePhotoViewModel", "can't delete picture")
                    }
                }
                
            }

        }

    }
}
