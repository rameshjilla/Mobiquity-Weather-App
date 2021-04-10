package com.mobiquity.weatherapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.utils.SwipeToDeleteCallback
import com.mobiquity.weatherapp.AddCityActivity
import com.mobiquity.weatherapp.R
import com.mobiquity.weatherapp.WeatherApplication
import com.mobiquity.weatherapp.adapter.CityAdapter
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), CityAdapter.ItemClickListener, CityAdapter.ItemDeleteListener {

    lateinit var recyclerAdapter: CityAdapter
    lateinit var recyclerView: RecyclerView
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory((requireActivity().application as WeatherApplication).cityrepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerAdapter = CityAdapter(requireContext(), this, this)
        recyclerView = root.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter
        getFavCitiesfromRepo()
        return root
    }


    fun getFavCitiesfromRepo() {

        homeViewModel.favCities.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.size > 0) {
                    recyclerView.visibility = View.VISIBLE
                    tv_no_fav_cities.visibility = View.GONE
                    it.let {
                        recyclerAdapter.setFavCitiesList(it)
                    }
                }
            }
        })
    }


    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
    }

    override fun onItemClick(city: City) {

        view?.let {
            val bundle = Bundle()
            bundle.putDouble("lat", city.lat)
            bundle.putDouble("lon", city.longit)
            bundle.putString("name", city.name)
            Navigation.findNavController(it).navigate(R.id.action_nav_home_to_nav_city, bundle)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search_city -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_nav_home_to_nav_searchCity)
                }
                return true
            }


            R.id.action_add_city -> {
                view?.let {
                    startActivity(Intent(requireContext(), AddCityActivity::class.java))
                }
                return true
            }

            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemDelete(city: City) {
        homeViewModel.deleteFavCity(city)
        recyclerAdapter.notifyDataSetChanged()


    }
}