package com.achsanit.simpleweather.features.home.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.simpleweather.BuildConfig
import com.achsanit.simpleweather.R
import com.achsanit.simpleweather.databinding.ItemWeatherBinding
import com.achsanit.simpleweather.foundation.utils.LocationUtil
import com.achsanit.simpleweather.foundation.utils.setCircleCropTransformation
import com.achsanit.simpleweather.model.CurrentWeather
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class ListWeatherAdapter @Inject constructor(
    private val locationUtil: LocationUtil
) : RecyclerView.Adapter<ListWeatherAdapter.ListWeatherAdapterViewHolder>() {

    inner class ListWeatherAdapterViewHolder(
        private val binding: ItemWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CurrentWeather, holder: ListWeatherAdapterViewHolder) {
            with(binding) {
                val iconUrl =
                    "${BuildConfig.BASE_IMAGE_URL}${data.icon}@4x.png"

                tvCity.text = locationUtil.getAddress(data.lat, data.long)

                Log.d("ADAPTER", "${data.lat} ${data.long}")
                ivIcon.load(iconUrl) {
                    setCircleCropTransformation()
                }
                tvTemperature.text = String.format(
                    holder.itemView.context.getString(R.string.temp_placeholder),
                    data.temperature.toInt()
                )
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CurrentWeather>() {
        override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
            return oldItem.locationName == newItem.locationName
        }

        override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(data: List<CurrentWeather>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListWeatherAdapterViewHolder {

        val view = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListWeatherAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: ListWeatherAdapterViewHolder, position: Int) {
        val currentData = differ.currentList[position]
        holder.bind(currentData, holder)
    }

    override fun getItemCount(): Int = differ.currentList.size
}