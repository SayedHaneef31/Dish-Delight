package com.example.dishdelight.API

import com.example.dishdelight.Data.CuisineRequest
import com.example.dishdelight.Data.CuisineResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OneBancApi
{

    @Headers(
        "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
        "X-Forward-Proxy-Action: get_item_list",
        "Content-Type: application/json"
    )
    @POST("emulator/interview/get_item_list")
    suspend fun getCuisineList(@Body requestBody:PageCountRequest): Response<CuisineResponse>



    @Headers(
        "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
        "X-Forward-Proxy-Action: get_item_by_filter",
        "Content-Type: application/json"
    )
    @POST("emulator/interview/get_item_by_filter")
    suspend fun getItemsByFilter(@Body request: CuisineRequest): Response<CuisineResponse>

}

data class PageCountRequest(
    val page:Int,
    val count:Int
)