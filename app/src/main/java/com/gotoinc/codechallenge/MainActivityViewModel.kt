package com.gotoinc.codechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.gotoinc.codechallenge.model.Place
import com.gotoinc.codechallenge.repository.PlaceRepository

class MainActivityViewModel(latLng: LatLng) : ViewModel(){
    private val repository = PlaceRepository

    var places: List<Place>

    init {
        places = repository.getPlaces(latLng)
    }



}


class MainActivityViewModelFactory(private val latLng: LatLng) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(latLng) as T
    }
}