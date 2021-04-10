package com.mobiquity.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.utils.Converter
import com.mobiquity.weatherapp.R
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.model.ForeCast

class ForeCastAdapter(
    val context: Context
) :
    RecyclerView.Adapter<ForeCastAdapter.MyViewHolder>() {


    var forecastList: List<ForeCast> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_forecast, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {

        return forecastList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvTemp.text = "T" + " " + Converter.getTempString(forecastList.get(position).temp)
        holder.tvHumidity.text =
            "H" + " " + Converter.getHumidityString(forecastList.get(position).humdity)
        holder.tvWind.text = Converter.getWindString(forecastList.get(position).speed)
        holder.tvday.text = forecastList.get(position).date
        Converter.setWeatherIcon(holder.image_view, forecastList.get(position).icon)

    }


    fun setForecast(forecastList: List<ForeCast>) {
        this.forecastList = forecastList;
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTemp: TextView = itemView!!.findViewById(R.id.temperature_text_view)
        val tvday: TextView = itemView!!.findViewById(R.id.day_text_view)
        val tvHumidity: TextView = itemView!!.findViewById(R.id.humidity_text_view)
        val tvWind: TextView = itemView!!.findViewById(R.id.wind_text_view)
        val image_view: AppCompatImageView = itemView!!.findViewById(R.id.weather_image_view)


    }


}