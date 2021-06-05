package com.example.myapplicationviewmodel.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.WeatherDTO
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.databinding.MainFragmentBinding
import com.example.myapplicationviewmodel.loader.WeatherLoader
import com.example.myapplicationviewmodel.loader.WeatherLoaderListener
import com.example.myapplicationviewmodel.save.SaveLoad
import com.example.myapplicationviewmodel.save.SaveLoadImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Response
import java.text.DecimalFormat


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


    private lateinit var viewModel: MainViewModel
    private var saveLoad: SaveLoad? = null
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var weather: Weather? = null
    private var weatherData: Weather? = null
    private lateinit var loader: WeatherLoader
    private val onLoadListener: WeatherLoaderListener =
        object : WeatherLoaderListener {

            override fun onLoaded(response: Response<WeatherDTO>) {
                displayWeather(response)
            }

            override fun onFailed(str: String) {
                Snackbar
                    .make(binding.mainView, str, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Загрузить снова") {
                        weatherData?.let { weatherData -> initWeatherLoader(weatherData) }
                    }
                    .show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        saveLoad = SaveLoadImpl()
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeather()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(
            view, savedInstanceState
        )

        weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)

        weather?.let {
            saveLoad?.save(context, it)
            weatherData = it
            initWeatherLoader(it)
        } ?: saveLoad?.load(context)?.let {
            weatherData = it
            initWeatherLoader(it)
        }


    }

    private fun initWeatherLoader(weather: Weather) {
        loader = WeatherLoader(onLoadListener, weather.city.lat, weather.city.lon)
        loader.loadWeather()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        saveLoad = null
    }


    private fun displayWeather(response: Response<WeatherDTO>) {
        val responses = response.body()
        mainView.visibility = View.VISIBLE
        loadingLayout.visibility = View.GONE
        binding.cityName.text = weatherData?.city?.city
        cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData?.city?.lat.toString(),
            weatherData?.city?.lon.toString()
        )
        binding.temperatureValue.text =
            resources.getString(
                R.string.temperature,
                DecimalFormat("#0").format(responses?.factDTO?.temp)

            )
        binding.feelsLikeValue.text =
            resources.getString(
                R.string.temperature,
                DecimalFormat("#0").format(responses?.factDTO?.feelsLike)
            )
        binding.humidityValue.text =
            resources.getString(
                R.string.humidity,
                DecimalFormat("#0").format(responses?.factDTO?.humidity)
            )
        binding.probabilityOfPrecipitationValue.text =
            resources.getString(
                R.string.pressure_mm,
                DecimalFormat("#0").format(responses?.factDTO?.pressure)
            )
        binding.windSpeedValue.text =
            resources.getString(
                R.string.wind_speed,
                DecimalFormat("#0").format(responses?.factDTO?.windSpeed)
            )
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Ошибка загрузки данных", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Загрузить снова") { viewModel.getWeather() }
                    .show()
            }
        }
    }


}