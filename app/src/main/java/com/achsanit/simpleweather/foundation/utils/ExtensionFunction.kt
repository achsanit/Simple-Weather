package com.achsanit.simpleweather.foundation.utils

import android.view.View
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

fun ImageRequest.Builder.setCircleCropTransformation() {
//    Set transformation
    transformations(CircleCropTransformation())
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}