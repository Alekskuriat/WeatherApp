package com.example.myapplicationviewmodel.ui.main


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.data.City
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.databinding.MainFragmentBinding
import com.example.myapplicationviewmodel.loader.*
import com.example.myapplicationviewmodel.save.SaveLoad
import com.example.myapplicationviewmodel.save.SaveLoadImpl
import com.example.myapplicationviewmodel.utils.showSnackBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DecimalFormat

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_TEMP_EXTRA = "TEMPERATURE"
const val DETAILS_FEELS_LIKE_EXTRA = "FEELS LIKE"
const val DETAILS_HUMIDITY_EXTRA = "HUMIDITY"
const val DETAILS_WINDSPEED_EXTRA = "WINDSPEED"
const val DETAILS_PRESSURE_EXTRA = "PRESSURE"
const val MAIN_URL = "https://api.weather.yandex.ru/v2/informers?"


class MainFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance() = MainFragment()

    }

    private lateinit var LAT_LON_URL: String
    private var saveLoad: SaveLoad? = null
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var weather: Weather? = null
    private var weatherData: Weather? = null
    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        saveLoad = SaveLoadImpl()
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(
            view, savedInstanceState
        )

        weather = arguments?.getParcelable(BUNDLE_EXTRA)

        weather?.let {
            saveLoad?.save(context, it)
            weatherData = it
        } ?: saveLoad?.load(context)?.let {
            weatherData = it
        }

        LAT_LON_URL = "lat=${weatherData?.city?.lat}&lon=${weatherData?.city?.lon}"
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        weatherData?.city?.let {
            viewModel.getWeatherFromRemoteSource(it.lat, it.lon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        saveLoad = null
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        weatherData?.city?.let {
                            viewModel.getWeatherFromRemoteSource(it.lat, it.lon)
                        }
                    })
            }
        }
    }

    private fun setWeather(weather: Weather) {
        val city = weatherData?.city
        city?.let { saveCity(it, weather) }
        binding.cityName.text = weatherData?.city?.city
        cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData?.city?.lat.toString(),
            weatherData?.city?.lon.toString()
        )
        binding.temperatureValue.text =
            resources.getString(
                R.string.temperature,
                DecimalFormat("#0").format(weather.temperature)

            )
        binding.feelsLikeValue.text =
            resources.getString(
                R.string.temperature,
                DecimalFormat("#0").format(weather.feelsLike)
            )
        binding.humidityValue.text =
            resources.getString(
                R.string.humidity,
                DecimalFormat("#0").format(weather.humidity)
            )
        binding.probabilityOfPrecipitationValue.text =
            resources.getString(
                R.string.pressure_mm,
                DecimalFormat("#0").format(weather.pressure)
            )
        binding.windSpeedValue.text =
            resources.getString(
                R.string.wind_speed,
                DecimalFormat("#0").format(weather.windSpeed)
            )
        binding.conditionValue.text = weather.condition

        if (!resources.getBoolean(R.bool.isLandscape)) {
            Picasso
                .get()
                .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                .into(headerIcon)

            binding.weatherIcon?.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                R.drawable.ic_baseline_clear))
        }
    }

    private fun saveCity(city: City, weather: Weather) {
        viewModel.saveCityToDB(Weather(
            city,
            weather.temperature,
            weather.feelsLike,
            weather.humidity,
            weather.windSpeed,
            weather.pressure,
            weather.condition
        ))
    }


}



