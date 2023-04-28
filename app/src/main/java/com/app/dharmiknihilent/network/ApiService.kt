package com.app.dharmiknihilent.network

import com.app.dharmiknihilent.models.ProductData
import retrofit2.http.*


interface ApiService {

    @GET("2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getProductList(): ProductData
}