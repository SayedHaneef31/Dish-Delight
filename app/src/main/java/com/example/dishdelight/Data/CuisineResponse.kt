package com.example.dishdelight.Data

data class CuisineResponse(    //Main Big data class containng all the data
    val response_code: Int,
    val outcome_code: Int,
    val response_message: String,
    val page: Int,
    val count: Int,
    val total_pages: Int,
    val total_items: Int,
    val cuisines: List<Cuisine>
)
