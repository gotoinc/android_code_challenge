package com.gotoinc.codechallenge.repository

import com.google.android.gms.maps.model.LatLng
import com.gotoinc.codechallenge.model.Place
import com.gotoinc.codechallenge.model.PlaceData

object PlaceRepository {
    private val colorsList =
        listOf("#4285F4", "#608AF5", "#F98028", "#19B775", "#ED2F2F", "#FF2E79")

    fun getPlaces(latitude: Double, longitude: Double) = ArrayList<Place>().apply {
        for (i in 0 until 5) {
            val placeData = PlaceData(
                "Extreme Alert",
                "Subtitle goes here",
                "Here is a sample title for an active marker",
                listOf(
                    "https://images.immediate.co.uk/production/volatile/sites/10/2018/02/7dce6028-0e39-4c0a-aa06-2a5b26285f9a-45028f8.jpg?quality=45&crop=13px,20px,2022px,860px&resize=960,640",
                    "https://images.immediate.co.uk/production/volatile/sites/10/2018/02/7dce6028-0e39-4c0a-aa06-2a5b26285f9a-45028f8.jpg?quality=45&crop=13px,20px,2022px,860px&resize=960,640"
                ),
                System.currentTimeMillis() + 10_000L
            )
            this.add(
                Place(
                    i,
                    colorsList[i],
                    latitude + ((i * 30) + 300) / 1_000_00f,
                    longitude + ((i * 30) + 300) / 1_000_00f,
                    "Awesome address",
                    100 + (i * 30),
                    placeData
                )
            )
        }
    }

    fun getPlaces(latLng: LatLng) = getPlaces(latLng.latitude, latLng.longitude)
}