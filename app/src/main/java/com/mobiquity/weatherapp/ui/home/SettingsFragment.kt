package com.mobiquity.weatherapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.weatherapp.R
import com.mobiquity.weatherapp.WeatherApplication
import com.mobiquity.weatherapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory((requireActivity().application as WeatherApplication).cityrepository)
    }
    lateinit var tv_changeUnits: AppCompatTextView
    lateinit var tv_reset: AppCompatTextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        tv_changeUnits = root.findViewById(R.id.tv_name) as AppCompatTextView
        tv_reset = root.findViewById(R.id.tv_reset_cities) as AppCompatTextView
        tv_changeUnits.setOnClickListener({
            Navigation.findNavController(it).navigate(R.id.action_nav_settings_to_nav_units)
        })

        tv_reset.setOnClickListener({
            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle(R.string.dialogTitle)

            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)


            builder.setPositiveButton("Yes") { dialogInterface, which ->
                homeViewModel.deleteAllFavCity()
            }
            //performing cancel action

            //performing negative action
            builder.setNegativeButton("No") { dialogInterface, which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
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
}