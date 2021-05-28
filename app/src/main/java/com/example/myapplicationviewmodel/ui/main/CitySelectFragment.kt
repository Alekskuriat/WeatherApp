package com.example.myapplicationviewmodel.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationviewmodel.adapters.CitySelectAdapter
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.databinding.FragmentCitySelectBinding
import com.example.myapplicationviewmodel.router.AppRouter
import com.example.myapplicationviewmodel.router.RouterHolder
import com.google.android.material.snackbar.Snackbar

class CitySelectFragment : Fragment() {

    private var _binding: FragmentCitySelectBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val adapter = CitySelectAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(MainFragment.BUNDLE_EXTRA, weather)
                val routerHolder : AppRouter? = (activity as RouterHolder).getRouter()
                routerHolder?.showCityWeather(bundle)
            }
        }
    })
    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitySelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
        } else {
            viewModel.getWeatherFromLocalSourceRus()
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.mainFragmentFAB,
                        "Ошибка загрузки данных",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction("Загрузить снова") { viewModel.getWeatherFromLocalSourceRus() }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

    companion object {
        fun newInstance() =
            MainFragment()
    }
}


