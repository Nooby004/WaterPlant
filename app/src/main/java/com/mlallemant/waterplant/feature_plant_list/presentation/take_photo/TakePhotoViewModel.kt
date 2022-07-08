package com.mlallemant.waterplant.feature_plant_list.presentation.take_photo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TakePhotoViewModel : ViewModel() {

    private val _performCameraEvent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val performCameraEvent = _performCameraEvent.asStateFlow()

    fun setPerformCameraEvent(request: Boolean) {
        _performCameraEvent.value = request
    }
    
}
