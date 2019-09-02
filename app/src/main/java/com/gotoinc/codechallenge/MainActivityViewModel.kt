package com.gotoinc.codechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.gotoinc.codechallenge.model.Place
import com.gotoinc.codechallenge.repository.PlaceRepository

class MainActivityViewModel() : ViewModel(){
    private val repository = PlaceRepository

    var places = emptyList<Place>()

    fun getUpdatedPlaces(latLng: LatLng): List<Place> {
        places = repository.getPlaces(latLng)
        return places
    }



}
