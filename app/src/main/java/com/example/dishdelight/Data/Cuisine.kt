package com.example.dishdelight.Data

data class Cuisine(
    val cuisine_id: String,
    val cuisine_name: String,
    val cuisine_image_url: String,
    val items: List<Dish>
)
