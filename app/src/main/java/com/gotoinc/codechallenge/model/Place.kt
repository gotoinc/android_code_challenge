package com.gotoinc.codechallenge.model

data class Place(
    val id: Int,
    val markerColor: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val radius: Int,
    val data: PlaceData
)

data class PlaceData(
    val title: String,
    val subtitle: String,
    val description: String,
    val images: List<String>,
    val updatedAt: Long
)