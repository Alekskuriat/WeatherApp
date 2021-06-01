package com.example.myapplicationviewmodel.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.databinding.MainFragmentBinding
import com.example.myapplicationviewmodel.save.SaveLoad
import com.example.myapplicationviewmodel.save.SaveLoadImpl
import com.google.android.material.snackbar.Snackbar
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        var weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)

        weather?.let {
            saveLoad?.save(context, it)
            setData(it)
        }

        if (weather == null) {
            saveLoad?.load(context)?.let { setData(it) }
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


    @SuppressLint("StringFormatInvalid")
    private fun setData(weatherData: Weather) {
        binding.cityName.text = weatherData.city.city
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lon.toString()
        )
        binding.temperatureValue.text =
            resources.getString(R.string.temperature, weatherData.temperature.toString())
        binding.feelsLikeValue.text =
            resources.getString(R.string.temperature, weatherData.feelsLike.toString())
        binding.humidityValue.text = resources.getString(
            R.string.humidity,
            DecimalFormat("#0.0").format(weatherData.humidity)
        )
        binding.probabilityOfPrecipitationValue.text = resources.getString(
            R.string.probability_of_precipitation,
            weatherData.probabilityOfPrecipitation.toString()
        )
        binding.windSpeedValue.text =
            resources.getString(R.string.wind_speed, weatherData.windSpeed.toString())
    }


}