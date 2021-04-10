package com.mobiquity.weatherapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mobiquity.weatherapp.R

class UnitsFragment : Fragment() {
    lateinit var tv_metric: AppCompatTextView
    lateinit var tv_imperial: AppCompatTextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings_units, container, false)
        tv_metric = root.findViewById(R.id.tv_metric)
        tv_imperial = root.findViewById(R.id.tv_imperial)
        tv_metric.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_units_to_nav_settings)
        }

        tv_imperial.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_units_to_nav_settings)


        }
        return root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }
}