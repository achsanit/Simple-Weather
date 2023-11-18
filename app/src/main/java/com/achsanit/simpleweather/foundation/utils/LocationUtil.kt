package com.achsanit.simpleweather.foundation.utils

import android.content.Context
import android.location.Geocoder
import android.os.Build
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class LocationUtil @Inject constructor(
    private val context: Context
) {

    fun getAddress(lat: Double, lng: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(context, Locale.getDefault())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, lng, 1) { list ->
                if (list.size != 0) {
                    addressName = list[0].locality
                }
            }
        } else {
            try {
                val list = geocoder.getFromLocation(lat, lng, 1)
                if (list != null && list.size != 0) {
                    addressName = list[0].locality
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
        return addressName
    }

}