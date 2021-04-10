package com.mobiquity.weatherapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.weatherapp.R
import com.mobiquity.weatherapp.WeatherApplication
import com.mobiquity.weatherapp.adapter.SearchAdapter
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.viewmodel.HomeViewModel


class SearchFragment : Fragment(), SearchAdapter.ItemClickListener {
    lateinit var recyclerAdapter: SearchAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var Edit_search: AppCompatEditText
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory((requireActivity().application as WeatherApplication).cityrepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerAdapter = SearchAdapter(requireContext(), this)
        recyclerView = root.findViewById(R.id.recyclerview) as RecyclerView
        Edit_search = root.findViewById(R.id.edit_search) as AppCompatEditText
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.setFavCitiesList(homeViewModel.staticList)


        Edit_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


            }

            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    override fun onItemClick(city: City) {
        view?.let {

            val bundle = Bundle()
            bundle.putDouble("lat", city.lat)
            bundle.putDouble("lon", city.longit)
            bundle.putString("name", city.name)

            Navigation.findNavController(it)
                .navigate(R.id.action_nav_searchCity_to_nav_city, bundle)

        }

    }


    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<City> = ArrayList()
        //looping through existing elements
        for (s in homeViewModel.staticList) {
            //if the existing elements contains the search input
            if (s.name.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        recyclerAdapter.getFilteredList(filterdNames)
    }


}