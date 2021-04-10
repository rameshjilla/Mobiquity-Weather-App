package com.mobiquity.weatherapp.viewmodel

import androidx.lifecycle.*
import com.mobiquity.utils.Constants
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.repo.CityRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CityRepository) : ViewModel() {

    val favCities: LiveData<List<City>> = repository.favCities.asLiveData()
    val staticList: List<City> = repository.getStaticListFromJson()

    class HomeViewModelFactory(private val repository: CityRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(
                    repository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    fun getLocatonFromMarker(lat: Double, long: Double): MutableLiveData<String> {
        return repository.getMarkerLocation(
            lat,
            long,
            Constants.NetworkService.API_KEY_VALUE
        )
    }


    fun insertCityfromMap(city: City) = viewModelScope.launch {
        repository.insert(city)

    }


    fun deleteFavCity(city: City) = viewModelScope.launch {
        repository.deleteFavCity(city)
    }


    fun deleteAllFavCity() = viewModelScope.launch {
        repository.deleteallCities()
    }


}