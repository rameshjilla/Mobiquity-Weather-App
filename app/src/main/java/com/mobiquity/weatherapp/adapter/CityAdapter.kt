package com.mobiquity.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.weatherapp.R
import com.mobiquity.weatherapp.data.City

class CityAdapter(
    val context: Context,
    val mItemClickListener: ItemClickListener,
    val mItemDeleteClickListener: ItemDeleteListener
) :
    RecyclerView.Adapter<CityAdapter.MyViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(city: City)
    }

    interface ItemDeleteListener {
        fun onItemDelete(city: City)
    }

    lateinit var cityList: List<City>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {

        return cityList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvcityName.text = cityList.get(position).name

    }


    fun setFavCitiesList(cityList: List<City>) {
        this.cityList = cityList;
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvcityName: TextView = itemView!!.findViewById(R.id.tv_name)
        val image: ImageView = itemView!!.findViewById(R.id.iv_bookmark)

        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(cityList.get(adapterPosition))
            }

            image.setOnClickListener {
                mItemDeleteClickListener.onItemDelete(cityList.get(adapterPosition))
            }
        }

    }
}