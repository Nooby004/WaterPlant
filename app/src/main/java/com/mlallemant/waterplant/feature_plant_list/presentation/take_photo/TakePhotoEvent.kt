package com.mlallemant.waterplant.feature_plant_list.presentation.take_photo

sealed class TakePhotoEvent {

    data class ShowPhoto(val path: String) : TakePhotoEvent()
    object ShowPreview : TakePhotoEvent()
    

}
