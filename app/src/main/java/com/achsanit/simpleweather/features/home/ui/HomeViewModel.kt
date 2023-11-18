package com.achsanit.simpleweather.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.simpleweather.features.home.domain.WeatherUseCase
import com.achsanit.simpleweather.foundation.utils.Resource
import com.achsanit.simpleweather.model.CurrentWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: WeatherUseCase
) : ViewModel() {

    private val _currentWeather: MutableStateFlow<Resource<CurrentWeather>> =
        MutableStateFlow(Resource.Loading())
    val currentWeather: StateFlow<Resource<CurrentWeather>> = _currentWeather

    private val _listWeather: MutableStateFlow<Resource<List<CurrentWeather>>> =
        MutableStateFlow(Resource.Loading())
    val listWeather: StateFlow<Resource<List<CurrentWeather>>> = _listWeather

    fun getCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            useCase.getCurrentWeather(lat, lon).collectLatest { result ->
                _currentWeather.update { result }
            }
        }
    }

    fun getListWeather() {
        viewModelScope.launch {
            useCase.getWeatherCity("","").collectLatest { listWeather ->
                _listWeather.update { listWeather }
            }
        }
    }
}