package com.achsanit.simpleweather.features.home.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.achsanit.simpleweather.BuildConfig
import com.achsanit.simpleweather.R
import com.achsanit.simpleweather.databinding.ActivityMainBinding
import com.achsanit.simpleweather.foundation.utils.DateHelper
import com.achsanit.simpleweather.foundation.utils.LocationUtil
import com.achsanit.simpleweather.foundation.utils.Resource
import com.achsanit.simpleweather.foundation.utils.makeGone
import com.achsanit.simpleweather.foundation.utils.makeVisible
import com.achsanit.simpleweather.foundation.utils.setCircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var locationManager: LocationManager
    private var location: Location? = null

    @Inject
    lateinit var locationUtil: LocationUtil

    @Inject
    lateinit var listWeatherAdapter: ListWeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        getCurrentLocation()

        getListWeatherCity()

        observeListWeatherCity()

        observeCurrentWeather()

        binding.rvListOtherPlace.layoutManager = LinearLayoutManager(this)
        binding.rvListOtherPlace.adapter = listWeatherAdapter
    }

    private fun observeCurrentWeather() {
        lifecycleScope.launch {
            homeViewModel.currentWeather.collectLatest {
                with(binding) {
                    when (it) {
                        is Resource.Loading -> {
                            pbLoading.makeVisible()
                            ivLocation.makeGone()
                        }
                        is Resource.Success -> {
                            pbLoading.makeGone()
                            ivLocation.makeVisible()
                            it.data?.let { currentWeather ->
                                val iconUrl =
                                    "${BuildConfig.BASE_IMAGE_URL}${currentWeather.icon}@4x.png"

                                tvTemperature.text = String.format(
                                    resources.getString(R.string.temp_placeholder),
                                    currentWeather.temperature.toInt()
                                )
                                location?.let {
                                    tvLocation.text = locationUtil.getAddress(it.latitude, it.longitude)
                                }
                                ivIcon.load(iconUrl) {
                                    setCircleCropTransformation()
                                }
                                tvDateTime.text =
                                    DateHelper.formatToDate(System.currentTimeMillis())
                                tvHumidity.text = String.format(
                                    resources.getString(R.string.humidity_placeholder),
                                    currentWeather.humidity
                                )
                                tvFeelsLike.text = String.format(
                                    resources.getString(R.string.feels_like_placeholder),
                                    currentWeather.feelsLike.toInt()
                                )
                                tvMainWeather.text = currentWeather.weather
                            }
                            it.data?.latestUpdate?.let { lastUpdate ->
                                tvLastUpdate.text = getString(
                                    R.string.last_update_at_place_holder,
                                    DateHelper.formatCurrentMillisTime(lastUpdate)
                                )
                            }
                        }
                        is Resource.Error -> {
                            pbLoading.makeGone()
                            Toast.makeText(
                                this@MainActivity,
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            pbLoading.makeGone()
                            Toast.makeText(
                                this@MainActivity,
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeListWeatherCity() {
        lifecycleScope.launch {
            homeViewModel.listWeather.collectLatest { result ->
                with(binding) {
                    when (result) {
                        is Resource.Loading -> {
                            pbListWeather.makeVisible()
                        }
                        is Resource.Success -> {
                            pbListWeather.makeGone()
                            result.data?.let {
                                listWeatherAdapter.submitData(it)
                            }
                        }

                        is Resource.Error -> {
                            pbListWeather.makeGone()
                        }
                        else -> {
                            pbListWeather.makeGone()
                        }
                    }
                }
            }
        }
    }

    private fun getListWeatherCity() {
        homeViewModel.getListWeather()
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return (this).requestAllPermission()
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            60000L * 2,
            0f,
            locationListener
        )

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                60000L * 2,
                0f, locationListener
            )
        }
    }

    private val locationListener = object : LocationListener {

        override fun onLocationChanged(p0: Location) {
            try {
                location = p0
                getCurrentWeather(p0)
            } catch (e: Throwable) {
                Log.e("", e.message.toString())
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }
    }

    private fun getCurrentWeather(location: Location) {
        homeViewModel.getCurrentWeather(location.latitude.toString(), location.longitude.toString())
    }

    fun getAddress(lat: Double, lng: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(this.applicationContext, Locale.getDefault())
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

    fun requestAllPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cant work without access location",
            PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestAllPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        getCurrentLocation()

        Snackbar.make(
            binding.root,
            "Permission Granted!!",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }
}