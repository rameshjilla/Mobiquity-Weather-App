package com.mobiquity.weatherapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.utils.Constants
import com.mobiquity.utils.Converter
import com.mobiquity.weatherapp.R
import com.mobiquity.weatherapp.WeatherApplication
import com.mobiquity.weatherapp.adapter.ForeCastAdapter
import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import com.mobiquity.weatherapp.model.ForeCast
import com.mobiquity.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather_city.*
import org.json.JSONObject

class CityWeatherFragment : Fragment() {
    var itemList: List<ForeCast> = ArrayList()
    lateinit var recyclerAdapter: ForeCastAdapter
    lateinit var recyclerView: RecyclerView


    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModel.WeatherViewModelFactory((requireActivity().application as WeatherApplication).weatherRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_weather_city, container, false)
        recyclerAdapter = ForeCastAdapter(requireContext())
        recyclerView = root.findViewById(R.id.forecast_recycler_view) as RecyclerView
        recyclerView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        recyclerView.adapter = recyclerAdapter

        return root
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            val lat = it.getDouble("lat")
            val lon = it.getDouble("lon")
            (activity as AppCompatActivity).supportActionBar?.title = it.getString("name")
            weatherViewModel.getCurrentweatherFromRemote(lat, lon)
                .observe(viewLifecycleOwner, Observer {
                    udpateUi(it)
                })

            weatherViewModel.getForecast(lat, lon, Constants.NetworkService.API_KEY_VALUE)
                .observe(viewLifecycleOwner, Observer {
                    //itemList = weatherViewModel.parseJsonForecast(it)
                    itemList = parseJsonForecast(it)

                })


        }


    }

    fun parseJsonForecast(response: String): List<ForeCast> {
        val jsonArray = JSONObject(response).getJSONArray("list")
        val itemList: MutableList<ForeCast> = ArrayList()
        for (i in 0..jsonArray.length() - 2) {
            val temp = jsonArray.getJSONObject(i).getJSONObject("main").optDouble("temp")
            val humidty = jsonArray.getJSONObject(i).getJSONObject("main").optInt("humidity")
            val cloudsArray = jsonArray.getJSONObject(i).getJSONArray("weather")
            val clouds = cloudsArray.getJSONObject(0).optString("main")
            val cloudsDesc = cloudsArray.getJSONObject(0).optString("description")
            val icon = cloudsArray.getJSONObject(0).optString("icon")
            val speed = jsonArray.getJSONObject(i).getJSONObject("wind").optDouble("speed")
            val fulldate = jsonArray.getJSONObject(i).optString("dt_txt").substring(0, 10)
            val dayeoftheweek = weatherViewModel.getDayoftheweek(fulldate)
            val foreCast =
                ForeCast(temp, humidty, clouds, cloudsDesc, icon, speed, dayeoftheweek.toString())
            if (!itemList.contains(foreCast)) {
                itemList.add(foreCast)
            }


        }
        recyclerAdapter.setForecast(itemList)
        return itemList

    }

    private fun udpateUi(it: CurrentWeatherEntity) {
        temperature_text_view.setText(it.main?.temp?.let { it1 -> Converter.getTempString(it1) })
        humidity_text_view.setText(it.main?.humidity?.let { it2 ->
            Converter.getHumidityString(
                it2
            )
        })



        weather_condition_text_view.setText(it.weather?.get(0)?.description)


        wind_speed_text_view.setText(it.wind?.speed?.let { it2 ->
            Converter.getWindString(
                it2
            )
        })

        setWeatherIcon(weather_image_view, it.weather?.let {
            it.get(0)?.icon
        })
    }

    @SuppressLint("NewApi")
    fun setWeatherIcon(view: ImageView, iconPath: String?) {
        if (iconPath.isNullOrEmpty())
            return
        val newPath = iconPath.replace(iconPath, "a$iconPath")
        val imageid = view.context.resources.getIdentifier(
            newPath + "_svg",
            "drawable",
            view.context.packageName
        )
        val imageDrawable = view.context.resources.getDrawable(imageid, null)
        view.setImageDrawable(imageDrawable)
    }


}